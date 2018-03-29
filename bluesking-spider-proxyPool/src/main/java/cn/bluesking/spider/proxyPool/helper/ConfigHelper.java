package cn.bluesking.spider.proxyPool.helper;

import cn.bluesking.spider.commons.util.CaseUtil;
import cn.bluesking.spider.commons.util.ConfigParser;
import cn.bluesking.spider.commons.util.PropsUtil;
import cn.bluesking.spider.proxyPool.constant.ConfigConstant;

/**
 * 属性文件助手类
 * 
 * @author 随心
 *
 */
public final class ConfigHelper {

    /** 配置文件 */
    private static final ConfigParser CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);
    
    /**
     * 获取redis服务器ip
     * 
     * @return
     */
    public static String getRedisServerHost() {
        return CONFIG_PROPS.getValue(ConfigConstant.redisServerHost);
    }
    
    /**
     * 获取redis服务器端口
     * 
     * @return
     */
    public static int getRedisServerPort() {
        return CaseUtil.caseInt(CONFIG_PROPS.getValue(ConfigConstant.redisServerPort), 6379);
    }
    
    /**
     * 获取redis连接池最大连接数
     * 
     * @return
     */
    public static int getRedisPoolMaxTotal() {
        return CaseUtil.caseInt(CONFIG_PROPS.getValue(ConfigConstant.redisPoolMaxTotal), 30);
    }
    
    /**
     * 获取redis连接池最大空闲连接数
     * 
     * @return
     */
    public static int getRedisPoolMaxIdle() {
        return CaseUtil.caseInt(CONFIG_PROPS.getValue(ConfigConstant.redisPoolMaxIdle), 10);
    }
    
}
