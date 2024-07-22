package com.zero.dao.customer.impl;

import com.zero.dao.common.BaseDao;
import com.zero.dao.customer.CustomerDao;
import com.zero.domain.entity.customer.CustomerAddEntity;
import com.zero.exception.BasicDataException;
import com.zero.repository.customer.CustomerMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author: sky-91
 * @Date: 2024/7/19 14:36
 * @Description: CustomerDaoImpl
 * @Jdk: 8
 */
@Repository
public class CustomerDaoImpl extends BaseDao implements CustomerDao {

    @Resource
    private CustomerMapper customerMapper;

    @Override
    public Integer add(CustomerAddEntity customerAddEntity) throws BasicDataException {
        try {
            return customerMapper.insert(customerAddEntity);
        } catch (Exception e) {
            logger.error("新增客户失败，原因：", e);
            throw new BasicDataException(e);
        }
    }
}
