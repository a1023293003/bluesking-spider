package cn.bluesking.spider.proxyPool.helper;

import java.net.InetSocketAddress;
import java.net.Proxy;

import cn.bluesking.spider.commons.util.CaseUtil;
import cn.bluesking.spider.commons.util.StringUtil;
import cn.bluesking.spider.proxyPool.entity.ProxyInfo;

/**
 * IP代理信息对象助手类
 * 
 * @author 随心
 *
 */
public final class ProxyInfoHelper {

    /**
     * 获取IP代理信息对象的身份标识符
     * 
     * @param proxyInfo [ProxyInfo]IP代理信息对象
     * @return [String]如果传入的ProxyInfo对象不为空,那么该方法将返回host:port格式的字符串
     */
    public static String getIdentity(ProxyInfo proxyInfo) {
        if (proxyInfo == null) {
            throw new NullPointerException("传入的代理对象不可以为空!");
        } else {
            return StringUtil.concat(proxyInfo.getHost(), ":", proxyInfo.getPort());
        }
    }
    
    /**
     * 通过IP代理信息对象的身份标识符和分数获取对应的IP代理对象
     * 
     * @param identity [String]IP代理信息对象的身份标识符
     * @param score    [double]分数
     * @return [ProxyInfo]IP代理信息对象
     */
    public static ProxyInfo toProxyInfo(String identity, double score) {
        if (StringUtil.isEmpty(identity)) {
            throw new IllegalArgumentException("传入的IP代理信息对象身份标识符不合法!" + identity);
        } else {
            String[] elements = identity.split(":");
            if (elements.length != 2) {
                throw new IllegalArgumentException("传入的IP代理信息对象身份标识符不合法!" + identity);
            } else {
                ProxyInfo proxyInfo = new ProxyInfo();
                proxyInfo.setHost(elements[0]);
                proxyInfo.setPort(CaseUtil.caseInt(elements[1], 8080));
                proxyInfo.setStatus(score);
                return proxyInfo;
            }
        }
    }
    
    /**
     * 通过代理对象返回代理IP信息
     * 
     * @param proxy [Proxy]代理对象
     * @return [ProxyInfo]方法执行成功将返回IP代理信息对象,否则方法返回null
     */
    public static ProxyInfo toProxyInfo(Proxy proxy) {
        if (proxy == null) {
            return null;
        } else {
            InetSocketAddress socketAddress = (InetSocketAddress) proxy.address();
            ProxyInfo proxyInfo = new ProxyInfo(socketAddress.getHostName(), 
                    CaseUtil.caseInt(socketAddress.getPort(), 8080), 0.0);
            return proxyInfo;
        }
    }
    
}
