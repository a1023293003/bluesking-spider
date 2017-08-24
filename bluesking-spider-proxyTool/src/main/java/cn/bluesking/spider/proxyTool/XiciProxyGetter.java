package cn.bluesking.spider.proxyTool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.util.CaseUtil;
import cn.bluesking.spider.commons.util.HttpUtil;
import cn.bluesking.spider.commons.util.RegexUtil;
import cn.bluesking.spider.proxyTool.helper.ProxyHelper;

/**
 * 西刺代理获取器
 * 
 * @author 随心
 *
 */
public class XiciProxyGetter implements ProxyGetter {

	/** slf4j日志配置 */
	private static final Logger _LOG = LoggerFactory.getLogger(XiciProxyGetter.class);
	
	/** 爬取间隔时间 */
	private Long interval = 5 * 60 * 1000L;
	
	@Override
	public Long getInterval() {
		return interval;
	}

	@Override
	public List<Proxy> getProxys() {
		try {
			_LOG.debug("===============进入西刺代理爬取模块===============");
			String content = HttpUtil.httpBrowserGet("http://www.xicidaili.com/nn/");
			List<String> ips = RegexUtil.regexString(content, "<td>(\\d+?(.\\d+?){3})</td>");
			List<String> ports = RegexUtil.regexString(content, "<td>(\\d+?)</td>");
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

	public static void main(String[] args) {
		ProxyProvider.execute();
	}
}
