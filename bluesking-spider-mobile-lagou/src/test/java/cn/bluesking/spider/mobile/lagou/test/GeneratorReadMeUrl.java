package cn.bluesking.spider.mobile.lagou.test;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import cn.bluesking.spider.commons.util.CaseUtil;
import cn.bluesking.spider.commons.util.CodecUtil;
import cn.bluesking.spider.commons.util.RegexUtil;

public class GeneratorReadMeUrl {

    public static void main(String[] args) {
        String prefix = "https://a1023293003.github.io/StaticHtmlPage/lagou/";
        String path = "E:\\GitHud\\StaticHtmlPage\\lagou";
        File dir = new File(path);
        String fileName;
        String city;
        String keyWord;
        String date;
        Map<Integer, StringBuilder> bufMap = new TreeMap<Integer, StringBuilder>();
        Integer year;
        StringBuilder buf;
        for (File file : dir.listFiles()) {
            fileName = file.getName();
//            System.out.println(file.getName());
            city = RegexUtil.
                    regexAString(fileName, "result-([\\w\\W]+?)-([\\w\\W]+?)-([\\w\\W]+?).html", 1);
//            System.out.println(city);
            keyWord = RegexUtil.
                    regexAString(fileName, "result-([\\w\\W]+?)-([\\w\\W]+?)-([\\w\\W]+?).html", 2);
//            System.out.println(keyWord);
            date = RegexUtil.
                    regexAString(fileName, "result-([\\w\\W]+?)-([\\w\\W]+?)-([\\w\\W]+?).html", 3);
//            System.out.println(date);
            year = CaseUtil.caseInt(RegexUtil.regexAString(date, "([\\d]+?)年"));
            if (bufMap.containsKey(year)) {
                buf = bufMap.get(year);
            } else {
                buf = new StringBuilder("> " + year + "年拉勾网爬虫爬取结果\n\n");
                bufMap.put(year, buf);
            }
            buf.append("[" + date + city + keyWord + "职位分布](" + prefix + CodecUtil.encodeURL(fileName) + ")\n\n");
        }
        buf = new StringBuilder();
        for (Entry<Integer, StringBuilder> entry : bufMap.entrySet()) {
            buf.append(entry.getValue());
        }
        System.out.println(buf.toString());
    }
}
