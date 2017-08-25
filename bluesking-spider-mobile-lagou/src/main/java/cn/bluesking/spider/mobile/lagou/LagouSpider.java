package cn.bluesking.spider.mobile.lagou;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.util.CaseUtil;
import cn.bluesking.spider.commons.util.CodecUtil;
import cn.bluesking.spider.commons.util.HttpUtil;
import cn.bluesking.spider.commons.util.RegexUtil;
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
		getJobs("广州", "Java");
	}
	
	/** 线程数 */
	private static final int THREAD_NUM = 10;
	
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
		// 开启线程池
		ProxyProvider.execute();
		// url前缀
		String urlPrefix = "https://m.lagou.com/search.json?city=" + 
				CodecUtil.encodeURL(city) + "&positionName=" + 
				CodecUtil.encodeURL(keyword);
		// 获取数据总数
		int totalSize = getTotalSize(city, keyword);
		_LOG.debug("数据总数为:" + totalSize);
		int lastPageNo = (int) Math.ceil(totalSize / 15.0);
		LagouThreadManager manager = new LagouThreadManager(THREAD_NUM, lastPageNo, urlPrefix, 5);
		// 结束标记全为true
		while(manager.isNotEnd()) {
			Thread.yield();
		}
		ProxyProvider.interrupt();
		System.err.println("爬取结束！");
	}
	
}
