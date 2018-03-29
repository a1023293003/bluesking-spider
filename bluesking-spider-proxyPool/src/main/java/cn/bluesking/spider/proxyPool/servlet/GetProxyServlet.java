package cn.bluesking.spider.proxyPool.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bluesking.spider.commons.util.CaseUtil;
import cn.bluesking.spider.commons.util.CollectionUtil;
import cn.bluesking.spider.commons.util.RegexUtil;
import cn.bluesking.spider.proxyPool.TaskExecutor;
import cn.bluesking.spider.proxyPool.entity.ProxyInfo;

/**
 * 获取代理
 * 
 * @author 随心
 *
 */
@WebServlet(urlPatterns = "/get/*", loadOnStartup = 0)
public class GetProxyServlet extends BaseServlet {

    /** 序列化ID */
    private static final long serialVersionUID = -8420331488211299162L;

    /**
     * 初始化操作
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        // 启动代理池
        TaskExecutor.execute();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();
        int count = CaseUtil.caseInt(
                RegexUtil.regexAString(uri, "^" + contextPath + "/get/([\\d]+?)$"), -1);
        if (count == -1) {
            super.doGet(request, response);
        } else if (count == 0) {
            response.getWriter().println("");
        } else {
            List<ProxyInfo> proxyInfoList = getProxyInfoDao().listRandomProxyInfos(count);
            StringBuilder buf = new StringBuilder();
            if (CollectionUtil.isNotEmpty(proxyInfoList)) {
                for (ProxyInfo proxyInfo : proxyInfoList) {
                    buf.append(proxyInfo.getHost())
                        .append(":")
                        .append(proxyInfo.getPort())
                        .append("&\n");
                }
            } else {
                // do nothing
            }
            response.getWriter().println(buf.toString());
        }
        
    }
}
