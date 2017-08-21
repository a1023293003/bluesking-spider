package cn.bluesking.spider.commons.util;

/**
 * 数学方法工具类
 * 
 * @author 随心
 *
 */
public final class MathUtil {

	/**
	 * 获取一个介于start到end之间的随机数,包含start和end
	 * @param start [int]起点
	 * @param end [int]终点
	 * @return
	 */
	public static int getRandom(int start, int end) {
		return (int) (start + Math.random() * (end - start));
	}
	
}
