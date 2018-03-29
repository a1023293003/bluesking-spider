package cn.bluesking.spider.proxyPool.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.bluesking.spider.commons.util.CollectionUtil;
import cn.bluesking.spider.commons.util.MathUtil;
import cn.bluesking.spider.proxyPool.entity.ProxyInfo;
import cn.bluesking.spider.proxyPool.helper.ConfigHelper;
import cn.bluesking.spider.proxyPool.helper.ProxyInfoHelper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisException;

/**
 * 通过Redis实现的IP代理信息持久化操作
 * 
 * @author 随心
 *
 */
public class RedisProxyInfoDaoImpl implements BaseProxyInfoDao {

    /** redis连接池 */
    private static JedisPool jedisPool;
    
    /** redis连接持有者 */
    private static ThreadLocal<Jedis> connectionHolder = new ThreadLocal<Jedis>();
    
    /** redis中代理信息对象存储的有序集合的key */
    private static final String PROXY_INFO_SORTED_SET_KEY = "proxyInfoSortedSet";

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        // 连接池中最大连接数
        config.setMaxTotal(ConfigHelper.getRedisPoolMaxTotal());
        // 连接池中最大空闲连接数
        config.setMaxIdle(ConfigHelper.getRedisPoolMaxIdle());
        // 获取Jedis连接的最大等待时间（50秒）
        config.setMaxWaitMillis(50 * 1000);
        // 在获取Jedis连接时，自动检验连接是否可用
        config.setTestOnBorrow(true);
        // 在将连接放回池中前，自动检验连接是否有效
        config.setTestOnReturn(true);
        // 自动测试池中的空闲连接是否都是可用连
        jedisPool = new JedisPool(config, ConfigHelper.getRedisServerHost(), ConfigHelper.getRedisServerPort());
    }

    /**
     * 获取redis连接
     * 
     * @return
     */
    private Jedis getConnection() {
        Jedis jedis = connectionHolder.get();
        if (jedis == null) {
            jedis = jedisPool.getResource();
            connectionHolder.set(jedis);
        }
        return jedis;
    }

    /**
     * 把可用连接还给连接池
     */
    @SuppressWarnings("deprecation")
    private void returnConnection() {
        Jedis jedis = connectionHolder.get();
        if (jedis != null) {
            connectionHolder.remove();
            jedisPool.returnResource(jedis);
        }
    }
    
    /**
     * 把不可用的连接退还给连接池
     */
    @SuppressWarnings("deprecation")
    private void returnBrokenConnection() {
        Jedis jedis = connectionHolder.get();
        if (jedis != null) {
            connectionHolder.remove();
            jedisPool.returnBrokenResource(jedis);
        }
    }

    /**
     * 开启事务
     * 
     * @return
     */
    @SuppressWarnings("unused")
    private Transaction openTransaction() {
        return getConnection().multi();
    }
    
    /**
     * 提交事务
     * 
     * @param transaction [Transaction]事务对象
     * @return
     */
    @SuppressWarnings("unused")
    private List<Object> commitTransaction(Transaction transaction) {
        if (transaction != null) {
            List<Object> resultList = transaction.exec();
            returnConnection();
            return resultList;
        } else {
            return null;
        }
    }
    
    /**
     * 回滚事务
     * 
     * @param transaction [Transaction]事务对象
     * @return
     */
    @SuppressWarnings("unused")
    private String rollBackTransaction(Transaction transaction) {
        if (transaction != null) {
            String result = transaction.discard();
            returnBrokenConnection();
            return result;
        } else {
            return null;
        }
    }
    
    /**
     * {@inheritDoc},对于已存在的IP代理对象,会被覆盖
     */
    @Override
    public Long save(ProxyInfo proxyInfo) {
        Jedis jedis = null;
        try {
            jedis = getConnection();
            String identity = ProxyInfoHelper.getIdentity(proxyInfo);
            double status = proxyInfo.getStatus();
            return jedis.zadd(PROXY_INFO_SORTED_SET_KEY, status, identity);
        } catch (JedisException e) {
            returnBrokenConnection();
            return -1L;
        } finally {
            returnConnection();
        }
    }
    
    @Override
    public Double incrby(ProxyInfo proxyInfo, double increment) {
        Jedis jedis = null;
        try {
            jedis = getConnection();
            String identity = ProxyInfoHelper.getIdentity(proxyInfo);
            return jedis.zincrby(PROXY_INFO_SORTED_SET_KEY, increment, identity);
        } catch (JedisException e) {
            returnBrokenConnection();
            return null;
        } finally {
            returnConnection();
        }
    }
    
    @Override
    public List<Object> incrby(Map<ProxyInfo, Double> scoreMembers) {
        Jedis jedis = null;
        try {
            jedis = getConnection();
            // 使用通道异步执行命令
            Pipeline pipelined = jedis.pipelined(); 
            for (Entry<ProxyInfo, Double> entry : scoreMembers.entrySet()) {
                pipelined.zincrby(PROXY_INFO_SORTED_SET_KEY, 
                        entry.getValue(), ProxyInfoHelper.getIdentity(entry.getKey()));
            }
            return pipelined.syncAndReturnAll();
        } catch (JedisException e) {
            returnBrokenConnection();
            return null;
        } finally {
            returnConnection();
        }
    }
    
    @Override
    public Long rem(ProxyInfo proxyInfo) {
        Jedis jedis = null;
        try {
            jedis = getConnection();
            String identity = ProxyInfoHelper.getIdentity(proxyInfo);
            return jedis.zrem(PROXY_INFO_SORTED_SET_KEY, identity);
        } catch (JedisException e) {
            returnBrokenConnection();
            return -1L;
        } finally {
            returnConnection();
        }
    }

    /**
     * {@inheritDoc},返回的ProxyInfo对象是带有score分数的
     */
    @Override
    public List<ProxyInfo> listRandomProxyInfos(int count) {
        if (count <= 0) {
            return new ArrayList<ProxyInfo>(0);
        } else {
            Jedis jedis = null;
            try {
                jedis = getConnection();
                // 获取代理总数
                Long total = jedis.zcard(PROXY_INFO_SORTED_SET_KEY);
                Set<Tuple> identities;
                // 根据需求的代理数量随机取出一段连续的代理
                if (total <= count) {
                    identities = jedis.zrangeWithScores(PROXY_INFO_SORTED_SET_KEY, 0, -1L);
                } else {
                    long start = MathUtil.getRandom(0L, total - count + 1);
                    identities = jedis.zrangeWithScores(PROXY_INFO_SORTED_SET_KEY, start, start + count - 1);
                }
                // 解析响应数据,封装成ProxyInfo对象列表
                if (CollectionUtil.isEmpty(identities)) {
                    return new ArrayList<ProxyInfo>(0);
                } else {
                    List<ProxyInfo> proxyInfoList = new ArrayList<ProxyInfo>(identities.size());
                    for (Tuple tuple : identities) {
                        proxyInfoList.add(ProxyInfoHelper.toProxyInfo(tuple.getElement(), tuple.getScore()));
                    }
                    return proxyInfoList;
                }
            } catch (JedisException e) {
                e.printStackTrace();
                returnBrokenConnection();
                return null;
            } finally {
                returnConnection();
            }
        }
    }
    
    /**
     * {@inheritDoc}这个方法完全没考虑代理数量会超过List存储的情况,因为作者本身也不相信可以拿到那么多可用代理
     */
    @Override
    public List<ProxyInfo> listProxy() {
        Jedis jedis = null;
        try {
            jedis = getConnection();
            Set<Tuple> tupleSet = jedis.zrangeWithScores(PROXY_INFO_SORTED_SET_KEY, 0, -1);
            List<ProxyInfo> proxyInfoList = new ArrayList<ProxyInfo>(tupleSet.size());
            for (Tuple tuple : tupleSet) {
                proxyInfoList.add(ProxyInfoHelper.toProxyInfo(tuple.getElement(), tuple.getScore()));
            }
            return proxyInfoList;
        } catch (JedisException e) {
            returnBrokenConnection();
            return null;
        } finally {
            returnConnection();
        }
    }
    
    @Override
    public Double getScore(ProxyInfo proxyInfo) {
        Jedis jedis = null;
        try {
            jedis = getConnection();
            String identity = ProxyInfoHelper.getIdentity(proxyInfo);
            return jedis.zscore(PROXY_INFO_SORTED_SET_KEY, identity);
        } catch (JedisException e) {
            returnBrokenConnection();
            return null;
        } finally {
            returnConnection();
        }
    }
    
    /**
     * 获取redis实现的持久化操作单例对象
     * 
     * @return
     */
    public static BaseProxyInfoDao getProxyInfoDao() {
        return InnerSingleClass.dao;
    }
    
    /**
     * 内部利用JVM加载机制实现的线程安全的单例模式
     * 
     * @author 随心
     *
     */
    private static class InnerSingleClass {
        public static final RedisProxyInfoDaoImpl dao = new RedisProxyInfoDaoImpl();
    }
    
    
}
