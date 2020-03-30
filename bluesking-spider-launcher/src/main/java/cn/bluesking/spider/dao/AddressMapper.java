package cn.bluesking.spider.dao;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;

import cn.bluesking.spider.entity.Address;
import cn.bluesking.spider.entity.Company;

/**
 * 地址信息持久层操作接口。
 * 
 * @author 随心
 * 2020年3月30日
 */
public interface AddressMapper extends BaseMapper<Address> {

    Collection<Address> selectByCompanyId(@Param("id") String id);
    int insertCompanyToAddress(@Param("company") Company company);

}
