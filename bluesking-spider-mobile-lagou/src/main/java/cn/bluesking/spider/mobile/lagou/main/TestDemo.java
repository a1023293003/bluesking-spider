package cn.bluesking.spider.mobile.lagou.main;

import java.io.IOException;

import cn.bluesking.spider.commons.util.StringUtil;

public class TestDemo {

	/**
	 * 规格化UserAgent的名称
	 * <pre>
	 * 去掉空格,括号,单引号,小数点,感叹号
	 * 空格后一个字母大写
	 * </pre>
	 * @param data [String]带规格化字符串
	 * @return
	 */
	private static String getStr(String data) {
		char[] cs = data.toCharArray();
		int outLen = 0;
		char c;
		boolean lastCharIsSpace = false;
		// 首字母大写
		if(cs.length >= 1) cs[0] = StringUtil.toUpperLetter(cs[0]);
		for(int i = 0; i < cs.length; i ++) {
			c = cs[i];
			if(lastCharIsSpace) { // 空格后一个字母大写
				c = StringUtil.toUpperLetter(c);
				lastCharIsSpace = false;
			}
			if(c == '.' || c == '-') c = '_';
			cs[outLen] = c;
			if(c != ' ' && c != '(' && c != ')' && c != '\'' && c != '!') {
				outLen ++; // 过滤掉空格,括号和单引号
			} else if(c == ' ') {
				lastCharIsSpace = true;
			}
		}
		return new String(cs, 0, outLen);
	}
	
	public static void main(String[] args) throws IOException {
//		String url = "https://m.lagou.com/search.json?city=%E4%B8%8A%E6%B5%B7&positionName=Java&pageNo=1&pageSize=15";
//		try {
//			String content = HttpUtil.httpBrowserGet(url);
//			System.out.println(content);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		// 获取和生成user-agent表
//		String content = HttpUtil.httpBrowserGet("http://www.cnblogs.com/VAllen/articles/UserAgentList.html");
//		System.out.println(content);
//		List<String> keys = RegexUtil.regexString(content, "<td[\\s\\S]*?>([\\s\\S]*?)</td><td>");
//		List<String> values = RegexUtil.regexString(content, "</td><td>([\\s\\S]*?)</td>");
//		for(int i = 0; i < keys.size(); i ++) {
//			keys.set(i, CodecUtil.decodeHTML(keys.get(i)));
//			values.set(i, CodecUtil.decodeHTML(values.get(i)));
//			System.out.println("	public static final String " + getStr(keys.get(i)) + " = \"" + values.get(i) + "\";\n");
//		}
	}
}
