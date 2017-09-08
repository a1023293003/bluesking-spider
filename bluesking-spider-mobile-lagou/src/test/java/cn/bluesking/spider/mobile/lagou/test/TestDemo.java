package cn.bluesking.spider.mobile.lagou.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.bluesking.spider.commons.entity.ProxyInfo;
import cn.bluesking.spider.commons.helper.MybatisHelper;
import cn.bluesking.spider.commons.mapper.ProxyInfoMapper;

public class TestDemo {

	public static void main(String[] args) {
		SqlSession session = MybatisHelper.getSessionFactory().openSession();
		ProxyInfoMapper mapper = session.getMapper(ProxyInfoMapper.class);
		List<ProxyInfo> proxys = mapper.selectTopProxys(20);
		for(ProxyInfo proxy : proxys) {
			System.out.println(proxy.toString());
		}
		session.commit();
		session.close();
	}
	
}
