package cn.bluesking.spider.commons.helper;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.UserAgentConstant;
import cn.bluesking.spider.commons.util.MathUtil;

/**
 * 浏览器UserAgent属性助手类
 * 
 * @author 随心
 *
 */
public final class UserAgentHelper {

    /** slf4j日志配置 */
    private static final Logger _LOG = LoggerFactory.getLogger(UserAgentHelper.class);

    /** 存储所有User-Agent的数组 */
    private static String[] userAgents = null;

    /** User-Agent最大下标 */
    private static int length = 0;

    static {
        // 反射获取UserAgentConstant常量接口中的常量
        Field[] fields = UserAgentConstant.class.getDeclaredFields();
        length = fields.length - 1; // 计算数组最大下标
        userAgents = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            try {
                userAgents[i] = (String) fields[i].get(null);
            } catch (Exception e) {
                _LOG.error("User-Agent常量表初始化异常" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 随机获取一个User-Agent
     * 
     * @return
     */
    public static String getRandomUserAgent() {
        return userAgents[MathUtil.getRandom(0, length)];
    }

}
