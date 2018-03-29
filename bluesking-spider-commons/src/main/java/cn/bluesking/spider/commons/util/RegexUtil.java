package cn.bluesking.spider.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配工具类
 * 
 * @author 随心
 *
 */
public final class RegexUtil {

    /**
     * 正则匹配截取指定内容
     * 
     * @param str        [String]总内容
     * @param patternStr [String]正则表达式
     * @return [ArrayList<String>]指定内容
     */
    public static ArrayList<String> regexString(String str, String patternStr) {
        return regexString(str, patternStr, 1);
    }
    
    /**
     * 正则匹配截取第一条指定内容
     * @param str        [String]总内容
     * @param patternStr [String]正则表达式
     * @return [String]指定内容
     */
    public static String regexAString(String str, String patternStr) {
        List<String> res = regexString(str, patternStr, 1);
        if(CollectionUtil.isNotEmpty(res)) {
            return res.get(0);
        } else {
            return "";
        }
    }
    
    /**
     * 正则匹配截取指定内容
     * 
     * @param str        [String]总内容
     * @param patternStr [String]正则表达式
     * @param group      [int]取出正则表达式第几组数据
     * @return [ArrayList<String>]指定内容
     */
    public static ArrayList<String> regexString(String str, String patternStr, int group) {
        // 预定义一个ArrayList来存储结果
        ArrayList<String> results = new ArrayList<String>();
        // 定义正则匹配规则
        Pattern pattern = Pattern.compile(patternStr);
        // 定义一个matcher来做匹配
        Matcher matcher = pattern.matcher(str);
        // 如果找到了
        boolean isFind = matcher.find();
        // 使用循环将句子里符合规则的子序列截取出来，存储到results里
        while(isFind) {
            if (group == -1) {
                results.add(matcher.group());
            } else {
                results.add(matcher.group(group));
            }
            // 更新标记
            isFind = matcher.find();
        }
        // 返回找到的结果
        return results;
    }
    
    /**
     * 判断某个正则是否能匹配到结果
     * 
     * @param str        [String]待匹配内容
     * @param patternStr [String]正则表达式
     * @return [boolean]true : 存在匹配, false : 不存在匹配
     */
    public static boolean exist(String str, String patternStr) {
        return Pattern.compile(patternStr).matcher(str).find();
    }
    
}
