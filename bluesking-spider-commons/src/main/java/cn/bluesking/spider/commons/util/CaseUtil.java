package cn.bluesking.spider.commons.util;

/**
 * 转换操作工具类
 * 
 * @author 随心
 *
 */
public final class CaseUtil {

    /**
     * 转为String型
     * 
     * @param obj [Object]待转换为字符串的对象
     * @return [String]尝试把传入对象转换成字符串,转换成功方法返回转换后的字符串,转换失败则返回默认值""
     */
    public static String caseString(Object obj) {
        return CaseUtil.caseString(obj, "");
    }

    /**
     * 转为String型(提供默认值)
     * 
     * @param obj          [Object]待转换为字符串的对象
     * @param defaultValue [String]默认值
     * @return [String]尝试把传入对象转换成字符串,转换成功方法返回转换后的字符串,转换失败则返回默认值
     */
    public static String caseString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为double型
     * 
     * @param obj [Object]待转换为双精度浮点数值的对象
     * @return [double]尝试把传入对象转换成double类型,转换成功方法返回转换后的数值,转换失败则返回默认值0
     */
    public static double caseDouble(Object obj) {
        return CaseUtil.caseDouble(obj, 0);
    }

    /**
     * 转为double型(提供默认值)
     * 
     * @param obj          [Object]待转换为双精度浮点数值的对象
     * @param defaultValue [double]默认值
     * @return [double]尝试把传入对象转换成double类型,转换成功方法返回转换后的数值,转换失败则返回默认值
     */
    public static double caseDouble(Object obj, double defaultValue) {
        if (obj != null) {
            String strValue = caseString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    return Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    /**
     * 转为long型
     * 
     * @param obj [Object]待转换为长整型数值的对象
     * @return [long]尝试把传入对象转换成long类型,转换成功方法返回转换后的数值,转换失败则返回默认值0
     */
    public static long caseLong(Object obj) {
        return CaseUtil.caseLong(obj, 0);
    }

    /**
     * 转为long型(提供默认值)
     * 
     * @param obj          [Object]待转换为长整型数值的对象
     * @param defaultValue [long]默认值
     * @return [long]尝试把传入对象转换成long类型,转换成功方法返回转换后的数值,转换失败则返回默认值
     */
    public static long caseLong(Object obj, long defaultValue) {
        if (obj != null) {
            String strValue = caseString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    return Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    /**
     * 转为int型
     * 
     * @param obj [Object]待转换为整型数值的对象
     * @return [long]尝试把传入对象转换成int类型,转换成功方法返回转换后的数值,转换失败则返回默认值0
     */
    public static int caseInt(Object obj) {
        return CaseUtil.caseInt(obj, 0);
    }

    /**
     * 转为int型(提供默认值)
     * 
     * @param obj          [Object]待转换为整型数值的对象
     * @param defaultValue [int]默认值
     * @return [long]尝试把传入对象转换成long类型,转换成功方法返回转换后的数值,转换失败则返回默认值
     */
    public static int caseInt(Object obj, int defaultValue) {
        if (obj != null) {
            String strValue = caseString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    return Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    /**
     * 转为boolean型
     * 
     * @param obj [Object]待转换为布尔值的对象
     * @return [long]尝试把传入对象转换成布尔值,转换成功方法返回转换后的布尔值,转换失败则返回默认值false
     */
    public static boolean caseBoolean(Object obj) {
        return CaseUtil.caseBoolean(obj, false);
    }

    /**
     * 转为boolean型(提供默认值)
     * 
     * @param obj          [Object]待转换为布尔值的对象
     * @param defaultValue [boolean]默认值
     * @return [long]尝试把传入对象转换成布尔值,转换成功方法返回转换后的布尔值,转换失败则返回默认值
     */
    public static boolean caseBoolean(Object obj, boolean defaultValue) {
        if (obj != null) {
            return Boolean.parseBoolean(caseString(obj));
        }
        return defaultValue;
    }

}
