package cn.bluesking.spider.proxyTool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.util.CaseUtil;
import cn.bluesking.spider.commons.util.HttpUtil;
import cn.bluesking.spider.commons.util.RegexUtil;

/**
 * 虫代理获取器
 * 
 * @author 随心
 *
 */
public class BugngProxyGetter implements ProxyGetter {

	/** slf4j日志配置 */
	private static final Logger _LOG = LoggerFactory.getLogger(BugngProxyGetter.class);
	
	/** 爬取间隔时间 */
	private Long interval = 4 * 60 * 1000L;
	
	@Override
	public Long getInterval() {
		return interval;
	}
	
   /** 每次从数据库获取代理数量 */
    private static final int PROXY_NUM = 50;

	@Override
	public List<Proxy> getProxys() {
		try {
			_LOG.debug("===============进入虫代理爬取模块===============");
			_LOG.debug("当前代理队列中元素总数:[" + ProxyProvider.size() + "]");
			String content = HttpUtil.httpBrowserGet("http://www.bugng.com/api/getproxy/txt?num=" + PROXY_NUM + "&anonymity=1&type=0");
			List<String> ips = RegexUtil.regexString(content, "(\\d+?.\\d+?.\\d+?.\\d+?):");
			List<String> ports = RegexUtil.regexString(content, ":(\\d+?)@");
			for(int i = 0; i < ips.size(); i++) {
				ProxyProvider.add(new Proxy(Proxy.Type.HTTP, 
					new InetSocketAddress(ips.get(i), CaseUtil.caseInt(ports.get(i), 8080))));
			}
			return null;
//			return ProxyHelper.getValidProxy(ips, ports);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
