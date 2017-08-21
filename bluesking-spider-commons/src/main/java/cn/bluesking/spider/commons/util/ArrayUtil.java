package cn.bluesking.spider.commons.util;

/**
 * 数组工具类
 * 
 * @author 随心
 *
 */
public final class ArrayUtil {

	/**
	 * 判断数组是否为空
	 * @param objs [Object[]]数组
	 * @return
	 */
	public static boolean isEmpty(Object[] objs) {
		return objs == null || objs.length <= 0;
	}
	
	/**
	 * 判断数组是否非空
	 * @param objs
	 * @return
	 */
	public static boolean isNotEmpty(Object[] objs) {
		return !isEmpty(objs);
	}
}
