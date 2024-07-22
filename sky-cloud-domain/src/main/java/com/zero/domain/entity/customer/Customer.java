package com.zero.domain.entity.customer;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: sky-91
 * @Date: 2024/7/19 17:02
 * @Description: 客户表
 * @Jdk: 8
 */
@Getter
@Setter
public class Customer {
    private String id;

    private String name;

    /**
    * 0-男1-女2-未知
    */
    private Short sex;

    private String idCard;

    private String telephone;

    private String email;

    private String photo;

    private String remark;

    private Long createTime;

    private Long updateTime;
}