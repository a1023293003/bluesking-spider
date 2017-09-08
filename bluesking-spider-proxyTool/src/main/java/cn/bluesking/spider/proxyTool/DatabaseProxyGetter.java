package cn.bluesking.spider.proxyTool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.entity.ProxyInfo;
import cn.bluesking.spider.commons.helper.MybatisHelper;
import cn.bluesking.spider.commons.mapper.ProxyInfoMapper;
import cn.bluesking.spider.commons.util.CaseUtil;
import cn.bluesking.spider.commons.util.HttpUtil;
import cn.bluesking.spider.commons.util.RegexUtil;

/**
 * 数据库代理获取器
 * 
 * @author 随心
 *
 */
public class DatabaseProxyGetter implements ProxyGetter {

	/** slf4j日志配置 */
	private static final Logger _LOG = LoggerFactory.getLogger(DatabaseProxyGetter.class);
	
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
		SqlSession session = null;
		try {
			_LOG.debug("===============进入数据库缓存代理获取模块===============");
			_LOG.debug("当前代理队列中元素总数:[" + ProxyProvider.size() + "]");
			session = MybatisHelper.getSessionFactory().openSession();
			ProxyInfoMapper mapper = session.getMapper(ProxyInfoMapper.class);
			List<ProxyInfo> proxys = mapper.selectTopProxys(PROXY_NUM);
			for(ProxyInfo proxy : proxys) {
				ProxyProvider.add(new Proxy(Proxy.Type.HTTP, 
						new InetSocketAddress(proxy.getIp(), proxy.getPort())));
			}
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) {
				session.close();
			}
		}
		return null;
	}

}
