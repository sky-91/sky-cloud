package com.zero.api.customer;

import com.zero.api.customer.dto.CustomerAddDTO;
import com.zero.exception.ServiceException;
import com.zero.result.CommonResult;

/**
 * @Author: sky-91
 * @Date: 2024/7/18 16:53
 * @Description: CustomerService
 * @Jdk: 8
 */
public interface CustomerService {

    CommonResult<Boolean> add(CustomerAddDTO customerAddDTO) throws ServiceException;
}
