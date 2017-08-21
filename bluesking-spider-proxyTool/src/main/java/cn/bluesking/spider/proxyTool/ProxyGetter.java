package cn.bluesking.spider.proxyTool;

import java.net.Proxy;
import java.util.List;

/**
 * 获取代理类接口
 * 
 * @author 随心
 *
 */
public interface ProxyGetter {

	/**
	 * 任务间隔时间
	 * @return
	 */
	public Long getInterval();
	
	/**
	 * 获取代理
	 * @return
	 */
	public List<Proxy> getProxys();
	
}
