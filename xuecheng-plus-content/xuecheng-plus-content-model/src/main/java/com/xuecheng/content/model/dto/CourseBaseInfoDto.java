package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseBase;
import lombok.Data;

/**
 * @author cornelius
 * @date 2023/5/11 09:22
 * @description 课程基本信息dto 营销信息
 */

@Data
public class CourseBaseInfoDto extends CourseBase {

    /**
     * 收费规则，对应数据字典
     */
    private String charge;

    /**
     * 价格
     */
    private Float price;


    /**
     * 原价
     */
    private Float originalPrice;

    /**
     * 咨询qq
     */
    private String qq;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 电话
     */
    private String phone;

    /**
     * 有效期天数
     */
    private Integer validDays;

    /**
     * 大分类名称
     */
    private String mtName;

    /**
     * 小分类名称
     */
    private String stName;
}
