package cn.bluesking.spider.dao;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import cn.bluesking.spider.commons.helper.MybatisHelper;
import cn.bluesking.spider.entity.Company;
import cn.bluesking.spider.entity.CompanySize;

/**
 * 公司信息持久层操作测试。
 * 
 * @author 随心 2020年3月30日
 */
public class CompanyMapperTest {

    @Test
    public void test() {
        try (SqlSession session = MybatisHelper.getSessionFactory().openSession()) {
            CompanyMapper mapper = session.getMapper(CompanyMapper.class);
            String id = UUID.randomUUID().toString();
            mapper.insert(Company.builder()
                                 .id(id)
                                 .name("公司名称")
                                 .description("公司介绍")
                                 .industryField("公司所属领域")
                                 .registrationDate(LocalDate.of(2020, 3, 29))
                                 .registeredCapital("注册资本：100万")
                                 .companySize(CompanySize.BETWEEN_150_500)
                                 .build());
            session.commit();

            Company company = mapper.selectById(id);
            session.commit();

            assertEquals(company.getName(), "公司名称");
            assertEquals(company.getDescription(), "公司介绍");
            assertEquals(company.getIndustryField(), "公司所属领域");
            assertEquals(company.getRegistrationDate().toString(), "2020-03-29");
            assertEquals(company.getRegisteredCapital(), "注册资本：100万");
            assertEquals(company.getCompanySize(), CompanySize.BETWEEN_150_500);

            mapper.deleteById(id);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
