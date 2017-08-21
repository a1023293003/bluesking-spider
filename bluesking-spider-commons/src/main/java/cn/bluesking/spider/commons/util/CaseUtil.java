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
	 * @param obj
	 * @return
	 */
	public static String caseString(Object obj) {
		return CaseUtil.caseString(obj, "");
	}
	
	/**
	 * 转为String型(提供默认值)
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static String caseString(Object obj, String defaultValue) {
		return obj != null ? String.valueOf(obj) : defaultValue;
	}
	
	/**
	 * 转为double型
	 * @param obj
	 * @return
	 */
	public static double caseDouble(Object obj) {
		return CaseUtil.caseDouble(obj, 0);
	}
	
	/**
	 * 转为double型(提供默认值)
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static double caseDouble(Object obj, double defaultValue) {
		if(obj != null) {
			String strValue = caseString(obj);
			if(StringUtil.isNotEmpty(strValue)) {
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
	 * @param obj
	 * @return
	 */
	public static long caseLong(Object obj) {
		return CaseUtil.caseLong(obj, 0);
	}
	
	/**
	 * 转为long型(提供默认值)
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static long caseLong(Object obj, long defaultValue) {
		if(obj != null) {
			String strValue = caseString(obj);
			if(StringUtil.isNotEmpty(strValue)) {
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
	 * @param obj
	 * @return
	 */
	public static int caseInt(Object obj) {
		return CaseUtil.caseInt(obj, 0);
	}
	
	/**
	 * 转为int型(提供默认值)
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static int caseInt(Object obj, int defaultValue) {
		if(obj != null) {
			String strValue = caseString(obj);
			if(StringUtil.isNotEmpty(strValue)) {
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
	 * @param obj
	 * @return
	 */
	public static boolean caseBoolean(Object obj) {
		return CaseUtil.caseBoolean(obj, false);
	}
	
	/**
	 * 转为boolean型(提供默认值)
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static boolean caseBoolean(Object obj, boolean defaultValue) {
		if(obj != null) {
			return Boolean.parseBoolean(caseString(obj));
		}
		return defaultValue;
	}
	
}
