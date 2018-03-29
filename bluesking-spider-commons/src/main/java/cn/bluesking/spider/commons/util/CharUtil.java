package cn.bluesking.spider.commons.util;

/**
 * 字符工具类
 * 
 * @author 随心
 *
 */
public final class CharUtil {

    /** 特殊的空白字符 */
    private static final char[] SPECIAL_WHITE_SPACE_CHAR = {160};
    
    /**
     * 判断是否是空白字符
     * 
     * @param c [char]待判断字符
     * @return 如果是空白字符返回true,否则返回false
     */
    public static boolean isWhitespace(char c) {
        // \t\n\x0B\f\r
        if (Character.isWhitespace(c)) {
            return true;
        } else {
            for (char sc : SPECIAL_WHITE_SPACE_CHAR) {
                if (c == sc) {
                    return true;
                }
            }
            return false;
        }
    }
    
    /**
     * 判断字符是否是数字
     * 
     * @param c [char]待判断字符
     * @return 如果字符是数字字符则返回true,否则返回false
     */
    public static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }
    
    /**
     * 字符数组转换成封装类Character数组
     * 
     * @param chars [char[]]待转换数组
     * @return
     */
    public static Character[] toCharacterArray(char[] chars) {
        Character[] res = new Character[chars.length];
        for (int i = 0; i < chars.length; i ++) {
            res[i] = chars[i];
        }
        return res;
    }
    
}
