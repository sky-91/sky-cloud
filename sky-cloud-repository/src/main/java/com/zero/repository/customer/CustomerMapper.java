package com.zero.repository.customer;

import com.zero.domain.entity.customer.Customer;
import com.zero.domain.entity.customer.CustomerAddEntity;
import com.zero.domain.entity.customer.CustomerUpdateEntity;

/**
 * @Author: sky-91
 * @Date: 2024/7/19 17:02
 * @Description:
 * @Jdk: 8
 */
public interface CustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerAddEntity entity);

    int insertSelective(CustomerAddEntity entity);

    Customer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerUpdateEntity entity);

    int updateByPrimaryKey(CustomerUpdateEntity entity);
}