package cn.bluesking.spider.commons.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * JSON工具类
 * 
 * @author 随心
 *
 */
@Slf4j
public final class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 主动忽略掉po类中没有的字段映射
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将POJO转为JSON
     * 
     * @param obj [T]待转换的POJO对象
     * @return [String]如果POJO对象转换成JSON格式的字符串成功方法直接返回该字符串,否则方法抛出运行异常
     */
    public static <T> String toJson(T obj) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error("POJO转为JSON数据失败！", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将JSON转为POJO
     * 
     * @param json [String]待转换的JSON格式的字符串
     * @param type [Class<T>]JSON格式字符串待转换成的POJO类的Class对象
     * @return [T]如果JSON格式字符串成功转换成POJO类方法直接返回该POJO类,否则方法抛出运行异常
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (Exception e) {
            LOGGER.error("JSON转为POJO失败！", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }

}
