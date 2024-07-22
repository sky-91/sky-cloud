package com.zero.web.customer.controller;

import com.zero.CglibCopier;
import com.zero.api.customer.CustomerService;
import com.zero.api.customer.dto.CustomerAddDTO;
import com.zero.exception.ServiceException;
import com.zero.result.CommonResult;
import com.zero.web.common.BaseController;
import com.zero.web.customer.vo.CustomerAddVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: sky-91
 * @Date: 2024/7/18 15:56
 * @Description: CustomerController
 * @Jdk: 8
 */
@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Resource
    private CustomerService customerService;

    @PostMapping("/add")
    public CommonResult<Boolean> add(@RequestBody CustomerAddVO customerAddVO) {
        CustomerAddDTO dto = CglibCopier.copy(customerAddVO, CustomerAddDTO.class);
        try {
            return customerService.add(dto);
        } catch (ServiceException e) {
            logger.error("新增客户异常，原因：", e);
            return this.failResult(e, "");
        }
    }

}
