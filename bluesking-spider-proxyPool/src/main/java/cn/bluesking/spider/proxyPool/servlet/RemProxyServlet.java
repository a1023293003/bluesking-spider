package cn.bluesking.spider.proxyPool.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bluesking.spider.commons.util.RegexUtil;
import cn.bluesking.spider.commons.util.StringUtil;
import cn.bluesking.spider.proxyPool.entity.ProxyInfo;
import cn.bluesking.spider.proxyPool.helper.ProxyInfoHelper;

/**
 * 删除代理
 * 
 * @author 随心
 *
 */
@WebServlet(urlPatterns = "/rem/*")
public class RemProxyServlet extends BaseServlet {

    /** 序列化ID */
    private static final long serialVersionUID = -6771950895439389802L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();
        String identity = 
                RegexUtil.regexAString(uri, "^" + contextPath + "/rem/([^:]+?:[\\d]{1,5})$");
        if (StringUtil.isEmpty(identity)) {
            super.doGet(request, response);
        } else {
            ProxyInfo proxyInfo = ProxyInfoHelper.toProxyInfo(identity, 0.0);
            Double score = getProxyInfoDao().getScore(proxyInfo);
            if (score == null) {
                super.doGet(request, response);
            } else if (score < 1.0) {
                // 分值小于1直接删除
                getProxyInfoDao().rem(proxyInfo);
            } else {
                getProxyInfoDao().incrby(proxyInfo, -1.0);
            }
        }
        
    }
    
}
