package cn.bluesking.spider.proxyPool.getter;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.util.CollectionUtil;
import cn.bluesking.spider.commons.util.HttpUtil;
import cn.bluesking.spider.commons.util.StringUtil;
import cn.bluesking.spider.proxyPool.TaskExecutor;
import cn.bluesking.spider.proxyPool.entity.ProxyInfo;
import cn.bluesking.spider.proxyPool.helper.ProxyHelper;
import cn.bluesking.spider.proxyPool.helper.ProxyInfoHelper;
import cn.bluesking.spider.proxyPool.task.BaseTask;

/**
 * 获取代理类接口
 * 
 * @author 随心
 *
 */
public abstract class BaseProxyGetter extends BaseTask {

    /** slf4j日志配置 */
    private static final Logger _LOG = LoggerFactory.getLogger(BaseProxyGetter.class);
    
    /**
     * 获取代理
     * 
     * @return
     */
    abstract List<Proxy> listProxy();

    @Override
    public void run() {
        List<Proxy> proxyList = listProxy(); // 执行代理爬取任务
        if (CollectionUtil.isEmpty(proxyList)) {
            // do nothing
        } else {
            Map<ProxyInfo, Double> scoreMembers = new HashMap<ProxyInfo, Double>();
            for (Proxy proxy : proxyList) {
                if (TaskExecutor.isSupenp()) {
                    _LOG.debug(this.getClass().getSimpleName() + "任务终止...");
                    break;
                } else {
                    if (ProxyHelper.checkProxy(proxy)) {
                        scoreMembers.put(ProxyInfoHelper.toProxyInfo(proxy), 1.0);
                    }
                }
            }
            if (CollectionUtil.isEmpty(scoreMembers)) {
                // do nothing
            } else {
                // 把可用的代理存到数据库中
                System.err.println("搜集到可用代理" + scoreMembers.size() + "条");
                getProxyInfoDao().incrby(scoreMembers);
            }
        }
    }
    
    /**
     * 获取目标地址的页面内容
     * 
     * @param url   [String]目标页面的URL
     * @param count [int]从数据库中获取的可用代理的个数
     * @return [String]方法执行成功将返回页面内容对应的String
     * @throws IOException 获取页面内容失败将返回该异常
     */
    protected String getContent(String url, int count) throws IOException {
        // 从数据库中随机取出指定数量的代理
        List<ProxyInfo> proxyInfoList = getProxyInfoDao().listRandomProxyInfos(count);
        String content;
        // 请求资源
        if (CollectionUtil.isEmpty(proxyInfoList)) {
            // 如果数据库中可用代理数量不够,那么就不使用代理进行请求
            content = HttpUtil.httpBrowserGet(url);
        } else {
            // 如果有可用IP代理,则优先使用IP代理进行请求
            for (ProxyInfo proxyInfo : proxyInfoList) {
                content = getContent(url, proxyInfo);
                if (content != null) {
                    return content;
                }
            }
            // 如果无可用IP代理,尝试不使用代理进行请求操作
            content = HttpUtil.httpBrowserGet(url);
        }
        // 返回资源
        if (StringUtil.isEmpty(content)) {
            throw new IOException("获取服务器资源失败!");
        } else {
            return content;
        }
    }
    
    /**
     * 通过传入的代理获取网页内容
     * 
     * @param url       [String]请求URL地址
     * @param proxyInfo [ProxyInfo]IP代理信息对象
     * @return [String]请求成功方法将返回页面内容对应的字符串,请求失败方法将返回null
     */
    private String getContent(String url, ProxyInfo proxyInfo) {
        if (proxyInfo == null) {
            // 不使用代理
            try {
                return HttpUtil.httpBrowserGet(url);
            } catch (IOException e) {
                return null;
            }
        } else {
            // 使用代理
            double increment = -1;
            try {
                String content = HttpUtil.httpBrowserGet(url, 
                        ProxyHelper.toProxy(proxyInfo.getHost(), proxyInfo.getPort()));
                increment = 1;
                return content;
            } catch (Exception e) {
                return null;
            } finally {
                getProxyInfoDao().incrby(proxyInfo, increment);
            }
        }
    }
    
}
