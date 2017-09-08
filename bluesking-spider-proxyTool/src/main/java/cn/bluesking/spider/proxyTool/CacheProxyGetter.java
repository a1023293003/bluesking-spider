package cn.bluesking.spider.proxyTool;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.entity.ProxyInfo;
import cn.bluesking.spider.commons.helper.MybatisHelper;
import cn.bluesking.spider.commons.mapper.ProxyInfoMapper;

/**
 * 缓存代理获取器
 * 
 * @author 随心
 *
 */
public class CacheProxyGetter implements ProxyGetter {

	/** slf4j日志配置 */
	private static final Logger _LOG = LoggerFactory.getLogger(CacheProxyGetter.class);
	
	/** 爬取间隔时间 */
	private Long interval = 4 * 60 * 1000L;
	
	/** 用于存储可用代理的阻塞队列 */
	private static final BlockingQueue<Proxy> proxyQueue = new LinkedBlockingQueue<Proxy>();
	
	@Override
	public Long getInterval() {
		return interval;
	}

	@Override
	public List<Proxy> getProxys() {
		_LOG.debug("===============进入缓存代理爬取模块===============");
		_LOG.debug("当前代理队列中元素总数:[" + ProxyProvider.size() + "]");
		if(proxyQueue.size() > 0) {
			while(proxyQueue.size() > 0) {
				try {
					ProxyProvider.add(proxyQueue.poll(2, TimeUnit.SECONDS));
				} catch (InterruptedException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}
	
	/**
	 * 把可用代理存入缓存
	 * @param proxy
	 */
	public static void add(Proxy proxy) {
		// 开启一个数据库会话
		SqlSession session = MybatisHelper.getSessionFactory().openSession();
		ProxyInfoMapper mapper = session.getMapper(ProxyInfoMapper.class);
		// 获取代理信息
		InetSocketAddress address = (InetSocketAddress) proxy.address();
		ProxyInfo proxyInfo = new ProxyInfo();
		proxyInfo.setIp(address.getAddress().getHostAddress());
		proxyInfo.setPort(address.getPort());
		proxyInfo.setStatus(0);
		// 把代理ip和端口存入数据库
		mapper.insert(proxyInfo);
		session.commit();
		session.close();
		proxyQueue.add(proxy);
	}

}
