package cn.bluesking.spider.mobile.lagou.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.bluesking.spider.commons.util.CodecUtil;
import cn.bluesking.spider.commons.util.HttpUtil;

public class Main {
	
	String test = null;
	
	public static int gcd(int a, int b) {
		return b > 0 ? gcd(b, a % b) : a;
	}
	
	public static void main(String[] args) {
		String city = "广州";
		String district = "天河区";
		String kd = "Java";
		// 请求头参数
		String referer = "https://www.lagou.com/jobs/list_" + kd + "?px=new&city="  + CodecUtil.encodeURL(city) + 
				"&district=" + CodecUtil.encodeURL(district);
		// cookie
		String cookie = "user_trace_token=20170811195603-0cd48802-7e8c-11e7-8559-5254005c3644; LGUID=20170811195603-0cd48bd6-7e8c-11e7-8559-5254005c3644; index_location_city=%E5%B9%BF%E5%B7%9E; JSESSIONID=ABAAABAAAFCAAEG2AE7244AC6FD96690C34D1432CC6FD3C; SEARCH_ID=0d58431d0aaf40b5b76b88b936b478b7; _gid=GA1.2.1008888420.1503023463; _ga=GA1.2.263259257.1502452547; LGSID=20170818144439-b559e13d-83e0-11e7-991d-525400f775ce; PRE_UTM=; PRE_HOST=; PRE_SITE=https%3A%2F%2Fwww.lagou.com%2Fjobs%2Flist_java%3Fcity%3D%25E5%25B9%25BF%25E5%25B7%259E%26district%3D%25E5%25A4%25A9%25E6%25B2%25B3%25E5%258C%25BA%26cl%3Dfalse%26fromSearch%3Dtrue%26labelWords%3D%26suginput%3D; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2Fjobs%2Flist_java%3Fpx%3Dnew%26city%3D%25E5%25B9%25BF%25E5%25B7%259E%26district%3D%25E5%25A4%25A9%25E6%25B2%25B3%25E5%258C%25BA; LGRID=20170818144439-b559e2d3-83e0-11e7-991d-525400f775ce; TG-TRACK-CODE=search_code";
		// 获取数据的json接口
//		String jsonUrl = "https://www.lagou.com/jobs/positionAjax.json?px=new&city=" + CodecUtil.encodeURL(city) + 
//				(StringUtil.isEmpty(district) ? "" : "&district=" + CodecUtil.encodeURL(district)) + // 城区
//				"&needAddtionalResult=false&isSchoolJob=0";
		String jsonUrl = "https://m.lagou.com/search.json?city=%E5%85%A8%E5%9B%BD&positionName=python%E7%88%AC%E8%99%AB&pageNo=2";
		System.out.println(CodecUtil.decodeURL("python%E7%88%AC%E8%99%AB"));
		System.out.println(CodecUtil.decodeURL("%E5%85%A8%E5%9B%BD"));
		System.out.println(referer);
		// 设置post表单数据
		Map<String, String> params = new HashMap<String, String>();
		params.put("first", "false");
		params.put("pn", "5");
		params.put("kd", kd); // 关键字
		// 设置请求头Referer : 上一个页面
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("Referer", referer);
		properties.put("Cookie", cookie);
		properties.put("Host", "www.lagou.com");
		properties.put("Origin", "https://www.lagou.com");
		properties.put("Connection", "keep-alive");
		properties.put("Accept", "application/json, text/javascript, */*; q=0.01");
		properties.put("Accept-Encoding", "gzip, deflate, br");
		properties.put("Accept-Language", "zh-CN,zh;q=0.8");
		properties.put("Content-Length", "24");
		properties.put("Content-Length", "24");
		properties.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		properties.put("X-Anit-Forge-Code", "0");
		properties.put("X-Anit-Forge-Token", "None");
		properties.put("X-Requested-With", "XMLHttpRequest");
		// 利用代理ip对目标url发起请求
		try {
			String content = HttpUtil.httpBrowserPost(jsonUrl, params, properties, null);
			System.out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
