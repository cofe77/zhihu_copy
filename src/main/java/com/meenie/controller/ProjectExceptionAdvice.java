package com.meenie.controller;

import com.meenie.exception.BusinessException;
import com.meenie.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler(SystemException.class)
    public ResResult doSystemException(SystemException ex) {
        //记录日志
        //发送消息给运维
        //发送邮件给开发
        return new ResResult(ex.getCode(), ex.getMessage(), null);
    }

    @ExceptionHandler(BusinessException.class)
    public ResResult doBusinessException(BusinessException ex) {
        return new ResResult(ex.getCode(), ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResResult doException(Exception ex) {
        //记录日志
        //发送消息给运维
        //发送邮件给开发
        System.out.println(ex);
        return new ResResult(ResCode.SYSTEM_UNKNOW_ERR, "系统繁忙，请稍后再试！", null);
    }

}
