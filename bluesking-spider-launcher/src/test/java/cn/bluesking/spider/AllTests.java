package cn.bluesking.spider;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

/**
 * 单元测试套件。
 * 
 * @author 随心
 * 2020年3月30日
 */
@RunWith(JUnitPlatform.class)
@SelectPackages({
    "cn.bluesking.spider.dao", 
    "cn.bluesking.spider.service", 
})
public class AllTests {

}
