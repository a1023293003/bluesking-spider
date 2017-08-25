package cn.bluesking.spider.commons.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.helper.UserAgentHelper;

/**
 * http助手类
 * 
 * @author 随心
 *
 */
public final class HttpUtil {

	/** slf4j日志配置 */
	private static final Logger _LOG = LoggerFactory.getLogger(HttpUtil.class);
	
	/** 谷歌浏览器请求头参数mapper */
	private static Map<String, String> request_header_mapper = new HashMap<String, String>();
	
	/**
	 * 获取请求头map
	 * @return
	 */
	private static Map<String, String> getRequestHeaderMap() {
		request_header_mapper.put("User-Agent", UserAgentHelper.getRandomUserAgent());
		return request_header_mapper;
	}
	
	/**
	 * 以get请求的形式获取指定网址的html字符串
	 * @param url [String]目标地址
	 * @return [String]页面内容
	 * @throws IOException 
	 */
	public static String httpBrowserGet(String url) throws IOException {
		return httpConnection(url, null, getRequestHeaderMap(), "UTF-8", "GET", null);
	}
	
	/**
	 * 以get请求的形式获取指定网址的html字符串
	 * @param url [String]目标地址
	 * @param properties [Map<String, Stirng>]请求头参数
	 * @param proxy [Proxy]ip代理对象
	 * @return [String]页面内容
	 * @throws IOException 
	 */
	public static String httpBrowserGet(String url, 
			Map<String, String> properties, Proxy proxy) throws IOException {
		if(CollectionUtil.isNotEmpty(properties)) {
			properties.putAll(getRequestHeaderMap());
		} else {
			properties = getRequestHeaderMap();
		}
		return httpConnection(url, null, properties, "UTF-8", "POST", proxy);
	}
	
	/**
	 * 以post请求的形式获取指定网址的html字符串
	 * @param url [String]目标地址
	 * @return [String]页面内容
	 * @throws IOException 
	 */
	public static String httpBrowserPost(String url) throws IOException {
		return httpConnection(url, null, request_header_mapper, "UTF-8", "POST", null);
	}
	
	/**
	 * 以get请求的形式获取指定网址的html字符串
	 * @param url [String]目标地址
	 * @param proxy [Proxy]ip代理对象
	 * @return [String]页面内容
	 * @throws IOException 
	 */
	public static String httpBrowserGet(String url, Proxy proxy) throws IOException {
		return httpConnection(url, null, getRequestHeaderMap(), "UTF-8", "GET", proxy);
	}
	
	/**
	 * 以get请求的形式获取指定网址的html字符串
	 * @param url [String]目标地址
	 * @param urlParams [LinkedHashMap<String, Stirng>]请求参数
	 * @return [String]页面内容
	 * @throws IOException 
	 */
	public static String httpBrowserGet(String url, LinkedHashMap<String, String> urlParams) throws IOException {
		if(CollectionUtil.isNotEmpty(urlParams)) {
			url = appendURLParams(url, urlParams); // 拼接url参数
		}
		return httpConnection(url, null, getRequestHeaderMap(), "UTF-8", "GET", null);
	}
	
	/**
	 * 以post请求的形式获取指定网址的html字符串
	 * @param url [String]目标地址
	 * @param params [Map<String, Stirng>]请求参数
	 * @return [String]页面内容
	 * @throws IOException 
	 */
	public static String httpBrowserPost(String url, Map<String, String> params) throws IOException {
		return httpConnection(url, params, getRequestHeaderMap(), "UTF-8", "POST", null);
	}
	
	/**
	 * 以post请求的形式获取指定网址的html字符串
	 * @param url [String]目标地址
	 * @param params [Map<String, Stirng>]请求参数
	 * @param properties [Map<String, Stirng>]请求头参数
	 * @param proxy [Proxy]ip代理对象
	 * @return [String]页面内容
	 * @throws IOException 
	 */
	public static String httpBrowserPost(String url, Map<String, String> params, 
			Map<String, String> properties, Proxy proxy) throws IOException {
		if(CollectionUtil.isNotEmpty(properties)) {
			properties.putAll(getRequestHeaderMap());
		} else {
			properties = getRequestHeaderMap();
		}
		return httpConnection(url, params, properties, "UTF-8", "POST", proxy);
	}
	
	/**
	 * 测试代理连接
	 * @param url [String]连接url
	 * @param proxy [Proxy]代理对象
	 * @return [int]200即为连接正常
	 * @throws IOException
	 */
	public static int testConnection(String url, Proxy proxy) throws IOException {
		// 定义一个请求连接
		HttpURLConnection conn = null;
		try {
			// 获取连接对象
			conn = getConnection(conn, url, null, proxy);
			// 设置请求方法
			conn.setRequestMethod("GET");
			// 开始实际链接,和服务器建立连接
			conn.connect(); // 可能出现连接超时异常
			// 返回响应代码
			return conn.getResponseCode();
		} catch (IOException e) {
			_LOG.error("连接失败！" + e.getMessage());
			throw e;
		} finally {
			// 关闭连接请求
			if(conn != null) { conn.disconnect(); }
		}
	}
	
	/**
	 * 请求的获取指定网址的html字符串
	 * @param url [String]目标地址
	 * @param params [Map<String, Stirng>]表单请求参数
	 * @param properties [Map<String, Stirng>]请求头设置
	 * @param code [String]字符编码
	 * @param method [String]请求方法
	 * @param proxy [Proxy]ip代理对象
	 * @return [String]页面内容
	 * @throws IOException 
	 */
	private static String httpConnection(String url, Map<String, String> params, 
			Map<String, String> properties, String code, String method, Proxy proxy) throws IOException {
		// 定义一个请求连接
		HttpURLConnection conn = null;
		try {
			// 获取连接对象
			conn = getConnection(conn, url, properties, proxy);
			// 设置请求方法
			conn.setRequestMethod(method);
			// 开始实际链接,和服务器建立连接
			conn.connect(); // 可能出现连接超时异常
			// 传入表单参数
			appendRequestParams(conn, params);
			// 读取请求响应数据并返回
			return readResponseData(conn, code); // 可能出现读取超时异常
		} catch (IOException e) {
			throw new IOException("获取响应页面内容失败！" + e.getMessage(), e);
		} finally {
			// 关闭连接请求
			if(conn != null) { conn.disconnect(); }
		}
	}
	
	/**
	 * 读取请求响应数据
	 * @param conn [HttpURLConnection]连接对象
	 * @param code [String]编码
	 * @return
	 * @throws IOException 获取响应数据失败
	 */
	private static String readResponseData(HttpURLConnection conn, String code) throws IOException {
		// 定义一个带缓冲区的字符串存储网页地内容
		StringBuilder result = new StringBuilder();
		// 定义一个缓冲字符输入流
		BufferedReader in = null;
		try {
			// 初始化 BufferReader输入流来读取URL的响应 : 缓冲读取器《-输入流读取器《-输入流
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), code));
			// 用来临时存储每一行数据
			String line = null;
			// 遍历抓取每一行数据将其存储到result里
			while((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			_LOG.error("获取请求连接返回内容失败！" + e.getMessage());
			throw e;
		} finally {
			try {
				if(in != null) { 
					in.close();
				}
			} catch (IOException e) {
				_LOG.error("关闭读取器失败！" + e);
				e.printStackTrace();
			}
		}
		return result.toString();
	}
	
	/** 超时连接判定时间 */
	private static int time_out = 5000;
	
	/**
	 * 获取一个HttpURLConnection连接对象
	 * @param conn [HttpURLConnection]连接对象
	 * @param url [String]请求目标url
	 * @param properties [Map<String, String>]请求头参数
	 * @return [HttpURLConnection]连接对象
	 * @throws IOException 
	 */
	private static HttpURLConnection getConnection(HttpURLConnection conn, String url, 
			Map<String, String> properties, Proxy proxy) throws IOException {
		URL readUrl = new URL(url);
		try {
			conn = proxy == null ? (HttpURLConnection) readUrl.openConnection() : 
					(HttpURLConnection) readUrl.openConnection(proxy);
		} catch (IOException e) {
			_LOG.error("创建连接失败！" + e.getMessage());
			throw e;
		}
		conn.setDoOutput(true); // 可以post写入数据
		conn.setConnectTimeout(time_out);
		conn.setReadTimeout(time_out);
		if(properties != null) { // 加载请求头参数,用于模拟浏览器
			for(Map.Entry<String, String> property : properties.entrySet()) {
				conn.setRequestProperty(property.getKey(), property.getValue());
			}
		}
		return conn;
	}
	
	/**
	 * 为一个连接对象写入post表单数据
	 * @param conn [HttpURLConnection]连接对象
	 * @param params [Map<String, String>]表单数据
	 * @return [HttpURLConnection]连接对象
	 * @throws IOException 
	 */
	private static HttpURLConnection appendRequestParams(HttpURLConnection conn, 
			Map<String, String> params) throws IOException {
		if(CollectionUtil.isEmpty(params)) {
			return conn;
		}
		// 定义一个带缓冲的字符串拼接参数
		StringBuilder paramBuf = new StringBuilder();
		// 拼接请求参数
		for(Map.Entry<String, String> entry : params.entrySet()) {
			paramBuf.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		paramBuf.deleteCharAt(paramBuf.length() - 1);
		//建立输入流，向指向的URL传入参数
		DataOutputStream dos = null;
		try {
			// 获取数据输出流并传输数据
			dos = new DataOutputStream(conn.getOutputStream());
			// 写入数据前需要转码
			dos.writeBytes(CodecUtil.encodeURL(paramBuf.toString()));
			dos.flush();
		} catch (IOException e) {
			_LOG.error("传入请求表单参数失败！" + e.getMessage());
			throw e;
		} finally {
			try {
				if(dos != null) { dos.close(); }
			} catch (IOException e) {
				_LOG.error("数据输出流关闭失败！" + e.getMessage());
			}
		}
		return conn;
	}
	
	/**
	 * 拼接请求参数到url后面
	 * @param url [String]url
	 * @param params [LinkedHashMap<String, String>]有序的键值对参数
	 * @return [String]拼接成功后的url
	 */
	public static String appendURLParams(String url, LinkedHashMap<String, String> params) {
		// 参数集合不为空
		if(CollectionUtil.isEmpty(params)) return url;
		boolean isHasParam = url.contains("?");
		StringBuilder buf = new StringBuilder(url + (isHasParam ? "&" : "?"));
		for(Map.Entry<String, String> param : params.entrySet()) {
			buf.append(param.getKey() + "=" + param.getValue() + "&");
		}
		buf.deleteCharAt(buf.length() - 1); // 删除最后一个字符&
		return CodecUtil.encodeURL(buf.toString());
	}
	
}
