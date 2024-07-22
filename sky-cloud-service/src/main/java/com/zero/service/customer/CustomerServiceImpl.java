package com.zero.service.customer;

import cn.hutool.core.lang.UUID;
import com.zero.CglibCopier;
import com.zero.api.customer.CustomerService;
import com.zero.api.customer.dto.CustomerAddDTO;
import com.zero.dao.customer.CustomerDao;
import com.zero.domain.entity.customer.CustomerAddEntity;
import com.zero.exception.BasicDataException;
import com.zero.exception.ServiceException;
import com.zero.result.CommonResult;
import com.zero.service.common.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author: sky-91
 * @Date: 2024/7/19 9:45
 * @Description:
 * @Jdk: 8
 */
@Service
public class CustomerServiceImpl extends BaseService implements CustomerService {

    @Resource
    private CustomerDao customerDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResult<Boolean> add(CustomerAddDTO customerAddDTO) throws ServiceException {
        customerAddDTO.setId(Optional.ofNullable(customerAddDTO.getId()).orElse(UUID.randomUUID().toString(true)));
        customerAddDTO.setCreateTime(Optional.ofNullable(customerAddDTO.getCreateTime()).orElse(System.currentTimeMillis()));
        customerAddDTO.setUpdateTime(Optional.ofNullable(customerAddDTO.getUpdateTime()).orElse(System.currentTimeMillis()));
        CustomerAddEntity entity = CglibCopier.copy(customerAddDTO, CustomerAddEntity.class);
        try {
            if (customerDao.add(entity) > 0) {
                return CommonResult.success(true);
            }
        } catch (BasicDataException e) {
            logger.error("新增客户失败", e);
            throw new ServiceException("", "");
        }
        return CommonResult.fail("", "");
    }
}
