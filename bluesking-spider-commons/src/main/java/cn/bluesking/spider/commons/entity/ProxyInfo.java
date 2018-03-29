package cn.bluesking.spider.commons.entity;


public class ProxyInfo {

	/**
	 * 代理编号
	 */
	private Integer proxyId = null;

	/**
	 * ip地址
	 */
	private String ip = null;

	/**
	 * 端口号
	 */
	private Integer port = null;

	/**
	 * 有效次数
	 */
	private Integer status = null;

	public void setProxyId(Integer proxyId) {
		this.proxyId = proxyId;
	}

	public Integer getProxyId() {
		return this.proxyId;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return this.ip;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

}