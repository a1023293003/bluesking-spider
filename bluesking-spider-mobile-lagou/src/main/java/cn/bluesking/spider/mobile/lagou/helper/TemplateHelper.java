package cn.bluesking.spider.mobile.lagou.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.util.ArrayUtil;
import cn.bluesking.spider.commons.util.JsonUtil;
import cn.bluesking.spider.commons.util.StringUtil;
import cn.bluesking.spider.mobile.lagou.template.TemplateReader;

/**
 * 模板助手类
 * <pre>
 * 圆饼图
 * ${text} 标题（字符串）
 * ${subtext} 子标题（字符串）
 * ${legend} 图例（json格式字符串数组）
 * ${series.name} 提示名称（字符串）
 * ${series.data} 数据键值对（json格式{name:"", value:""}）
 * 柱状图
 * ${text} 标题（字符串）
 * ${subtext} 子标题（字符串）
 * ${xAxis.data} x轴下标名称（json格式的字符串数组）
 * ${yAxis.name} y轴名称（字符串）
 * ${series.name} 提示名称（字符串）
 * ${series.data} 数据值（json格式的数值数组）
 * 多柱状图
 * ${legend.data} 图例（json格式字符串数组）
 * ${xAxis.data} x轴下标名称（json格式的字符串数组）
 * ${yAxis.name} y轴名称（字符串）
 * ${series} 每条柱形图的数据值（多个json格式的数值数组）
 * 内部拼接格式：
 * [{
 * 	name: '$[legend.data图例数组中的元素]',
 * 	type: 'bar',
 * 	data: $[每一条柱子的json格式数值数组]
 * }]
 * </pre>
 * 
 * @author 随心
 *
 */
public class TemplateHelper {

	/** slf4j日志配置 */
	private static final Logger _LOG = LoggerFactory.getLogger(TemplateHelper.class);

	/** ID值 */
	private static int ID = 1;
	
	/**
	 * 提供数据,获取一个圆饼图前端代码块
	 * @param text [String]标题
	 * @param subtext [String]子标题
	 * @param legend [String[]]图例
	 * @param seriesName [String]提示名称
	 * @param seriesData [int[]]数据值,和图例组成键值对
	 * @return
	 */
	public static String getPieChart(String text, String subtext, String[] legend,
			String seriesName, Integer[] seriesData) {
		if(StringUtil.isEmpty(text) || 
				StringUtil.isEmpty(subtext) || StringUtil.isEmpty(seriesName)) {
			throw new IllegalArgumentException("参数不能为空");
		}
		// 有数据需要显示
		if(ArrayUtil.isEmpty(seriesData) ||
				ArrayUtil.isEmpty(legend) || legend.length != seriesData.length) {
			throw new IllegalArgumentException("参数不能为空");
		} else {
			return getPieChart0(text, subtext, legend, seriesName, seriesData);
		}
	}
	
	private static String getPieChart0(String text, String subtext, String[] legend,
			String seriesName, Integer[] seriesData) {
		String[] keys = {"${text}", "${subtext}", "${legend}", "${series.name}", "${series.data}"};
		String[] values = new String[keys.length];
		values[0] = text;
		values[1] = subtext;
		values[2] = JsonUtil.toJson(legend);
		values[3] = seriesName;
		StringBuilder sBuilder = new StringBuilder("[");
		sBuilder.append("{name:'" + legend[0] + 
				"', value:" + seriesData[0] + "}");
		for(int i = 1; i < legend.length; i ++) {
			sBuilder.append(",{name:'" + legend[i] + 
					"', value:" + seriesData[i] + "}");
		}
		sBuilder.append("]");
		values[4] = sBuilder.toString();
		return replaceKeyWord(
				TemplateReader.getTemplate(TemplateReader.PIE_CHART), keys, values)
				.replace("pieChart", "pieChart" + (ID ++));
	}

	/**
	 * 提供数据,获取一个条形图前端代码块
	 * @param text [String]标题
	 * @param subtext [String]子标题
	 * @param xAxisData [String[]]x轴下标名称
	 * @param yAxisName [String]y轴名称
	 * @param seriesName [String]提示名称
	 * @param seriesData [int[]]数据值,和x轴下标名称组成键值对
	 * @return
	 */
	public static String getBarChart(String text, String subtext, String[] xAxisData, 
			String yAxisName, String seriesName, Integer[] seriesData) {
		if(StringUtil.isEmpty(text) || 
				StringUtil.isEmpty(subtext) || 
				StringUtil.isEmpty(yAxisName) || StringUtil.isEmpty(seriesName)) {
			throw new IllegalArgumentException("参数不能为空");
		}
		if(ArrayUtil.isEmpty(xAxisData) || ArrayUtil.isEmpty(seriesData)) {
			throw new IllegalArgumentException("参数不能为空");
		} else {
			return getBarChart0(text, subtext, xAxisData, yAxisName, seriesName, seriesData);
		}
	}

	private static String getBarChart0(String text, String subtext, String[] xAxisData, 
			String yAxisName, String seriesName, Integer[] seriesData) {
		String[] keys = {"${text}", "${subtext}", "${xAxis.data}", "${yAxis.name}", "${series.name}", "${series.data}"};
		String[] values = new String[keys.length];
		values[0] = text;
		values[1] = subtext;
		values[2] = JsonUtil.toJson(xAxisData);
		values[3] = yAxisName;
		values[4] = seriesName;
		values[5] = JsonUtil.toJson(seriesData);
		return replaceKeyWord(
				TemplateReader.getTemplate(TemplateReader.BAR_CHART), keys, values)
				.replace("barChart", "barChart" + (ID ++));
	}
	
	/**
	 * 提供数据,获取一个多条形图前端代码块
	 * <pre>
	 * 内部拼接格式：
	 * [{
	 * 	name: '$[legend.data图例数组中的元素]',
	 * 	type: 'bar',
	 * 	data: $[每一条柱子的json格式数值数组]
	 * }]
	 * </pre>
	 * @param legendData [String[]]图例
	 * @param xAxisData [String[]]x轴下标名称
	 * @param yAxisName [String]y轴名称
	 * @param seriesDatas [int[][]]每条柱形图的数据值
	 * @return
	 */
	public static String getMultiBarChart(String[] legendData, String[] xAxisData, 
			String yAxisName, Integer[][] seriesDatas) {
		if(StringUtil.isEmpty(yAxisName)) {
			throw new IllegalArgumentException("参数不能为空");
		}
		if(ArrayUtil.isEmpty(legendData) || 
				ArrayUtil.isEmpty(xAxisData) || ArrayUtil.isEmpty(seriesDatas)) {
			throw new IllegalArgumentException("参数不能为空");
		}
		return getMultiBarChart0(legendData, xAxisData, yAxisName, seriesDatas);
	}
	
	private static String getMultiBarChart0(String[] legendData, String[] xAxisData, 
			String yAxisName, Integer[][] seriesDatas) {
		String[] keys = {"${legend.data}", "${xAxis.data}", "${yAxis.name}", "${series}"};
		String[] values = new String[keys.length];
		values[0] = JsonUtil.toJson(legendData);
		values[1] = JsonUtil.toJson(xAxisData);
		values[2] = yAxisName;
		StringBuilder sBuilder = new StringBuilder("[");
		for(int i = 0, maxI = seriesDatas.length - 1; 
				i < seriesDatas.length; i ++) {
			sBuilder.append("{name:'" + legendData[i] + 
					"',type: 'bar',data:[" + seriesDatas[i][0]);
			for(int k = 1; k < seriesDatas[i].length; k ++) {
				sBuilder.append("," + seriesDatas[i][k]);
			}
			if(i < maxI) {
				sBuilder.append("]},");
			} else {
				sBuilder.append("]}");
			}
		}
		sBuilder.append("]");
		values[3] = sBuilder.toString();
		return replaceKeyWord(
				TemplateReader.getTemplate(TemplateReader.MULTI_BAR_CHART), keys, values)
				.replace("multiBarChart", "multiBarChart" + (ID ++));
	}
	
	/**
	 * 关键字替换
	 * @param content [String]模板
	 * @param keys [String[]]关键字
	 * @param values [String[]]替换值
	 * @return
	 */
	private static String replaceKeyWord(String content, String[] keys, 
			String[] values) {
		for(int i = 0; i < keys.length; i ++) {
			content = content.replace(keys[i], values[i]);
		}
		return content;
	}
	
	public static void main(String[] args) {
		System.out.println(getPieChart("广州Java招聘工作分布", "2017年8月29日", 
				new String[] {"未爬取到明确城区", "南沙区","天河区","海珠区","番禺区","白云区","花都区","荔湾区","萝岗区","越秀区","黄埔区"},
				"直接访问", new Integer[] {29,7,799,168,102,37,5,26,61,120,18}));
	}
}
