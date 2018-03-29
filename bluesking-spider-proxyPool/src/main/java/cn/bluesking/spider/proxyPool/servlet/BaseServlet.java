package cn.bluesking.spider.proxyPool.servlet;

import javax.servlet.http.HttpServlet;

import cn.bluesking.spider.proxyPool.persistence.BaseProxyInfoDao;
import cn.bluesking.spider.proxyPool.persistence.RedisProxyInfoDaoImpl;

/**
 * 基础servlet
 * 
 * @author 随心
 *
 */
public class BaseServlet extends HttpServlet {

    /** 序列化ID */
    private static final long serialVersionUID = 1953772407900697159L;
    
    /** 代理信息持久化操作对象 */
    private static BaseProxyInfoDao proxyInfoDao = RedisProxyInfoDaoImpl.getProxyInfoDao();
    
    protected static BaseProxyInfoDao getProxyInfoDao() {
        return proxyInfoDao;
    }
    
}
