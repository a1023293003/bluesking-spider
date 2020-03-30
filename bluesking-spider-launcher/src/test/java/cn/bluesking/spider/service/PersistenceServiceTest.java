package cn.bluesking.spider.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import cn.bluesking.spider.entity.Address;
import cn.bluesking.spider.entity.Company;
import cn.bluesking.spider.entity.CompanySize;
import cn.bluesking.spider.entity.Education;
import cn.bluesking.spider.entity.Job;
import cn.bluesking.spider.entity.WorkExperience;
import cn.bluesking.spider.entity.Job.Salary;

/**
 * 持久化服务类测试。
 * 
 * @author 随心 2020年3月30日
 */
public class PersistenceServiceTest {

    public static PersistenceService SERVICE = PersistenceServiceFactory.SINGLETON;

    @Test
    public void test() {
        Address[] addresses = new Address[2];
        addresses[0] = Address.builder()
                              .id(UUID.randomUUID().toString())
                              .city("广州")
                              .district("黄埔区")
                              .address("大沙东")
                              .lat(123.123)
                              .lng(45.45)
                              .build();
        addresses[1] = Address.builder()
                              .id(UUID.randomUUID().toString())
                              .city("深圳")
                              .district("南山区")
                              .address("塘朗")
                              .lat(125.125)
                              .lng(43.43)
                              .build();
        Company source = Company.builder()
                                .id(UUID.randomUUID().toString())
                                .name("公司名称")
                                .description("公司介绍")
                                .industryField("公司所属领域")
                                .registrationDate(LocalDate.of(2020, 3, 29))
                                .registeredCapital("注册资本：100万")
                                .companySize(CompanySize.BETWEEN_150_500)
                                .addresses(addresses)
                                .build();
        // 预期：存储 Company 信息的时候会将 Address 信息及两者的关系一起存储起来。
        // 涉及表：company、address、company_to_address
        SERVICE.save(source);
        // 预期：查询 Company 信息的时候会将关联的 Address 信息一并查询出来。
        // 涉及表：company、address、company_to_address
        Company record = SERVICE.findCompanyById(source.getId());
        assertEquals(source.hashCode(), record.hashCode());
        
        Address address = Address.builder()
                                 .id(UUID.randomUUID().toString())
                                 .city("广州")
                                 .district("黄埔区")
                                 .address("大沙东")
                                 .lat(123.123)
                                 .lng(45.45)
                                 .build();
        
        Job job = Job.builder()
                     .id(UUID.randomUUID().toString())
                     .title("职位头衔")
                     .advantage("职位诱惑")
                     .description("岗位描述")
                     .education(Education.BACHELOR)
                     .workExperience(WorkExperience.BETWEEN_1_3_YEAR)
                     .salary(new Salary(10000, 15000))
                     .address(address)
                     .company(source)
                     .build();
        // 预期：存储 Job 信息的时候会将 Address 信息一并存储起来。
        // 涉及表：job、address
        SERVICE.save(job);
        // 预期：查询 Job 信息的时候会将关联的 Address 信息和 Company 信息查询出来。
        // 涉及表：job、address、company、company_to_address
        Job result = SERVICE.findJobById(job.getId());
        assertEquals(job.hashCode(), result.hashCode());
        
        // 预期：删除 Job 信息会将关联的 Address 信息一并删除。
        // 涉及表：job、address
        SERVICE.deleteJobById(job.getId());
        assertNull(SERVICE.findAddressById(address.getId()));
        
        // 预期：删除 Company 信息会将关联的 Address 及其关系记录删除。
        // 涉及表：company、address、company_to_address
        SERVICE.deleteCompanyById(source.getId());
        for (Address addr : addresses) {
            assertNull(SERVICE.findAddressById(addr.getId()));
        }
    }

}
