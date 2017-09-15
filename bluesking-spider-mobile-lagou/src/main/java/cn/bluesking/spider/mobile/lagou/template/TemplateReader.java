package cn.bluesking.spider.mobile.lagou.template;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.util.StreamUtil;
import cn.bluesking.spider.commons.util.StringUtil;

public class TemplateReader {

	/** slf4j日志配置 */
	private static final Logger _LOG = LoggerFactory.getLogger(TemplateReader.class);
	
	/** 圆饼图 */
	public static final String PIE_CHART = "template/pieChart.template";
	
	/** 条形图 */
	public static final String BAR_CHART = "template/barChart.template";
	
	/** 多条条形图 */
	public static final String MULTI_BAR_CHART = "template/multiBarChart.template";
	
	/** 模板缓存 */
	private static Map<String, String> templateCache = new HashMap<String, String>();
	
	/**
	 * 获取模板
	 * @param path [String]模板路径
	 * @return
	 */
	public static String getTemplate(String path) {
		if(StringUtil.isNotEmpty(path)) {
			// 优先读取缓存
			if(templateCache.containsKey(path)) {
				return templateCache.get(path);
			} else {
				InputStream inStream = null;
				try {
					// 获取资源输入流
					inStream = TemplateReader.class.getClassLoader().getResourceAsStream(path);
					String template = StreamUtil.getStringWithLF(inStream);
					// 存入缓存中
					templateCache.put(path, template);
					return template;
				} catch (Exception e) {
					_LOG.error(path + "路径有误,获取模板资源出错");
					return "";
				}
			}
		} else {
			return "";
		}
	}
	
}
