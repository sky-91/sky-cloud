package com.zero.domain.entity.customer;

import com.zero.entity.CommonBaseTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: sky-91
 * @Date: 2024/7/19 14:34
 * @Description: CustomerUpdateEntity
 * @Jdk: 8
 */
@Getter
@Setter
public class CustomerUpdateEntity extends CommonBaseTime {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别,0-男1-女2-未知
     */
    private Integer sex;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 照片
     */
    private String photo;

    private String remark;
}
