package cn.bluesking.spider.commons.util;

/**
 * 字符串工具类
 * <pre>
 * 小写字母的ASCII码范围 : 97(a) - 122(z)
 * 大写字母的ASCII码范围 : 65(A) - 90(Z)
 * 数字的ASCII码取值范围 : 48(0) - 57(9)
 * <pre>
 * 
 * @author 随心
 *
 */
public final class StringUtil {

	/**
	 * 分隔符
	 */
	public static final String SEPARATOR = String.valueOf((char) 29);
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static final boolean isEmpty(String str) {
		if(str != null) {
			str = str.trim();
		}
		return str == null || str.length() <= 0;
	}
	
	/**
	 * 判断字符串是否非空
	 * @param str
	 * @return
	 */
	public static final boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	 * 判断字符是否是小写字母
	 * @param c [char]带判断字符
	 * @return
	 */
	public static final boolean isLowerLetter(char c) {
		return c >= 'a' && c <= 'z';
	}
	
	/**
	 * 判断字符是否不是小写字母
	 * @param c [char]带判断字符
	 * @return
	 */
	public static final boolean isNotLowerLetter(char c) {
		return !isLowerLetter(c);
	}
	
	/**
	 * 字符转换成小写字母
	 * @param c [char]带转换字符
	 * @return
	 */
	public static final char toLowerLetter(char c) {
		return isUpperLetter(c) ? (char) (c + 32) : c;
	}
	
	/**
	 * 判断字符是否是大写字母
	 * @param c [char]带判断字符
	 * @return
	 */
	public static final boolean isUpperLetter(char c) {
		return c >= 'A' && c <= 'Z';
	}
	
	/**
	 * 判断字符是否不是大写字母
	 * @param c [char]带判断字符
	 * @return
	 */
	public static final boolean isNotUpperLetter(char c) {
		return !isUpperLetter(c);
	}
	
	/**
	 * 字符转换成大写字母
	 * @param c [char]带转换字符
	 * @return
	 */
	public static final char toUpperLetter(char c) {
		return isLowerLetter(c) ? (char) (c - 32) : c;
	}
	
	/**
	 * 判断字符是否是字母
	 * @param c [char]带判断字符
	 * @return
	 */
	public static final boolean isLetter(char c) {
		return isLowerLetter(c) || isUpperLetter(c);
	}
	
	/**
	 * 判断字符是否不是字母
	 * @param c [char]带判断字符
	 * @return
	 */
	public static final boolean isNotLetter(char c) {
		return !isLetter(c);
	}
	
	/**
	 * 判断字符是否是数字
	 * @param c [char]带判断字符
	 * @return
	 */
	public static final boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}
	
	/**
	 * 判断字符是否不是数字
	 * @param c [char]带判断字符
	 * @return
	 */
	public static final boolean isNotNumber(char c) {
		return !isNumber(c);
	}
	
	/**
	 * 首字母大写
	 * @param str [String]待转换字符串
	 * @return [String]首字母大写之后的字符串
	 */
	public static String capitalFirstChar(String str) {
		if(str == null) return null;
		char[] s = str.toCharArray();
		// 首字母是小写字母
		if(s[0] >= 97 && s[0] <= 122) {
			// s[0] -= 32;这里可以用位运算代替
			s[0] ^= 32;
		}
		return String.valueOf(s);
	}
	
}
