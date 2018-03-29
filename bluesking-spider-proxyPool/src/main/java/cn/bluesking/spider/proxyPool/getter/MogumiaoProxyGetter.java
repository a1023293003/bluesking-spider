package cn.bluesking.spider.proxyPool.getter;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.util.RegexUtil;
import cn.bluesking.spider.proxyPool.annotation.ScheduledTask;
import cn.bluesking.spider.proxyPool.helper.ProxyHelper;

/**
 * 蘑菇代理
 * 
 * @author 随心
 *
 */
@ScheduledTask(10 * 60 * 1000L)
public class MogumiaoProxyGetter extends BaseProxyGetter {

    /** slf4j日志配置 */
    private static final Logger _LOG = LoggerFactory.getLogger(MogumiaoProxyGetter.class);
    
    /** url前缀 */
    private static final String URL_PREFIX = "http://www.mogumiao.com/proxy/free/listFreeIp";
    
    /** 每页能够获取的代理上线 */
    private static final int EACH_PAGE_PROXY_COUNT = 10;
    
    /** 最大尝试使用数据库代理个数 */
    private static final int MAX_TRY_PROXY_COUNT = 10;
    
    @Override
    List<Proxy> listProxy() {
        try {
            _LOG.debug("===============进入蘑菇代理爬取模块===============");
            List<Proxy> proxyList = new ArrayList<Proxy>(EACH_PAGE_PROXY_COUNT);
            // 访问主页
            String content = getContent(URL_PREFIX, MAX_TRY_PROXY_COUNT);
            List<String> ips = RegexUtil.regexString(content, "ip\":\"(\\d+?(.\\d+?){3})\"");
            List<String> ports = RegexUtil.regexString(content, "port\":\"(\\d+?)\"");
            proxyList.addAll(ProxyHelper.getProxyList(ips, ports));
            _LOG.debug("当前蘑菇代理正在爬取到了" + ips.size() + "个ip和" + ports.size() + "个端口");
            return proxyList;
        } catch (IOException e) {
            _LOG.error("蘑菇代理获取失败" + e.getMessage());
            return null;
        }
    }
    
}
