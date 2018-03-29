package cn.bluesking.spider.commons.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.constant.EncodingConstant;
import cn.bluesking.spider.commons.constant.RequestMethodConstant;
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

    /** 超时连接判定时间 */
    private static final int TIMEOUT = 8000;

    /**
     * 获取请求头map
     * 
     * @return
     */
    private static Map<String, String> getRequestHeaderMap() {
        request_header_mapper.put("User-Agent", UserAgentHelper.getRandomUserAgent());
        return request_header_mapper;
    }

    /**
     * 以get请求的形式获取指定网址的html字符串
     * 
     * @param url [String]目标地址
     * @return [String]页面内容
     * @throws IOException
     */
    public static String httpBrowserGet(String url) throws IOException {
        return httpConnection(url, null, getRequestHeaderMap(), EncodingConstant.UTF_8, 
                RequestMethodConstant.GET, null, TIMEOUT, TIMEOUT);
    }

    /**
     * 以get请求的形式获取指定网址的html字符串
     * 
     * @param url [String]目标地址
     * @param properties [Map<String, Stirng>]请求头参数
     * @param proxy [Proxy]ip代理对象
     * @return [String]页面内容
     * @throws IOException
     */
    public static String httpBrowserGet(String url, Map<String, String> properties, Proxy proxy) throws IOException {
        if (CollectionUtil.isNotEmpty(properties)) {
            properties.putAll(getRequestHeaderMap());
        } else {
            properties = getRequestHeaderMap();
        }
        return httpConnection(url, null, properties, EncodingConstant.UTF_8, 
                RequestMethodConstant.POST, proxy, TIMEOUT, TIMEOUT);
    }

    /**
     * 以post请求的形式获取指定网址的html字符串
     * 
     * @param url [String]目标地址
     * @return [String]页面内容
     * @throws IOException
     */
    public static String httpBrowserPost(String url) throws IOException {
        return httpConnection(url, null, request_header_mapper, EncodingConstant.UTF_8, 
                RequestMethodConstant.POST, null, TIMEOUT, TIMEOUT);
    }

    /**
     * 以get请求的形式获取指定网址的html字符串
     * 
     * @param url [String]目标地址
     * @param proxy [Proxy]ip代理对象
     * @return [String]页面内容
     * @throws IOException
     */
    public static String httpBrowserGet(String url, Proxy proxy) throws IOException {
        return httpConnection(url, null, getRequestHeaderMap(), EncodingConstant.UTF_8, 
                RequestMethodConstant.GET, proxy, TIMEOUT, TIMEOUT);
    }

    /**
     * 以get请求的形式获取指定网址的html字符串
     * 
     * @param url [String]目标地址
     * @param urlParams [LinkedHashMap<String, Stirng>]请求参数
     * @return [String]页面内容
     * @throws IOException
     */
    public static String httpBrowserGet(String url, LinkedHashMap<String, String> urlParams) throws IOException {
        if (CollectionUtil.isNotEmpty(urlParams)) {
            url = appendURLParams(url, urlParams); // 拼接url参数
        }
        return httpConnection(url, null, getRequestHeaderMap(), EncodingConstant.UTF_8, 
                RequestMethodConstant.GET, null, TIMEOUT, TIMEOUT);
    }

    /**
     * 以post请求的形式获取指定网址的html字符串
     * 
     * @param url    [String]目标地址
     * @param params [Map<String, Stirng>]请求参数
     * @return [String]页面内容
     * @throws IOException
     */
    public static String httpBrowserPost(String url, Map<String, String> params) throws IOException {
        return httpConnection(url, params, getRequestHeaderMap(), EncodingConstant.UTF_8, 
                RequestMethodConstant.POST, null, TIMEOUT, TIMEOUT);
    }

    /**
     * 以post请求的形式获取指定网址的html字符串
     * 
     * @param url     [String]目标地址
     * @param params  [Map<String, Stirng>]请求参数集合
     * @param headers [Map<String, Stirng>]请求头参数集合
     * @param proxy   [Proxy]ip代理对象
     * @return [String]页面内容
     * @throws IOException
     */
    public static String httpBrowserPost(String url, Map<String, String> params, Map<String, String> headers,
            Proxy proxy) throws IOException {
        if (CollectionUtil.isNotEmpty(headers)) {
            headers.putAll(getRequestHeaderMap());
        } else {
            headers = getRequestHeaderMap();
        }
        return httpConnection(url, params, headers, EncodingConstant.UTF_8, RequestMethodConstant.POST, proxy, TIMEOUT, TIMEOUT);
    }

    /**
     * 测试代理连接,GET方法请求
     * 
     * @param url         [String]连接url
     * @param proxy       [Proxy]代理对象
     * @return [boolean]测试连接可用方法返回true,否则方法返回false
     */
    public static boolean testConnection(String url, Proxy proxy) {
        return testConnection(url, proxy, TIMEOUT);
    }
    
    /**
     * 测试代理连接,GET方法请求
     * 
     * @param url         [String]连接url
     * @param proxy       [Proxy]代理对象
     * @param connTimeout [int]连接超时时间
     * @return [boolean]测试连接可用方法返回true,否则方法返回false
     */
    public static boolean testConnection(String url, Proxy proxy, int connTimeout) {
        // 定义一个请求连接
        HttpURLConnection conn = null;
        try {
            // 获取连接对象
            conn = getConnection(url, null, proxy, connTimeout, TIMEOUT);
            // 设置请求方法
            conn.setRequestMethod(RequestMethodConstant.GET);
            // 开始实际链接,和服务器建立连接,可能出现连接超时异常
            conn.connect();
            // 通过响应编码判断请求是否成功
            int responseCode = conn.getResponseCode();
            return responseCode >= 200 && responseCode < 300;
        } catch (IOException e) {
//            _LOG.error("连接失败！" + e.getMessage());
            return false;
        } finally {
            // 关闭连接请求
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 发起一次http请求，并返回资源对应的字符串
     * 
     * @param url
     * @param params      [Map<String, String>]请求参数集合
     * @param headers     [Map<String, String>]请求头参数集合
     * @param code        [String]请求响应数据读取时设置的编码类型
     * @param method      [String]请求方法
     * @param proxy       [Proxy]ip代理对象
     * @param connTimeout [int]等待请求响应超时时间
     * @param readTimeout [int]读取响应数据超时时间
     * @return [String]方法执行成功将返回请求成功读取的资源对应的字符串
     * @throws IOException 请求及读取过程中可能出现改异常
     */
    private static String httpConnection(String url, Map<String, String> params, Map<String, String> headers,
            String code, String method, Proxy proxy, int connTimeout, int readTimeout) throws IOException {
        // 定义一个请求连接
        HttpURLConnection conn = null;
        try {
            // 获取连接对象
            conn = getConnection(url, headers, proxy, connTimeout, readTimeout);
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
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 读取请求响应数据
     * 
     * @param conn [HttpURLConnection]连接对象
     * @param code [String]编码
     * @return [String]方法执行成功将返回本次请求响应的数据
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
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
//            _LOG.error("获取请求连接返回内容失败！" + e.getMessage());
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                _LOG.error("关闭读取器失败！" + e);
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 获取一个HttpURLConnection连接对象
     * 
     * @param url         [String]请求的url
     * @param headers     [Map<String, String>]请求头参数集合
     * @param proxy       [Proxy]ip代理对象
     * @param connTimeout [int]等待请求响应超时时间
     * @param readTimeout [int]读取响应数据超时时间
     * @return [HttpURLConnection]方法执行成功将返回一个HttpURLConnection对象
     * @throws IOException 创建HttpURLConnection对象过程中可能会抛出该异常
     */
    private static HttpURLConnection getConnection(String url, Map<String, String> headers, Proxy proxy,
            int connTimeout, int readTimeout) throws IOException {
        HttpURLConnection conn = null;
        if (StringUtil.isEmpty(url)) {
            return null;
        } else {
            try {
                URL readUrl = new URL(url);
                conn = proxy == null ? (HttpURLConnection) readUrl.openConnection()
                        : (HttpURLConnection) readUrl.openConnection(proxy);
                conn.setDoOutput(true); // 可以post写入数据
                conn.setConnectTimeout(connTimeout);
                conn.setReadTimeout(readTimeout);
                if (CollectionUtil.isNotEmpty(headers)) { // 加载请求头参数,用于模拟浏览器
                    for (Map.Entry<String, String> property : headers.entrySet()) {
                        conn.setRequestProperty(property.getKey(), property.getValue());
                    }
                }
                return conn;
            } catch (IOException e) {
                _LOG.error("创建连接失败！" + e.getMessage());
                throw e;
            }
        }
    }

    /**
     * 为一个连接对象写入post表单数据
     * 
     * @param conn   [HttpURLConnection]连接对象
     * @param params [Map<String, String>]表单数据
     * @return [HttpURLConnection]方法执行成功将返回本次请求的连接对象
     * @throws IOException 在向请求服务器传输表单数据过程中可能出现该异常
     */
    private static HttpURLConnection appendRequestParams(HttpURLConnection conn, Map<String, String> params)
            throws IOException {
        if (conn == null || CollectionUtil.isEmpty(params)) {
            return conn;
        } else {
            // 定义一个带缓冲的字符串拼接参数
            StringBuilder paramBuf = new StringBuilder();
            // 分隔符
            String separator = "";
            // 拼接请求参数
            for (Map.Entry<String, String> entry : params.entrySet()) {
                paramBuf.append(entry.getKey()).append("=").append(entry.getValue()).append(separator);
                separator = "&";
            }
            // 建立输入流，向指向的URL传入参数
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
                    if (dos != null) {
                        dos.close();
                    }
                } catch (IOException e) {
                    _LOG.error("数据输出流关闭失败！" + e.getMessage());
                }
            }
            return conn;
        }
    }

    /**
     * 拼接请求参数到url后面
     * 
     * @param url    [String]请求的url
     * @param params [Map<String, String>]请求参数集合
     * @return [String]方法执行成功将返回一个拼接成功后的url字符串
     */
    public static String appendURLParams(String url, Map<String, String> params) {
        // 参数集合不为空
        if (StringUtil.isEmpty(url) || CollectionUtil.isEmpty(params)) {
            return url;
        } else {
            boolean isHasParam = url.contains("?");
            StringBuilder buf = new StringBuilder(url + (isHasParam ? "&" : "?"));
            // 分隔符
            String separator = "";
            for (Map.Entry<String, String> param : params.entrySet()) {
                buf.append(param.getKey()).append("=").append(param.getValue()).append(separator);
                separator = "&";
            }
            return CodecUtil.encodeURL(buf.toString());
        }
    }

}
