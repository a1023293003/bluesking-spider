package cn.bluesking.spider.proxyTool.helper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.util.CaseUtil;
import cn.bluesking.spider.commons.util.CollectionUtil;
import cn.bluesking.spider.commons.util.HttpUtil;
import cn.bluesking.spider.proxyTool.ProxyProvider;

/**
 * 代理助手类
 * 
 * @author 随心
 *
 */
public final class ProxyHelper {

	/** slf4j日志配置 */
	private static final Logger _LOG = LoggerFactory.getLogger(ProxyHelper.class);
	
	/**
	 * 获取有效的代理列表
	 * @param ips [List<String>]ip列表
	 * @param ports [List<String>]端口列表
	 * @return
	 */
	public static <T> List<Proxy> getValidProxy(List<String> ips, List<T> ports) {
		// 传入ip和端口列表不为空且元素数目相等
		if(CollectionUtil.isEmpty(ips) || 
				CollectionUtil.isEmpty(ports) || 
				ips.size() != ports.size()) return null;
		List<Proxy> proxys = new ArrayList<Proxy>(ips.size());
		// 遍历验证代理对象
		Proxy proxy;
		for(int i = 0; i < ips.size(); i ++) {
			// 测试代理,端口不和规格默认使用8080
			proxy = new Proxy(Proxy.Type.HTTP, 
					new InetSocketAddress(ips.get(i), CaseUtil.caseInt(ports.get(i), 8080)));
			try {
				// 测试代理
				HttpUtil.testConnection("https://www.baidu.com/", proxy);
				_LOG.debug("可用代理:[" + ips.get(i) + ":" + ports.get(i) + "]");
				ProxyProvider.add(proxy);
				proxys.add(proxy);
			} catch (IOException e) {
				_LOG.debug("连接失败,代理不可用!");
			}
		}
		return proxys;
	}
	
}
