package cn.bluesking.spider.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 职位信息对象。
 * 
 * @author 随心 2020年3月24日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    /** 工作 ID */
    private String id;
    /** 岗位头衔 */
    private String title;
    /** 薪资 */
    private Salary salary;
    /** 岗位优势 */
    private String advantage;
    /** 岗位描述/要求 */
    private String description;
    /** 岗位所属公司 */
    private Company company;
    /** 工作地址 */
    private Address address;
    /** 学历要求 */
    private Education education;
    /** 工作年限要求 */
    private WorkExperience workExperience;

    public Address getAddress() {
        if (address == null && company != null && company.getAddresses().length > 0) {
            address = company.getAddresses()[0];
        }
        return address;
    }

    /**
     * 薪资对象，单位：人民币元。
     * 
     * @author 随心 2020年3月24日
     */
    @Data
    @NoArgsConstructor
    public static class Salary {
    
        private int min;
        private int max;
        
        public Salary(int min, int max) {
            this.min = min;
            this.max = max;
        }
        
    }

}
