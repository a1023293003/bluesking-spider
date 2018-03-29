package cn.bluesking.spider.proxyPool.entity;

/**
 * IP代理信息对象
 * 
 * @author 随心
 *
 */
public class ProxyInfo {

    /** 代理编号 */
    private Integer proxyId;

    /** ip地址 */
    private String host;

    /** 端口号 */
    private Integer port;

    /** 有效次数 */
    private Double status = 0.0;

    public ProxyInfo() {
    };

    public ProxyInfo(String host, Integer port, Double status) {
        super();
        this.host = host;
        this.port = port;
        this.status = status;
    }

    public void setProxyId(Integer proxyId) {
        this.proxyId = proxyId;
    }

    public Integer getProxyId() {
        return this.proxyId;
    }

    public void setHost(String ip) {
        this.host = ip;
    }

    public String getHost() {
        return this.host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setStatus(Double status) {
        this.status = status;
    }

    public Double getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return "ProxyInfo [proxyId=" + proxyId + ", ip=" + host + ", port=" + port + ", status=" + status + "]";
    }

}