package cn.bluesking.spider.mobile.lagou.test;

import java.io.IOException;

import cn.bluesking.spider.commons.util.HttpUtil;
import cn.bluesking.spider.commons.util.RegexUtil;

public class TestTranslate {

    public static void main(String[] args) throws IOException {
        String result = HttpUtil.httpBrowserGet("http://fanyi.baidu.com/#en/zh/apple");
        System.out.println("请求结束：" + result);
        result = RegexUtil.regexAString(result, "::before([\\s\\S]+?)::after");
        System.out.println(result);
    }
}
