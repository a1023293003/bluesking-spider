package cn.bluesking.spider.commons.helper;

import java.lang.reflect.Field;

import cn.bluesking.spider.commons.UserAgentConstant;
import cn.bluesking.spider.commons.util.MathUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 浏览器UserAgent属性助手类
 * 
 * @author 随心
 *
 */
@Slf4j
public final class UserAgentHelper {

    /** 存储所有User-Agent的数组 */
    private static String[] USER_AGENTS;

    static {
        // 反射获取 UserAgentConstant 常量接口中的常量
        Field[] fields = UserAgentConstant.class.getDeclaredFields();
        int length = fields.length;
        assert length > 0;
        USER_AGENTS = new String[length];
        for (int i = 0; i < length; i ++) {
            try {
                USER_AGENTS[i] = (String) fields[i].get(null);
            } catch (Exception e) {
                LOGGER.error("User-Agent 常量表初始化异常！" + e);
            }
        }
    }

    /**
     * 随机获取一个 User-Agent。
     * 
     * @return [String] 返回字符串格式的 User-Agent。
     */
    public static String getRandomUserAgent() {
        return USER_AGENTS[MathUtil.getRandom(0, USER_AGENTS.length)];
    }

}
