package cn.bluesking.spider.commons.helper;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * Mybatis 助手类。
 * 
 * @author 随心
 *
 */
@Slf4j
public final class MybatisHelper {

    /** Mybatis 配置文件相对路径 */
    private static final String CONFIG_PATH = "config/mybatis.xml";

    /**
     * 获取 Mybatis 的 SessionFactory 对象，该方法每次返回的都将是同一个 SessionFactory 对象。
     * 
     * @return [SqlSessionFactory] 返回 Mybatis SessionFactory 对象
     */
    public static SqlSessionFactory getSessionFactory() {
        return SessionFactoryHolder.sessionFactory;
    }

    private static class SessionFactoryHolder {
        private static SqlSessionFactory sessionFactory;
        static {
            try {
                String resource = CONFIG_PATH;
                InputStream is = MybatisHelper.class.getClassLoader()
                                                    .getResourceAsStream(resource);
                sessionFactory = new SqlSessionFactoryBuilder().build(is);
            } catch (Exception e) {
                LOGGER.error("创建 Mybatis Session Factory 失败！", e);
            }
        }
    }

}
