package cn.bluesking.spider.proxyPool.task;

import java.net.Proxy;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.util.CollectionUtil;
import cn.bluesking.spider.proxyPool.TaskExecutor;
import cn.bluesking.spider.proxyPool.annotation.ScheduledTask;
import cn.bluesking.spider.proxyPool.entity.ProxyInfo;
import cn.bluesking.spider.proxyPool.helper.ProxyHelper;

/**
 * 数据清理任务,用于清理掉数据库中不可用的代理
 * 
 * @author 随心
 *
 */
@ScheduledTask(10 * 60 * 1000L)
public class CleaningDataTask extends BaseTask {
    
    /** slf4j日志配置 */
    private static final Logger _LOG = LoggerFactory.getLogger(CleaningDataTask.class);
    
    @Override
    public void run() {
        _LOG.debug("===============进入清洗数据任务模块===============");
        List<ProxyInfo> proxyInfoList = getProxyInfoDao().listProxy();
        if (CollectionUtil.isEmpty(proxyInfoList)) {
            _LOG.debug("数据库中代理可用率为:0%");
            return;
        } else {
            // 统计有效的代理个数
            double validProxyNum = 0;
            int totalProxy = proxyInfoList.size();
            // 遍历删除不可用的代理
            Proxy proxy;
            for (ProxyInfo proxyInfo : proxyInfoList) {
                if (TaskExecutor.isSupenp()) {
                    // 定时器处于暂停状态则终止操作
                    _LOG.debug("清理数据任务终止操作...");
                    return;
                } else {
                    // 先进行代理测试再进行操作
                    proxy = ProxyHelper.toProxy(proxyInfo.getHost(), proxyInfo.getPort());
                    if (!ProxyHelper.checkProxy(proxy)) {
                        // 直接删除策略
                        totalProxy --;
                        _LOG.debug("清理掉一个不可用代理:" + proxyInfo.getHost() + ":" + proxyInfo.getPort());
                        getProxyInfoDao().rem(proxyInfo);
                    } else {
                        validProxyNum ++;
                        getProxyInfoDao().incrby(proxyInfo, 1);
                    }
                }
            } // for
            System.err.println("数据库中" + totalProxy + "个代理可用率为:" + 
            String.format("%.2f", validProxyNum / proxyInfoList.size() * 100) + "%");
        }
    }
    
}
