package cn.bluesking.spider.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具类
 * 
 * @author 随心
 *
 */
public final class JsonUtil {

	/**
	 * slf4j日志配置
	 */
	private static final Logger _LOG = LoggerFactory.getLogger(JsonUtil.class);
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	static {
		// 主动忽略掉po类中没有的字段映射
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	/**
	 * 将POJO转为JSON
	 * @param obj
	 * @return
	 */
	public static <T> String toJson(T obj) {
		String json;
		try {
			json = OBJECT_MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			_LOG.error("POJO转为JSON数据失败！", e);
			throw new RuntimeException(e);
		}
		return json;
	}
	
	/**
	 * 将JSON转为POJO
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> type) {
		T pojo;
		try {
			pojo = OBJECT_MAPPER.readValue(json, type);
		} catch (Exception e) {
			_LOG.error("JSON转为POJO失败！", e);
			throw new RuntimeException(e);
		}
		return pojo;
	}

}
