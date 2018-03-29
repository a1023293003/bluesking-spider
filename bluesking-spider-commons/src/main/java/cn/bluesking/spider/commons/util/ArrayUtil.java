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
     * 
     * @param objs [Object[]]待判断数组
     * @return [boolean]如果传入数组为null或数组长度为0则该方法返回true,否则该方法返回false
     */
    public static boolean isEmpty(Object[] objs) {
        return objs == null || objs.length <= 0;
    }
    
    /**
     * 判断数组是否非空
     * @param objs [Object[]]待判断数组
     * @return [boolean]如果传入数组不为null且数组长度大于0则该方法返回true,否则该方法返回false
     */
    public static boolean isNotEmpty(Object[] objs) {
        return !isEmpty(objs);
    }

    /**
     * 初始化数组元素值
     * 
     * @param array        [T[]]待初始化数组
     * @param defaultValue [T]初始值
     * @return [T[]]初始化之后的数组
     */
    public static <T> T[] setDefaultValues(T[] array, T defaultValue) {
        if (isEmpty(array)) {
            return array;
        } else {
            for (int i = 0; i < array.length; i ++) {
                array[i] = defaultValue;
            }
            return array;
        }
    }

    /**
     * 查询并返回指定元素第一次出现在数组中的下标
     * 
     * @param array   [T[]]数组
     * @param element [T]待查询元素
     * @return 方法将返回元素在数组中第一次出现的位置,如果元素未曾出现在数组中,方法将返回-1
     */
    public static <T> int indexOf(T[] array, T element) {
        if (isEmpty(array)) {
            return -1;
        } else {
            for (int i = 0; i < array.length; i ++) {
                if (array[i] == element) {
                    return i;
                }
            }
            return -1;
        }
    }

}
