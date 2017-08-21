package cn.bluesking.spider.commons.helper;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * mybatis助手类
 * 
 * @author 随心
 *
 */
public final class MybatisHelper {

	/** 配置文件路径 */
	private static final String CONFIG_PATH = "mybatis/conf.xml";
	
	/** mybatis会话工厂 */
	private static SqlSessionFactory sessionFactory = null;
	
	/**
	 * 获取session工厂
	 */
	public static SqlSessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			String resource = CONFIG_PATH;
			InputStream is = MybatisHelper.class.getClassLoader().getResourceAsStream(resource);
			sessionFactory = new SqlSessionFactoryBuilder().build(is);
		}
		return sessionFactory;
	}
	
}
