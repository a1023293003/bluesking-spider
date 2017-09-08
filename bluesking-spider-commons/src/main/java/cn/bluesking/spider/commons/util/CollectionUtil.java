package cn.bluesking.spider.commons.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 集合工具类
 * 
 * @author 随心
 *
 */
public final class CollectionUtil {

	/**
	 * 判断Collection是否为空
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.size() <= 0;
	}
	
	/**
	 * 判断Collection是否非空
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}
	
	/**
	 * 判断Map是否为空
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.size() <= 0;
	}
	
	/**
	 * 判断Map是否非空
	 * @param map
	 * @return
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}
	
}
