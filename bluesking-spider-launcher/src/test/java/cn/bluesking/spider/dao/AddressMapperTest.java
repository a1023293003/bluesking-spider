package cn.bluesking.spider.dao;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import cn.bluesking.spider.commons.helper.MybatisHelper;
import cn.bluesking.spider.entity.Address;

/**
 * 地址信息持久层操作测试。
 * 
 * @author 随心 2020年3月30日
 */
public class AddressMapperTest {

    @Test
    public void test() {
        try (SqlSession session = MybatisHelper.getSessionFactory().openSession()) {
            AddressMapper mapper = session.getMapper(AddressMapper.class);
            String id = UUID.randomUUID().toString();
            mapper.insert(Address.builder()
                                 .id(id)
                                 .city("广州")
                                 .district("黄埔区")
                                 .address("大沙东")
                                 .lat(123.123)
                                 .lng(45.45)
                                 .build());
            session.commit();

            Address address = mapper.selectById(id);
            session.commit();
            assertEquals(address.getCity(), "广州");
            assertEquals(address.getDistrict(), "黄埔区");
            assertEquals(address.getAddress(), "大沙东");
            assertEquals(address.getLat(), 123.123, 1e-15);
            assertEquals(address.getLng(), 45.45, 1e-15);

            mapper.deleteById(id);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
