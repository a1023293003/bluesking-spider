package cn.bluesking.spider.proxyPool.persistence;

import java.util.List;
import java.util.Map;

import cn.bluesking.spider.proxyPool.entity.ProxyInfo;

/**
 * IP代理持久化操作接口
 * 
 * @author 随心
 *
 */
public interface BaseProxyInfoDao {

    /**
     * 存储一个IP代理信息对象
     * 
     * @param proxyInfo [ProxyInfo]待存储的IP代理信息对象
     * @return [Integer]方法执行成功将返回成功存储的IP代理信息对象的数量,方法执行出现异常方法将返回-1
     */
    Long save(ProxyInfo proxyInfo);
    
    /**
     * 为指定的IP代理对象递增分数,如果指定对象不存在,则创建对象,并默认分数初始化为0进行递增操作
     * 
     * @param proxyInfo [ProxyInfo]IP代理对象
     * @param increment [double]递增的分数值,可以是负数
     * @return [Double]方法执行成功将返回递增后的分数值,如果方法执行失败将返回null
     */
    Double incrby(ProxyInfo proxyInfo, double increment);
    
    /**
     * 为指定的IP代理对象递增分数,如果指定对象不存在,则创建对象,并默认分数初始化为0进行递增操作
     * 
     * @param scoreMembers [Map<ProxyInfo, Double>]IP代理对象和递增分数组成的键值对集合
     * @return [List<Object>]方法执行成功将返回递增后的分数值集合,如果方法执行失败将返回null
     */
    List<Object> incrby(Map<ProxyInfo, Double> scoreMembers);
    
    /**
     * 删除指定的IP代理对象
     * 
     * @param proxyInf [ProxyInfo]待删除的IP代理对象
     * @return [Long]方法执行成功将返回被成功删除的代理个数,方法执行过程出现异常方法将返回-1
     */
    Long rem(ProxyInfo proxyInfo);
    
    /**
     * 从数据库中随机取出一段长度为count的连续的IP代理信息集合
     * 
     * @param count [int]取出的IP代理信息数量
     * @return
     */
    List<ProxyInfo> listRandomProxyInfos(int count);

    /**
     * 获取所有IP代理信息
     * 
     * @return [List<ProxyInfo>]方法执行成功将代理信息列表,方法执行过程中出现异常,方法将返回null
     */
    List<ProxyInfo> listProxy();
    
    /**
     * 获取指定IP代理信息对象的分数
     * 
     * @param proxyInfo [ProxyInfo]待获取分数的IP代理信息对象
     * @return [Double]如果数据库中存在该代理信息对象,执行成功将返回该代理信息对象对应的分数,如果数据库中不存在该代理信息对象,方法将返回null
     */
    Double getScore(ProxyInfo proxyInfo);
    
}
