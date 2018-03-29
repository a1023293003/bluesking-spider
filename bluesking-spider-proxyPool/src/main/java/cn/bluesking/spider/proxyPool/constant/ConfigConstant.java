package cn.bluesking.spider.proxyPool.constant;

/**
 * 提供相关配置项常量
 * 
 * @author 随心
 *
 */
public interface ConfigConstant {

    /** 配置文件 */
    String CONFIG_FILE = "config.properties";
    
    /** 项目基础包名 */
    String APP_BASE_PACKAGE = "cn.bluesking.api.spider";
    
    /** redis服务器ip */
    String redisServerHost = "proxyPool.redis.server.host";
    
    /** redis服务器端口 */
    String redisServerPort = "proxyPool.redis.server.port";
    
    /** redis连接池最大连接数 */
    String redisPoolMaxTotal = "proxyPool.redis.connectionPool.maxTotal";
    
    /** redis连接池最大空闲数 */
    String redisPoolMaxIdle = "proxyPool.redis.connectionPool.maxIdle";
    
}
