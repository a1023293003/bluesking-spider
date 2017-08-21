package cn.bluesking.spider.mobile.lagou.entity;

import java.util.List;

/**
 * 手机版拉勾网json数据解析实体类
 * 
 * @author 随心
 *
 */
public class JsonResult {

	/** 职位信息 */
	List<MobileLagouPosition> result;

	public List<MobileLagouPosition> getResult() {
		return result;
	}

	public void setResult(List<MobileLagouPosition> result) {
		this.result = result;
	}
	
}
