package cn.bluesking.spider.mobile.lagou;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.entity.FieldsInfo;
import cn.bluesking.spider.commons.helper.MybatisHelper;
import cn.bluesking.spider.commons.mapper.MobileLagouPositionMapper;
import cn.bluesking.spider.commons.util.JsonUtil;
import cn.bluesking.spider.commons.util.StringUtil;
import cn.bluesking.spider.mobile.lagou.helper.TemplateHelper;

/**
 * 爬取结果生成
 * 
 * @author 随心
 *
 */
public class GenerateResult {

	/** slf4j日志配置 */
	private static final Logger _LOG = LoggerFactory.getLogger(GenerateResult.class);

	private static String PATH;
	static {
		try {
			PATH = GenerateResult.class.
					getClassLoader().getResource("").toURI().getPath() + "result";
		} catch (URISyntaxException e) {
			try {
				PATH = GenerateResult.class.
						getClassLoader().getResource("").toURI().getPath();
			} catch (URISyntaxException e1) {
				PATH = "";
			}
		}
	}
	
	/**
	 * 生成爬取结果的html
	 * @param city [String]城市
	 * @param keyWord [String]关键字
	 * @param data [String]日期
	 */
	public static void generateResultToHtml(String city, String keyWord, String data) {
		String workYearPieChart = getWorkYearPieChart(city, keyWord, data);
		String districtBarChart = getDistrictBarChart(city, keyWord, data);
		String DWmultiBarChart = getDistrictAndKeyWorkMultiBarChart(city, keyWord);
		PrintWriter pw = null;
		try {
			pw = getPrintWriter(city, keyWord, data);
			pw.print("<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
					"    <meta charset=\"utf-8\">\r\n" + 
					"    <title>ECharts</title>\r\n" + 
					"    <!-- 引入 echarts.js -->\r\n" + 
					"    <script src=\"../js/echarts.min.js\"></script>\r\n" + 
					"   <style>\r\n" + 
					"    	h2 {\r\n" + 
					"    		text-align: center;\r\n" + 
					"    	}\r\n" + 
					"    	#pieChart, #barChart, #multiBarChart {\r\n" + 
					"    		margin: auto;\r\n" + 
					"    	}\r\n" + 
					"    </style>\r\n" + 
					"</head>\r\n" + 
					"<body>\r\n" + 
					"	<h2>以下数据均爬取自拉勾网</h2>\r\n");
			pw.println(workYearPieChart);
			pw.println(districtBarChart);
			pw.println(DWmultiBarChart);
			pw.print("</body>\r\n" + 
					"</html>");
			pw.flush();
		} catch (IOException e) {
			_LOG.error("获取PrintWriter失败" + e.getMessage());
		} finally {
			if(pw != null) {
				pw.close();
			}
		}
	}
	
	/**
	 * 获取输出文件的PrintWriter
	 * @return
	 * @throws IOException
	 */
	private static PrintWriter getPrintWriter(String city, 
			String keyWord, String data) throws IOException {
		File file = new File(PATH);
		if(!file.exists()) {
			file.mkdirs();
		}
		String fileName = PATH + "/result-" + 
				city + "-" + keyWord + "-" + data + ".html";
		file = new File(fileName);
		if(!file.exists()) {
			file.createNewFile();
		}
		return new PrintWriter(fileName, "UTF-8");
	}
	
	/**
	 * 获取工作经验要求圆饼图
	 * @param city [String]城市
	 * @param keyWord [String]关键字
	 * @param data [String]日期
	 * @return
	 */
	private static String getWorkYearPieChart(String city, String keyWord, String data) {
		// 获取数据库会话
		SqlSession session = MybatisHelper.getSessionFactory().openSession();
		MobileLagouPositionMapper mapper = session.getMapper(MobileLagouPositionMapper.class);
		// 获取工作经验类别及其职位数量
		List<FieldsInfo> fieldInfos = mapper.selectFieldByGroup("work_year", city, keyWord, null);
		String[] workYears = new String[fieldInfos.size()];
		Integer[] counts = new Integer[workYears.length];
		for(int i = 0; i < fieldInfos.size(); i ++) {
			workYears[i] = fieldInfos.get(i).getWorkYear();
			counts[i] = fieldInfos.get(i).getCount();
		}
		session.commit();
		session.close();
		return TemplateHelper.getPieChart(city + keyWord + "招聘工作经验要求职位分布", 
				data, workYears, "职位数量", counts);
	}
	
	/**
	 * 获取职位城区分布条形图
	 * @param city [String]城市
	 * @param keyWord [String]关键字
	 * @param data [String]日期
	 * @return
	 */
	private static String getDistrictBarChart(String city, String keyWord, String data) {
		// 获取数据库会话
		SqlSession session = MybatisHelper.getSessionFactory().openSession();
		MobileLagouPositionMapper mapper = session.getMapper(MobileLagouPositionMapper.class);
		// 获取城区类别及其职位数量
		List<FieldsInfo> fieldInfos = mapper.selectFieldByGroup("district", city, keyWord, null);
		String[] districts = new String[fieldInfos.size()];
		Integer[] counts = new Integer[districts.length];
		for(int i = 0; i < fieldInfos.size(); i ++) {
			districts[i] = StringUtil.isEmpty(fieldInfos.get(i).getDistrict()) ?
					"未爬取到详细城区" : fieldInfos.get(i).getDistrict();
			counts[i] = fieldInfos.get(i).getCount();
		}
		session.commit();
		session.close();
		return TemplateHelper.getBarChart(city + keyWord + "招聘职位城区分布", 
				data, districts, "职位数量", "职位数量", counts);
	}
	
	/**
	 * 获取职位城区和工作经验要求分布多条形图
	 * @param city [String]城市
	 * @param keyWord [String]关键字
	 * @return
	 */
	private static String getDistrictAndKeyWorkMultiBarChart(String city, String keyWord) {
		// 获取数据库会话
		SqlSession session = MybatisHelper.getSessionFactory().openSession();
		MobileLagouPositionMapper mapper = session.getMapper(MobileLagouPositionMapper.class);
		// 获取工作经验类别及其职位数量
		List<FieldsInfo> workYearsInfos = mapper.selectFieldByGroup("work_year", city, keyWord, null);
		String[] workYears = new String[workYearsInfos.size()];
		for(int i = 0; i < workYearsInfos.size(); i ++) {
			workYears[i] = workYearsInfos.get(i).getWorkYear();
		}
		// 获取城区类别及其职位数量
		List<FieldsInfo> districtInfos = mapper.selectFieldByGroup("district", city, keyWord, null);
		String[] districts = new String[districtInfos.size()];
		for(int i = 0; i < districtInfos.size(); i ++) {
			districts[i] = StringUtil.isEmpty(districtInfos.get(i).getDistrict()) ?
					"未爬取到详细城区" : districtInfos.get(i).getDistrict();;
		}
		// 职位分布数值[经验][城区]
		Integer[][] counts = new Integer[workYears.length][districts.length];
		for(int i = 0; i < workYears.length; i ++) {
			// 获取指定工作经验要求的城区类别及其职位数量分布
			List<FieldsInfo> districtAndCount = mapper.
					selectFieldByGroup("district", city, keyWord, workYears[i]);
			for(int k = 0; k < districtAndCount.size(); k ++) {
				counts[i][k] = districtAndCount.get(k).getCount();
			}
		}
		session.commit();
		session.close();
		if(workYears.length > 4) {
			String[] newWorkYears = new String[workYears.length + 1];
			for(int i = 0; i< 4; i ++) {
				newWorkYears[i] = workYears[i];
			}
			newWorkYears[4] = "";
			for(int i = 4; i< workYears.length; i ++) {
				newWorkYears[i + 1] = workYears[i];
			}
			return TemplateHelper.getMultiBarChart(workYears, districts, "职位数量", counts).
					replaceAll("(legend: \\{[\\s\\S]*?data:)([\\s\\S]+?)\\],", 
					"$1" + JsonUtil.toJson(newWorkYears) + ",");
		} else {
			return TemplateHelper.getMultiBarChart(workYears, districts, "职位数量", counts);
		}
	}
	
	public static void main(String[] args) throws URISyntaxException {
		generateResultToHtml("深圳", "Java", "2017年8月29日");
		generateResultToHtml("广州", "Java", "2017年8月29日");
	}
}
