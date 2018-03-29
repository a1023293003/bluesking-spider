package cn.bluesking.spider.proxyPool.task;

import cn.bluesking.spider.proxyPool.annotation.ScheduledTask;
import cn.bluesking.spider.proxyPool.persistence.BaseProxyInfoDao;
import cn.bluesking.spider.proxyPool.persistence.RedisProxyInfoDaoImpl;

/**
 * 基础任务类
 * 
 * @author 随心
 *
 */
public abstract class BaseTask implements Runnable {

    /** IP代理信息对象持久化操作 */
    private static BaseProxyInfoDao proxyInfoDao = RedisProxyInfoDaoImpl.getProxyInfoDao();
    
    /**
     * 返回代理信息持久化操作实现类
     * 
     * @return
     */
    protected static BaseProxyInfoDao getProxyInfoDao() {
        return proxyInfoDao;
    }
    
    /**
     * 获取定时任务执行间隔时间,单位:毫秒
     * 
     * @return
     */
    public Long getInterval() {
        ScheduledTask task = this.getClass().getAnnotation(ScheduledTask.class);
        return task == null ? Long.MAX_VALUE : task.value();
    }

}
