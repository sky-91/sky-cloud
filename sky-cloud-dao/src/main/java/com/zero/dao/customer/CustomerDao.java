package com.zero.dao.customer;

import com.zero.domain.entity.customer.CustomerAddEntity;
import com.zero.exception.BasicDataException;

/**
 * @Author: sky-91
 * @Date: 2024/7/19 14:32
 * @Description: CustomerDao
 * @Jdk: 8
 */
public interface CustomerDao {

    Integer add(CustomerAddEntity customerAddEntity) throws BasicDataException;
}
