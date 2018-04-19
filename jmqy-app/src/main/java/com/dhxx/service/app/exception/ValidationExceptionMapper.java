package com.dhxx.service.app.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.protocol.rest.RpcExceptionMapper;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.dhxx.common.exceptions.BusinessException;
import com.dhxx.facade.message.ResponseMessage;

public class ValidationExceptionMapper extends RpcExceptionMapper {
    
    private static Logger log = LoggerFactory.getLogger(ValidationExceptionMapper.class);

    @Override
    protected Response handleConstraintViolationException(ConstraintViolationException cve) {
        String message = null;
        StringBuffer msg = new StringBuffer();
        for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
            msg.append(cv.getMessage());
        }
        message = msg.toString();
        log.debug("服务异常>>>>>>>>>>>>>>>code: " + BusinessException.PARAMETER_ERROR.getCode() + ", message:" + message);
        ResponseMessage rm = new ResponseMessage(false, message);
        // 采用json输出代替xml输出
        return Response.status(Response.Status.OK).entity(rm).type(ContentType.APPLICATION_JSON_UTF_8).build();
    }

}
