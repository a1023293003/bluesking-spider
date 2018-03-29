package cn.bluesking.spider.proxyPool.test;

import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.bluesking.spider.commons.entity.ProxyInfo;
import cn.bluesking.spider.commons.helper.MybatisHelper;
import cn.bluesking.spider.commons.mapper.ProxyInfoMapper;
import cn.bluesking.spider.commons.util.CollectionUtil;
import cn.bluesking.spider.proxyPool.helper.ProxyHelper;
import cn.bluesking.spider.proxyPool.persistence.BaseProxyInfoDao;
import cn.bluesking.spider.proxyPool.persistence.RedisProxyInfoDaoImpl;

public class DataMigrationTest {

    private static BaseProxyInfoDao dao = RedisProxyInfoDaoImpl.getProxyInfoDao();
    
    public void migrate() {
        SqlSession session = MybatisHelper.getSessionFactory().openSession();
        ProxyInfoMapper mapper = session.getMapper(ProxyInfoMapper.class);
        List<ProxyInfo> proxyList = mapper.selectByExample(null);
        Map<cn.bluesking.spider.proxyPool.entity.ProxyInfo, Double> scoreMember = new HashMap<>();
        cn.bluesking.spider.proxyPool.entity.ProxyInfo newProxy;
        session.commit();
        int count = 1;
        for (ProxyInfo proxy : proxyList) {
            newProxy = new cn.bluesking.spider.proxyPool.entity.ProxyInfo(proxy.getIp(), proxy.getPort(), 1.0);
            Proxy p = ProxyHelper.toProxy(proxy.getIp(), proxy.getPort());
            if (ProxyHelper.checkProxy(p)) {
                scoreMember.put(newProxy, 1.0);
            }
            if (count < 100) {
                count ++;
            } else {
                System.err.println("Test:数据库中获取到" + scoreMember.size() + "条可用代理");
                if (CollectionUtil.isNotEmpty(scoreMember)) {
                    dao.incrby(scoreMember);
                    scoreMember.clear();
                }
                count = 1;
            }
        }
    }
    
    public static void main(String[] args) {
        BaseProxyInfoDao dao = RedisProxyInfoDaoImpl.getProxyInfoDao();
        System.out.println(dao.listProxy().size());
    }
}
