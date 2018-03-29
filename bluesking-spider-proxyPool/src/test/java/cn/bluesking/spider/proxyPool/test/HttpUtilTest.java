package cn.bluesking.spider.proxyPool.test;

import java.util.List;

import cn.bluesking.spider.proxyPool.entity.ProxyInfo;
import cn.bluesking.spider.proxyPool.helper.ProxyHelper;
import cn.bluesking.spider.proxyPool.persistence.BaseProxyInfoDao;
import cn.bluesking.spider.proxyPool.persistence.RedisProxyInfoDaoImpl;

public class HttpUtilTest {

    private static BaseProxyInfoDao dao = RedisProxyInfoDaoImpl.getProxyInfoDao();
    
    public static void main(String[] args) throws InterruptedException {
        List<ProxyInfo> proxyInfoList = dao.listProxy();
        double count = 0;
        for (ProxyInfo proxyInfo : proxyInfoList) {
            System.out.print(proxyInfo.toString());
            if (ProxyHelper.checkProxy(ProxyHelper.toProxy(proxyInfo.getHost(), proxyInfo.getPort()))) {
                count ++;
                System.err.println(true);
            } else {
                System.err.println(false);
            }
        }
        System.out.println("数据库中代理可用率为:" + String.format("%.2f", count / proxyInfoList.size() * 100));
    }
}
