package cn.bluesking.spider.proxyPool.helper;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import cn.bluesking.spider.commons.util.ArrayUtil;
import cn.bluesking.spider.commons.util.CaseUtil;
import cn.bluesking.spider.commons.util.CollectionUtil;
import cn.bluesking.spider.commons.util.HttpUtil;
import cn.bluesking.spider.commons.util.MathUtil;

/**
 * 代理IP助手类
 * 
 * @author 随心
 *
 */
public final class ProxyHelper {

    /** 测试代理是否可用的url集合 */
    private static final String[] TEST_URLS = {"http://www.whatsmyip.org/", 
            "https://www.qiushibaike.com/", "https://www.zhihu.com/question/20799742"};
    
    /** 代理测试次数 */
    private static final int TEST_TIMES = 2;
    
    /**
     * 随机返回一个测试用的url
     * 
     * @return
     */
    private static String getRandomTestUrl() {
        if (ArrayUtil.isEmpty(TEST_URLS) ) {
            return null;
        } else {
            int index = MathUtil.getRandom(0, TEST_URLS.length);
            return TEST_URLS[index];
        }
    }
    
    /**
     * 检测代理IP是否可用
     * 
     * @param proxy   [Proxy]代理IP
     * @return [boolean]如果代理可用,方法返回true,如果代理不可用,方法返回false
     */
    public static boolean checkProxy(Proxy proxy) {
        try {
            return HttpUtil.testConnection(getRandomTestUrl(), proxy);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 检测代理IP是否可用
     * 
     * @param proxy   [Proxy]代理IP
     * @param timeout [int]超时时间,单位:毫秒
     * @return [boolean]如果代理可用,方法返回true,如果代理不可用,方法返回false
     */
    public static boolean checkProxy(Proxy proxy, int timeout) {
        try {
            for (int i = 0; i < TEST_TIMES; i ++) {
                if (HttpUtil.testConnection(getRandomTestUrl(), proxy, timeout)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 通过代理IP和端口获取代理对象
     * 
     * @param host [String]代理IP
     * @param port [int]代理端口
     * @return [Proxy]方法执行成功将返回一个代理对象,方法执行过程中出现异常,方法将返回null
     */
    public static Proxy toProxy(String host, int port) {
        try {
            return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 通过ip集合和port集合创建并返回代理(Proxy)对象集合
     * 
     * @param hosts [List<String>]ip集合
     * @param ports [List<String>]端口集合
     * @return [List<Proxy>]方法执行成功将返回代理对象集合
     */
    public static List<Proxy> getProxyList(List<String> hosts, List<String> ports) {
        if (CollectionUtil.isEmpty(hosts) || CollectionUtil.isEmpty(ports)) {
            return new ArrayList<Proxy>(0);
        } else {
            // 方法返回值,代理列表
            List<Proxy> proxyList = new ArrayList<Proxy>(hosts.size());
            for (int i = 0; i < hosts.size(); i++) {
                proxyList.add(toProxy(hosts.get(i), CaseUtil.caseInt(ports.get(i), 8080)));
            }
            return proxyList;
        }
    }
    
}
