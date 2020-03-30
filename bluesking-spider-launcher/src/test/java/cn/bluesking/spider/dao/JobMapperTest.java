package cn.bluesking.spider.dao;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import cn.bluesking.spider.commons.helper.MybatisHelper;
import cn.bluesking.spider.entity.Education;
import cn.bluesking.spider.entity.Job;
import cn.bluesking.spider.entity.Job.Salary;
import cn.bluesking.spider.entity.WorkExperience;

/**
 * 岗位信息持久层操作测试类。
 * 
 * @author 随心 2020年3月29日
 */
public class JobMapperTest {

    @Test
    public void test() {
        try (SqlSession session = MybatisHelper.getSessionFactory().openSession()) {
            JobMapper mapper = session.getMapper(JobMapper.class);
            String id = UUID.randomUUID().toString();
            mapper.insert(Job.builder()
                             .id(id)
                             .title("职位头衔")
                             .advantage("职位诱惑")
                             .description("岗位描述")
                             .education(Education.BACHELOR)
                             .workExperience(WorkExperience.BETWEEN_1_3_YEAR)
                             .salary(new Salary(10000, 15000))
                             .build());
            session.commit();
            
            Job job = mapper.selectById(id);
            session.commit();
            assertEquals(job.getTitle(), "职位头衔");
            assertEquals(job.getAdvantage(), "职位诱惑");
            assertEquals(job.getDescription(), "岗位描述");
            assertEquals(job.getEducation(), Education.BACHELOR);
            assertEquals(job.getWorkExperience(), WorkExperience.BETWEEN_1_3_YEAR);
            assertEquals(job.getSalary().getMin(), 10000);
            assertEquals(job.getSalary().getMax(), 15000);
            
            mapper.deleteById(id);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
