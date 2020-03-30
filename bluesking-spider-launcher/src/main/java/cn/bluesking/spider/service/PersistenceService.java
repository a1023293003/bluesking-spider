package cn.bluesking.spider.service;

import cn.bluesking.spider.entity.Address;
import cn.bluesking.spider.entity.Company;
import cn.bluesking.spider.entity.Job;

/**
 * 持久化相关操作服务接口。
 * 
 * @author 随心
 * 2020年3月29日
 */
public interface PersistenceService {

    boolean save(Job job);
    boolean save(Address address);
    boolean save(Company company);

    Job findJobById(String id);
    Address findAddressById(String id);
    Company findCompanyById(String id);

    boolean deleteJobById(String id);
    boolean deleteAddressById(String id);
    boolean deleteCompanyById(String id);

}
