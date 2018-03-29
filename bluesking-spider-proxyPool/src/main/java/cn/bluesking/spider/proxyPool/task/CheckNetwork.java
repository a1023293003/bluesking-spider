package cn.bluesking.spider.proxyPool.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.util.ThreadUtil;
import cn.bluesking.spider.proxyPool.TaskExecutor;
import cn.bluesking.spider.proxyPool.annotation.ScheduledTask;
import cn.bluesking.spider.proxyPool.helper.ProxyHelper;

/**
 * 检查网络通畅度
 * 
 * @author 随心
 *
 */
@ScheduledTask(40 * 1000L)
public class CheckNetwork extends BaseTask {

    /** slf4j日志配置 */
    private static final Logger _LOG = LoggerFactory.getLogger(CheckNetwork.class);
    
    @Override
    public void run() {
        _LOG.debug("===============进入网络通畅检查任务模块===============");
        if (ProxyHelper.checkProxy(null, 20000)) {
            _LOG.debug("网络通畅!");
        } else {
            _LOG.error("网络不可用!");
            // 再次判断是否断网了
            if (ProxyHelper.checkProxy(null, 20000)) {
                _LOG.debug("网络通畅!");
            } else {
                // 真的断网了就暂停定时器
                TaskExecutor.supenp();
                _LOG.debug("暂停定时器!");
                // 循环检查网络
                while (!ProxyHelper.checkProxy(null, 20000)) {
                    try {
                        ThreadUtil.randomSleep(1000, 1000);
                    } catch (InterruptedException e) {
                    }
                }
                // 网络通畅就重启定时器
                TaskExecutor.restart();
                _LOG.debug("重启定时器!");
            }
        }
    }

}
