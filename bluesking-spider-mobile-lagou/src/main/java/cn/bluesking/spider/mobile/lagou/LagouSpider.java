package cn.bluesking.spider.mobile.lagou;

import java.io.IOException;
import java.net.Proxy;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.helper.MybatisHelper;
import cn.bluesking.spider.commons.util.CaseUtil;
import cn.bluesking.spider.commons.util.CodecUtil;
import cn.bluesking.spider.commons.util.HttpUtil;
import cn.bluesking.spider.commons.util.JsonUtil;
import cn.bluesking.spider.commons.util.RegexUtil;
import cn.bluesking.spider.mobile.lagou.entity.JsonResult;
import cn.bluesking.spider.mobile.lagou.entity.MobileLagouPosition;
import cn.bluesking.spider.mobile.lagou.mapper.MobileLagouPositionMapper;
import cn.bluesking.spider.proxyTool.ProxyProvider;

/**
 * 拉钩爬虫
 * 
 * @author 随心
 *
 */
public class LagouSpider {
	
	/**
	 * slf4j日志配置
	 */
	private static final Logger _LOG = LoggerFactory.getLogger(LagouSpider.class);
	
	public static void main(String[] args) throws IOException {
//		getJobs("广州", "Java");
		System.out.println(HttpUtil.httpBrowserGet("https://m.lagou.com/jobs/3398635.html"));
	}
	
	/** 爬取结束标记 */
	private static volatile boolean flag = false;
	
	/** 线程数 */
	private static final int THREAD_NUM = 10;
	
	/** 线程数组 */
	private static Thread[] threadArray = new Thread[THREAD_NUM];
	
	/**
	 * 获取数据总数
	 * @param city [String]城市
	 * @param keyword [String]关键字
	 * @return [int]数据总数
	 * @throws IOException 
	 */
	private static int getTotalSize(String city, String keyword) {
		String url = "https://m.lagou.com/search.json?city=" + 
				CodecUtil.encodeURL(city) + "&positionName=" + 
				CodecUtil.encodeURL(keyword) + "&pageNo=1&pageSize=15";
		// 发起请求
		String content = null;
		try {
			content = HttpUtil.httpBrowserGet(url);
			// 截取并解析json数据
			content = RegexUtil.regexString(content, "\"totalCount\":\"([\\d]+?)\"").get(0);
		} catch (IOException e) {
			_LOG.error("获取数据总数失败！" + e.getMessage());
			e.printStackTrace();
		}
		return CaseUtil.caseInt(content, 0);
	}
	
	/**
	 * 爬取拉勾网求职信息数据
	 * @param city [String]城市
	 * @param keyword [String]关键字
	 */
	public static void getJobs(String city, String keyword) {
		ProxyProvider.execute();
		// url前缀
		String urlPrefix = "https://m.lagou.com/search.json?city=" + 
					CodecUtil.encodeURL(city) + "&positionName=" + 
					CodecUtil.encodeURL(keyword) + "&pageNo=1&pageSize=15";
		// 获取数据总数
		int totalSize = getTotalSize(city, keyword);
		_LOG.debug("数据总数为:" + totalSize);
		int lastPageNo = (int) Math.ceil(totalSize / 15.0);
		for(int i = 1; i <= lastPageNo; i ++) {
			final int pageNo = i;
			new Thread() {
				// 构建url
				String url = urlPrefix + "&pageNo=" + pageNo + "&pageSize=15";
				@Override
				public void run() {
					try {
						// 发起请求
						String content = HttpUtil.httpBrowserGet(url, ProxyProvider.poll());
						// 截取并解析json数据
						content = "{" + RegexUtil.regexString(content, "(\"result\":\\[[\\s\\S]*?\\])").get(0) + "}";
						JsonResult result = JsonUtil.fromJson(content, JsonResult.class);
						// 获取数据库会话
						SqlSession session = MybatisHelper.getSessionFactory().openSession();
						MobileLagouPositionMapper mapper = session.getMapper(MobileLagouPositionMapper.class);
						for(MobileLagouPosition pos : result.getResult()) {
							if(mapper.selectByPrimaryKey(pos.getPositionId()) == null) {
								// 分析薪资
								analysisSalary(pos);
								// 获取职位详细信息
								getMobilePositionDetail(pos);
								mapper.insert(pos);
								session.commit();
							}
						}
						session.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		}
		ProxyProvider.interrupt();
	}
	
	/**
	 * 过滤html标签和特殊字符
	 * @param source
	 * @return
	 */
	private static String filterHtmlTags(String source) {
		source = source.replace("<p>", "");
		source = source.replace("</p>", "\n");
		source = source.replaceAll("<br/*?>", "\n");
		return CodecUtil.decodeHTML(source);
	}
	
	/**
	 * 手机接口获取公司的详细信息
	 * @param position
	 */
	private static void getMobilePositionDetail(MobileLagouPosition position) {
		String url = "https://m.lagou.com/jobs/" + position.getPositionId() + ".html";
		try {
			Proxy proxy = ProxyProvider.poll();
			String content = HttpUtil.httpBrowserGet(url, proxy);
			// 学历
			position.setEducation(RegexUtil.regexAString(content, 
					"name=\"keywords\"><meta content=\".+? .+? (.+?) .+?\" name=\"description\">"));
			// 工作经验
			position.setWorkYear(RegexUtil.regexAString(content, 
					"name=\"keywords\"><meta content=\".+? .+? .+? (.+?) .+?\" name=\"description\">"));
			// 职位诱惑
			position.setPositionAdvantage(RegexUtil.regexAString(content, 
					"name=\"keywords\"><meta content=\".+? .+? .+? .+? .+? .+? (.+?) .+?\" name=\"description\">"));
			// 职位描述
			position.setPositionDescription(filterHtmlTags(RegexUtil.regexAString(content, 
					"<h3 class=\"description\">职位描述：</h3> *?<div> *?([\\s\\S]+?) *?</div>")));
			// 城区
			position.setDistrict(RegexUtil.regexAString(content, 
					"city=.*?&district=(.*?)#filterBox"));
			// 商区
			position.setBizArea(RegexUtil.regexAString(content, 
					"district=.*?&bizArea=(.*?)#filterBox"));
			// 地址
			position.setPositionAddress(RegexUtil.regexAString(content, 
					"name=\"positionAddress\" value=\"(.*?)\" />"));
			// 经度
			position.setLat(RegexUtil.regexAString(content, 
					"name=\"positionLat\" value=\"(.*?)\""));
			// 纬度
			position.setLng(RegexUtil.regexAString(content, 
					"name=\"positionLng\" value=\"(.*?)\""));
		} catch (IOException e) {
			_LOG.error("获取职位 {}详细信息出错！", position.getCompanyId());
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取公司的详细信息
	 * @param position
	 */
	private static void getPositionDetail(MobileLagouPosition position) {
		String url = "https://www.lagou.com/jobs/" + position.getPositionId() + ".html";
		try {
			Proxy proxy = ProxyProvider.poll();
			String content = HttpUtil.httpBrowserGet(url, proxy);
			// 学历
			position.setEducation(RegexUtil.regexAString(content, 
					"name=\"keywords\"><meta content=\".+? .+? (.+?) .+?\" name=\"description\">"));
			// 工作经验
			position.setWorkYear(RegexUtil.regexAString(content, 
					"name=\"keywords\"><meta content=\".+? .+? .+? (.+?) .+?\" name=\"description\">"));
			// 职位诱惑
			position.setPositionAdvantage(RegexUtil.regexAString(content, 
					"name=\"keywords\"><meta content=\".+? .+? .+? .+? .+? .+? (.+?) .+?\" name=\"description\">"));
			// 职位描述
			position.setPositionDescription(filterHtmlTags(RegexUtil.regexAString(content, 
					"<h3 class=\"description\">职位描述：</h3> *?<div> *?([\\s\\S]+?) *?</div>")));
			// 城区
			position.setDistrict(RegexUtil.regexAString(content, 
					"city=.*?&district=(.*?)#filterBox"));
			// 商区
			position.setBizArea(RegexUtil.regexAString(content, 
					"district=.*?&bizArea=(.*?)#filterBox"));
			// 地址
			position.setPositionAddress(RegexUtil.regexAString(content, 
					"name=\"positionAddress\" value=\"(.*?)\" />"));
			// 经度
			position.setLat(RegexUtil.regexAString(content, 
					"name=\"positionLat\" value=\"(.*?)\""));
			// 纬度
			position.setLng(RegexUtil.regexAString(content, 
					"name=\"positionLng\" value=\"(.*?)\""));
		} catch (IOException e) {
			_LOG.error("获取职位 {}详细信息出错！", position.getCompanyId());
			e.printStackTrace();
		}
	}
	
	/**
	 * 解析薪资上下限
	 * @param position
	 */
	private static void analysisSalary(MobileLagouPosition position) {
		String salary = position.getSalary();
		try {
			// 薪资10k以上就把薪资上下限都设置为10k
			if (salary.indexOf("以上") != -1) {
				Integer salaryNum = Integer.parseInt(RegexUtil.regexString(salary, "([\\d]+?)[kK]").get(0));
				position.setMaxSalary(salaryNum);
				position.setMinSalary(salaryNum);
			} else if (salary.indexOf("以下") != -1) { // 薪资10k以下就把薪资下限都设置为0
				Integer salaryNum = Integer.parseInt(RegexUtil.regexString(salary, "([\\d]+?)[kK]").get(0));
				position.setMaxSalary(salaryNum);
				position.setMinSalary(0);
			} else {
				List<String> salaries = RegexUtil.regexString(salary, "(\\d+?)[kK]");
				position.setMaxSalary(Integer.parseInt(salaries.get(1)));
				position.setMinSalary(Integer.parseInt(salaries.get(0)));
			}		
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(salary);
			throw new RuntimeException("解析薪资范围出错！");
		}
	}
	
}
