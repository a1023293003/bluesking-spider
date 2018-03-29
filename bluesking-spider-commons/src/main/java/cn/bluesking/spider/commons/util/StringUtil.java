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

    /** 分隔符 */
    public static final String SEPARATOR = String.valueOf((char) 29);
    
    /**
     * 判断字符串是否为空
     * 
     * @param str [String]待判断字符串
     * @return [boolean]如果字符串为空方法返回true,否则方法返回false
     */
    public static final boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return str == null || str.length() <= 0;
    }
    
    /**
     * 判断字符串是否非空
     * 
     * @param str [String]待判断字符串
     * @return [boolean]如果字符串不为空方法返回true,否则方法返回false
     */
    public static final boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * 判断字符是否是小写字母
     * 
     * @param c [char]待判断字符
     * @return [boolean]如果字符是小写字母方法返回true,否则方法返回false
     */
    public static final boolean isLowerLetter(char c) {
        return c >= 'a' && c <= 'z';
    }
    
    /**
     * 判断字符是否不是小写字母
     * 
     * @param c [char]待判断字符
     * @return [boolean]如果字符不是小写字母方法返回true,否则方法返回false
     */
    public static final boolean isNotLowerLetter(char c) {
        return !isLowerLetter(c);
    }
    
    /**
     * 字符转换成小写字母
     * 
     * @param c [char]待转换字符
     * @return [char]如果字符是大写字母则方法返回对应的小写字母,否则方法直接返回传入字符
     */
    public static final char toLowerLetter(char c) {
        return isUpperLetter(c) ? (char) (c + 32) : c;
    }
    
    /**
     * 判断字符是否是大写字母
     * 
     * @param c [char]待判断字符
     * @return [boolean]如果字符是大写字母方法返回true,否则方法返回false
     */
    public static final boolean isUpperLetter(char c) {
        return c >= 'A' && c <= 'Z';
    }
    
    /**
     * 判断字符是否不是大写字母
     * 
     * @param c [char]待判断字符
     * @return [boolean]如果字符不是大写字母方法返回true,否则方法返回false
     */
    public static final boolean isNotUpperLetter(char c) {
        return !isUpperLetter(c);
    }
    
    /**
     * 字符转换成大写字母
     * 
     * @param c [char]待转换字符
     * @return [char]如果字符是小写字母则方法返回对应的大写字母,否则方法直接返回传入字符
     */
    public static final char toUpperLetter(char c) {
        return isLowerLetter(c) ? (char) (c - 32) : c;
    }
    
    /**
     * 判断字符是否是字母
     * 
     * @param c [char]待判断字符
     * @return [char]如果字符是字母则方法返回true,否则方法返回false
     */
    public static final boolean isLetter(char c) {
        return isLowerLetter(c) || isUpperLetter(c);
    }
    
    /**
     * 判断字符是否不是字母
     * 
     * @param c [char]待判断字符
     * @return [char]如果字符不是字母则方法返回true,否则方法返回false
     */
    public static final boolean isNotLetter(char c) {
        return !isLetter(c);
    }
    
    /**
     * 判断字符是否是数字
     * 
     * @param c [char]待判断字符
     * @return [char]如果字符是数字则方法返回true,否则方法返回false
     */
    public static final boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }
    
    /**
     * 判断字符是否不是数字
     * 
     * @param c [char]待判断字符
     * @return[char]如果字符不是数字则方法返回true,否则方法返回false
     */
    public static final boolean isNotNumber(char c) {
        return !isNumber(c);
    }
    
    /**
     * 首字母大写
     * 
     * @param str [String]待转换字符串
     * @return [String]首字母大写之后的字符串
     */
    public static String capitalFirstChar(String str) {
        if (str == null) return null;
        char[] s = str.toCharArray();
        // 首字母是小写字母
        if (s[0] >= 97 && s[0] <= 122) {
            // s[0] -= 32;这里可以用位运算代替
            s[0] ^= 32;
        }
        return String.valueOf(s);
    }
    
    /**
     * 判断两个字符串是否相等
     * 
     * @param s1 [String]字符串1
     * @param s2 [String]字符串2
     * @return [boolean]如果两个字符串内容相同方法返回true,否则方法返回false
     */
    public static boolean equals(String s1, String s2) {
        if (s1 == null) {
            return s2 == null;
        } else {
            return s1.equals(s2);
        }
    }
    
    /**
     * 滤掉字符串前后不可见的空格
     * 
     * @param str [String]待处理字符串
     * @return [String]方法执行成功将返回已经过滤掉前导和后导不可见字符的字符串
     */
    public static String trim(String str) {
        if (str == null) {
            return null;
        } else if (str.length() == 0) {
            return str;
        } else {
            int start = 0;
            int len = str.length();
            char[] value = str.toCharArray();
            while (start < len && CharUtil.isWhitespace(value[start])) {
                start ++;
            }
            while (start < len && CharUtil.isWhitespace(value[len - 1])) {
                len --;
            }
            return str.substring(start, len);
        }
    }
    
    /**
     * 拼接字符串方法
     * 
     * @param obj [Object...]待拼接对象集合
     * @return [String]方法执行成功将返回所有对象拼接起来的字符串,当传入的对象均为null或空的时候,方法返回""
     */
    public static String concat(Object... objs) {
        StringBuilder buf = new StringBuilder("");
        for (Object obj : objs) {
            if (obj != null) {
                buf.append(CaseUtil.caseString(obj, ""));
            }
        }
        return buf.toString();
    }
    
}
