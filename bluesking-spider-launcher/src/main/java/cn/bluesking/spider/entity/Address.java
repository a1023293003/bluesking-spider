package cn.bluesking.spider.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 位置信息。
 * 
 * @author 随心 2020年3月24日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    /** 地址 ID */
    private String id;
    /** 城市 */
    private String city;
    /** 城区 */
    private String district;
    /** 地址 */
    private String address;
    /** 纬度 */
    private double lat;
    /** 经度 */
    private double lng;

}
