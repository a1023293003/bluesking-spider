package cn.bluesking.spider.mobile.lagou;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.entity.JsonResult;
import cn.bluesking.spider.commons.entity.MobileLagouPosition;
import cn.bluesking.spider.commons.entity.MobileLagouPositionExample;
import cn.bluesking.spider.commons.entity.MobileLagouPositionExample.Criteria;
import cn.bluesking.spider.commons.helper.MybatisHelper;
import cn.bluesking.spider.commons.mapper.MobileLagouPositionMapper;
import cn.bluesking.spider.commons.util.CodecUtil;
import cn.bluesking.spider.commons.util.HttpUtil;
import cn.bluesking.spider.commons.util.JsonUtil;
import cn.bluesking.spider.commons.util.RegexUtil;
import cn.bluesking.spider.commons.util.StringUtil;
import cn.bluesking.spider.proxyTool.CacheProxyGetter;
import cn.bluesking.spider.proxyTool.ProxyProvider;

/**
 * 拉勾网爬虫线程
 * 
 * @author 随心
 *
 */
public class LagouSpiderThread extends Thread {

	/**
	 * slf4j日志配置
	 */
	private static final Logger _LOG = LoggerFactory.getLogger(LagouSpiderThread.class);
	
	/** 爬虫爬取的页数 */
	private int pageNo = 0;
	
	/** 爬虫编号 */
	private int index = 0;
	
	/** 线程所属的线程管理器 */
	private LagouThreadManager manager = null;
	
	public int getPageNo() {
		return pageNo;
	}
	
	/**
	 * 构造方法
	 * @param pageNo [int]初始爬取页数
	 * @param index [int]爬虫编号
	 * @param manager [LagouThreadManager]线程所属管理器
	 */
	public LagouSpiderThread(int pageNo, int index, LagouThreadManager manager) {
		this.pageNo = pageNo;
		this.index = index;
		this.manager = manager;
	}
	
	/**
	 * 克隆一个代理对象
	 * @param proxy [Proxy]待克隆代理对象
	 * @return
	 */
	private static Proxy cloneProxy(Proxy proxy) {
		_LOG.debug("当前代理队列中代理容量:[" + ProxyProvider.size() + "]");
		InetSocketAddress address = (InetSocketAddress) proxy.address();
		return new Proxy(Proxy.Type.HTTP, 
				new InetSocketAddress(
						address.getAddress().getHostAddress(), address.getPort()));
	}
	
	/**
	 * 构建url
	 * @param urlPrefix [String]url前缀
	 * @param pageNo [int]爬取页数
	 * @return
	 */
	public static String getUrl(String urlPrefix, int pageNo) {
		return urlPrefix + "&pageNo=" + pageNo + "&pageSize=15";
	}
	
	/**
	 * 打印完成线程的编号
	 * @param manager [LagouThreadManager]线程所属的管理器
	 */
	private static synchronized void printFinishThreadNo(LagouThreadManager manager) {
		if(manager.getThreadArray().length <= 0) return;
		System.err.print("目前已完成的爬虫有:[");
		if(manager.getFlag(0)) {
			System.err.print("0:" + manager.getThreadArray()[0].getPageNo());
		} else {
			System.err.print("$:" + manager.getThreadArray()[0].getPageNo());
		}
		for(int i = 1; i < manager.getThreadNum(); i ++) {
			if(manager.getFlag(i)) {
				System.err.print(", " + i + ":" + manager.getThreadArray()[i].getPageNo());
			} else {
				System.err.print(", $:" + manager.getThreadArray()[i].getPageNo());
			}
		}
		System.err.println("]");
	}
	
	@Override
	public void run() {
		if(this.pageNo <= manager.getLastPage_no()) {
			SqlSession session = null;
			try {
				_LOG.debug("开启线程" + pageNo);
				Proxy proxy = ProxyProvider.poll();
				// 备份代理对象
				Proxy backUpProxy = cloneProxy(proxy);
				System.out.println("线程" + pageNo + "成功获取代理！");
				// 发起请求
				String content = HttpUtil.httpBrowserGet(
						getUrl(manager.getUrlPrefix(), pageNo), proxy);
				// 截取并解析json数据
				content = "{" + RegexUtil.regexAString(content, "(\"result\":\\[[\\s\\S]*?\\]),") + "}";
				JsonResult result = JsonUtil.fromJson(content, JsonResult.class);
				// 获取数据库会话
				session = MybatisHelper.getSessionFactory().openSession();
				MobileLagouPositionMapper mapper = session.getMapper(MobileLagouPositionMapper.class);
				// 遍历所有职位信息
				for(MobileLagouPosition pos : result.getResult()) {
					if(!manager.getPositionIdList().contains(pos.getPositionId())) {
						// 设置关键字
						pos.setKeyWord(manager.getKeyWord());
						// 分析薪资
						analysisSalary(pos);
						// 获取职位详细信息
						getPositionDetail(pos);
						mapper.insert(pos);
						session.commit();
						// 存入列表中
						manager.addPositionId(pos.getPositionId());
					}
				}
				// 代理对象存入缓存中
				CacheProxyGetter.add(backUpProxy);
				this.pageNo += manager.getThreadNum();
				printFinishThreadNo(manager); // 打印当前线程完成情况
				run(); // 循环调用
			} catch (IOException e) {
				run(); // 报错之后重新爬取
			} finally {
				if(session != null) { session.close(); }
			}
		} else {
			while(!manager.getFlag(index)) {
				// 当前爬虫任务结束
				manager.setFlag(index, true);
			}
			System.err.println(index + "编号爬虫任务完成！");
			printFinishThreadNo(manager); // 打印当前线程完成情况
		}
		
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
		source = source.replaceAll("<[\\s\\S]+?>", "");
		return CodecUtil.decodeHTML(source);
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
	
	/**
	 * 字符串不能为空,否则抛出IO异常
	 * @param data [String]待判断字符串
	 * @return
	 * @throws IOException
	 */
	private static String isNotEmpty(String data) throws IOException {
		if(StringUtil.isEmpty(data)) {
			throw new IOException();
		} else {
			return data;
		}
	}
	
	/**
	 * 获取公司的详细信息
	 * @param position
	 * @throws IOException 
	 */
	private static void getPositionDetail(MobileLagouPosition position) throws IOException {
		System.err.println("爬取详情的职位编号:[" + position.getPositionId() + "]");
		String url = "https://www.lagou.com/jobs/" + position.getPositionId() + ".html";
		try {
			Proxy proxy = ProxyProvider.poll();
			// 备份代理对象
			Proxy backUpProxy = cloneProxy(proxy);
			String content = HttpUtil.httpBrowserGet(url, proxy);
			// 学历
			position.setEducation(isNotEmpty(RegexUtil.regexAString(content, 
					"<dd class=\"job_request\">[\\s]*?<p>[\\s]*?<span class=\"salary\">[\\s\\S]+?</span>[\\s]*?<span>/[\\s\\S]+? /</span>[\\s]*?<span>经验[\\s\\S]+? /</span>[\\s]*?<span>([\\s\\S]*?)[及以上]*? /</span>")));
			// 工作经验
			position.setWorkYear(isNotEmpty(RegexUtil.regexAString(content, 
					"<dd class=\"job_request\">[\\s]*?<p>[\\s]*?<span class=\"salary\">[\\s\\S]+?</span>[\\s]*?<span>/[\\s\\S]+? /</span>[\\s]*?<span>经验([\\s\\S]+?) /</span>")));
			// 职位诱惑
			position.setPositionAdvantage(RegexUtil.regexAString(content, 
					"class=\"advantage\">职位诱惑：</span>[\\s]*?<p>([\\s\\S]+?)</p>"));
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
			position.setPositionAddress(isNotEmpty(RegexUtil.regexAString(content, 
					"name=\"positionAddress\" value=\"(.*?)\" />")));
			// 经度
			position.setLat(RegexUtil.regexAString(content, 
					"name=\"positionLat\" value=\"(.*?)\""));
			// 纬度
			position.setLng(RegexUtil.regexAString(content, 
					"name=\"positionLng\" value=\"(.*?)\""));
			// 代理对象存入缓存中
			CacheProxyGetter.add(backUpProxy);
			// 城区或商区为空
//			if(StringUtil.isEmpty(position.getDistrict()) || StringUtil.isEmpty(position.getBizArea())) {
//				getCompanyDetail(position);
//			}
		} catch (IOException e) {
//			_LOG.error("获取职位 {}详细信息出错！", position.getPositionId());
			getPositionDetail(position); // 出错之后继续爬取
		}
	}
	
	/**
	 * 获得公司详细信息
	 * @param position
	 */
	private static void getCompanyDetail(MobileLagouPosition position) {
		if(position.getCompanyId() == null) {
			return;
		} else {
			System.err.println("爬取详情的公司编号:[" + position.getCompanyId() + "]");
			String url = "https://www.lagou.com/gongsi/" + position.getCompanyId() + ".html";
			try {
				Proxy proxy = ProxyProvider.poll();
				// 备份代理对象
				Proxy backUpProxy = cloneProxy(proxy);
				String content = HttpUtil.httpBrowserGet(url, proxy);
				// 城区
				position.setDistrict(RegexUtil.regexAString(content, 
						",\"district\":\"([\\s\\S]+?)\","));
				// 商区
				position.setBizArea(RegexUtil.regexAString(content, 
						",\"businessArea\":\"([\\s\\S]+?),"));
				System.err.println("城区:[" + position.getDistrict() + "] 商区:[" + position.getBizArea() + "]");
				// 代理对象存入缓存中
				CacheProxyGetter.add(backUpProxy);
			} catch (IOException e) {
				getCompanyDetail(position); // 出错之后继续爬取
			}
		}
	}

	public int getIndex() {
		return index;
	}
	
	public static void main(String[] args) throws IOException {
		ProxyProvider.execute();
		// 获取数据库会话
		SqlSession session = MybatisHelper.getSessionFactory().openSession();
		MobileLagouPositionMapper mapper = session.getMapper(MobileLagouPositionMapper.class);
		MobileLagouPositionExample example = new MobileLagouPositionExample();
		Criteria criteria = example.createCriteria();
		criteria.andCityEqualTo("广州");
		criteria.andDistrictEqualTo("");
//		criteria = example.or();
//		criteria.andCityEqualTo("广州");
//		criteria.andBizAreaEqualTo("");
		List<MobileLagouPosition> posList = mapper.selectByExample(example);
		System.out.println("共计" + posList.size() + "条记录");
		for(MobileLagouPosition pos : posList) {
			new Thread() {
				@Override
				public void run() {
					getCompanyDetail(pos);
					synchronized (this) {
						mapper.updateByPrimaryKey(pos);
						session.commit();
					}
				}
			}.start();
		}
	}
}
