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
 * 66免费ip代理
 * 
 * @author 随心
 *
 */
public class SixSixProxyGetter implements ProxyGetter {

	/** slf4j日志配置 */
	private static final Logger _LOG = LoggerFactory.getLogger(SixSixProxyGetter.class);
	
	/** 爬取间隔时间 */
	private Long interval = 4 * 60 * 1000L;
	
	public Long getInterval() {
		return interval;
	}

	public List<Proxy> getProxys() {
		try {
			_LOG.debug("===============进入66代理爬取模块===============");
			_LOG.debug("当前代理队列中元素总数:[" + ProxyProvider.size() + "]");
			String content = HttpUtil.httpBrowserGet("http://www.66ip.cn/nmtq.php?getnum=20&isp=0&anonymoustype=0&start=&ports=&export=&ipaddress=&area=1&proxytype=2&api=66ip");
			List<String> ips = RegexUtil.regexString(content, "(\\d+?.\\d+?.\\d+?.\\d+?):");
			List<String> ports = RegexUtil.regexString(content, ":(\\d+?)");
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
