package cn.bluesking.spider.mobile.lagou.helper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import cn.bluesking.spider.commons.util.ArrayUtil;
import cn.bluesking.spider.commons.util.CaseUtil;
import cn.bluesking.spider.commons.util.HttpUtil;
import cn.bluesking.spider.commons.util.StringUtil;

/**
 * IP代理获取助手类
 * 
 * @author 随心
 *
 */
public final class ProxyGetterHelper {

    /** 用于存储可用代理的阻塞队列 */
    private static final BlockingQueue<Proxy> proxyQueue = new LinkedBlockingQueue<Proxy>();
    
    private static final String URL = "http://localhost:8080/bluesking-spider-proxyPool/get/10";
    
    /**
     * 从代理池中获取可用代理并存入阻塞队列中,然后从阻塞队列中返回一个代理
     * 
     * @return
     */
    private static Proxy getProxy0() {
        try {
            String content = HttpUtil.httpBrowserGet(URL);
            if (StringUtil.isEmpty(content)) {
                return null;
            } else {
                String[] proxyInfos = content.split("&");
                if (ArrayUtil.isEmpty(proxyInfos)) {
                    return null;
                } else {
                    String[] temp;
                    Proxy proxy;
                    for (String proxyInfo : proxyInfos) {
                        temp = proxyInfo.split(":");
                        if (ArrayUtil.isNotEmpty(temp) && temp.length == 2) {
                            try {
                                proxy = new Proxy(Proxy.Type.HTTP, 
                                        new InetSocketAddress(temp[0], CaseUtil.caseInt(temp[1], 8080)));
                            } catch (Exception e) {
                                continue;
                            }
                            proxyQueue.add(proxy);
                        }
                    }
                }
            }
            return proxyQueue.take();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 返回一个代理对象
     * 
     * @return
     */
    public static Proxy getProxy() {
        if (proxyQueue.size() <= 0) {
            return getProxy0();
        } else {
            Proxy proxy;
            try {
                proxy = proxyQueue.poll(5, TimeUnit.SECONDS);
                if (proxy != null) {
                    return proxy;
                } else {
                    return getProxy0();
                }
            } catch (InterruptedException e) {
                return getProxy();
            }
        }
    }
    
    /**
     * 阻塞队列中代理数量
     * 
     * @return
     */
    public static int size() {
        return proxyQueue.size();
    }
    
    public static void main(String[] args) {
        getProxy();
    }
    
}
