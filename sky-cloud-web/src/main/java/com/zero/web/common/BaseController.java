package com.zero.web.common;

import com.zero.exception.ServiceException;
import com.zero.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Author: sky-91
 * @Date: 2024/7/19 10:50
 * @Description: BaseController
 * @Jdk: 8
 */
public class BaseController {

    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Resource
    private MessageSource messageSource;

    public String getMessage(String code, String defaultMsg) {
        return this.messageSource
                .getMessage(code, null, defaultMsg, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code) {
        return this.getMessage(code, "");
    }

    protected <T> CommonResult<T> failResult(Exception e, String code) {
        if (e instanceof ServiceException) {
            ServiceException se = (ServiceException) e;
            if (StringUtils.hasText(se.getCode())) {
                return CommonResult.fail(se.getCode(), se.getMessage());
            }
        }
        return CommonResult.fail(code, this.getMessage(code));
    }
}
