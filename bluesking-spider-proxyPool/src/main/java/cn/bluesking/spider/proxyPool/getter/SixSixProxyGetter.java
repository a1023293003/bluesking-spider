package cn.bluesking.spider.proxyPool.getter;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.util.CollectionUtil;
import cn.bluesking.spider.commons.util.RegexUtil;
import cn.bluesking.spider.commons.util.ThreadUtil;
import cn.bluesking.spider.proxyPool.annotation.ScheduledTask;
import cn.bluesking.spider.proxyPool.helper.ProxyHelper;

/**
 * 66免费ip代理
 * 
 * @author 随心
 *
 */
@ScheduledTask(1 * 60 * 1000L)
public class SixSixProxyGetter extends BaseProxyGetter {

    /** slf4j日志配置 */
    private static final Logger _LOG = LoggerFactory.getLogger(SixSixProxyGetter.class);
    
    /** url前缀 */
    private static final String URL_PREFIX = "http://www.66ip.cn";
    
    /** 最大页数 */
    private static final int MAX_PAGE = 1087;
    
    /** 每次爬取的页数 */
    private static final int EVERY_PAGE_NUM = 1;
    
    /** 最大尝试使用数据库代理个数 */
    private static final int MAX_TRY_PROXY_COUNT = 5;
    
    /** 每页能够获取的代理上线 */
    private static final int EACH_PAGE_PROXY_COUNT = 15;
    
    /** 当前爬取的页数下标 */
    private int pageIndex = 1;
    
    public List<Proxy> listProxy() {
        try {
            _LOG.debug("===============进入66代理爬取模块===============");
            List<Proxy> proxyList = new ArrayList<Proxy>(EACH_PAGE_PROXY_COUNT * EVERY_PAGE_NUM);
            List<String> ips;
            List<String> ports;
            int count = EVERY_PAGE_NUM;
            while (count -- > 0) {
                // 访问主页
                String content = getContent(URL_PREFIX + "/" + pageIndex + ".html", MAX_TRY_PROXY_COUNT);
                // 获取表格HTML
                String tableHTML = RegexUtil.regexAString(content, "<table width='100%' border=\"2px\" cellspacing=\"0px\" bordercolor=\"#6699ff\"[\\s\\S]+?>([\\s\\S]+?)</table>");
                ips = RegexUtil.regexString(tableHTML, "<tr><td>([\\s\\S]+?)</td>");
                ports = RegexUtil.regexString(tableHTML, "<tr><td>[\\s\\S]+?<td>([\\s\\S]+?)</td>");
                if (CollectionUtil.isEmpty(ips) || CollectionUtil.isEmpty(ports)) {
                    return null;
                } else {
                    proxyList.addAll(ProxyHelper.getProxyList(ips, ports));
                    _LOG.debug("当前66代理正在爬取第" + pageIndex + "页代理,爬取到了" + ips.size() + "个ip和" + ports.size() + "个端口");
                    ThreadUtil.randomSleep(2000, 4000);
                    if (pageIndex < MAX_PAGE) {
                        pageIndex ++;
                    } else {
                        pageIndex = 1;
                    }
                }
            }
            return proxyList;
        } catch (IOException e) {
            _LOG.error("66代理获取失败" + e.getMessage());
            return null;
        } catch (InterruptedException e) {
            _LOG.error("66代理获取失败" + e.getMessage());
            return null;
        }
    }
    
}
