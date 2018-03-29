package cn.bluesking.spider.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性文件工具类
 * 
 * @author 随心
 *
 */
public final class PropsUtil {
    
    /** slf4j日志配置 */
    private static final Logger _LOG = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 读取配置文件
     * 
     * @param path [String]配置文件路径
     * @return [ConfigParse]配置文件对象
     */
    public static ConfigParser loadProps(String path) {
        try {
            return new ConfigParser(path);
        } catch (Exception e) {
            _LOG.info("读取配置文件失败 ！", e);
            return null;
        }
    }
}
