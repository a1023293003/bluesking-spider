package cn.bluesking.spider.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公司对象。
 * 
 * @author 随心 2020年3月24日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    /** 公司 ID */
    private String id;
    /** 公司名称 */
    private String name;
    /** 公司介绍 */
    private String description;
    /** 公司地址 */
    private Address[] addresses;
    /** 公司所属领域 */
    private String industryField;
    /** 公司规模 */
    private CompanySize companySize;
    /** 公司注册时间 */
    private LocalDate registrationDate;
    /** 公司注册资本 */
    private String registeredCapital;

}
