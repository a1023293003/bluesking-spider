package cn.bluesking.spider.commons.util;

import java.util.Map;

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
	
	/**
	 * 初始化布尔数组
	 * @param flag [boolean[]]带初始化数组
	 * @param defaultValue [boolean]默认值
	 */
	public static void setAllValue(boolean[] flag, boolean defaultValue) {
		for(int i = 0; i < flag.length; i ++) {
			flag[i] = defaultValue;
		}
	}
	
	/**
	 * 判断布尔数组是否包含指定值
	 * @param array [boolean[]]待判断数组
	 * @param value [boolean]指定值
	 * @return
	 */
	public static boolean ContainsValue(boolean[] array, boolean value) {
		for(int i = 0; i < array.length; i ++) {
			if(array[i] == value) {
				return true;
			}
		}
		return false;
	}

}
