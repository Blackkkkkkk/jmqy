package com.dhxx.service.app.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.dhxx.common.exceptions.BusinessException;
import com.dhxx.facade.message.ResponseMessage;

public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {
    
    private static Logger log = LoggerFactory.getLogger(BusinessExceptionMapper.class);

    public Response toResponse(BusinessException ex) {
        log.debug("业务异常>>>>>>>>>>>>>>>code:" + ex.getCode() + ", message:" + ex.getMsg());
        ResponseMessage rm = new ResponseMessage(false, ex.getMsg());
        return Response.status(Response.Status.OK).entity(rm).type(ContentType.APPLICATION_JSON_UTF_8).build();
    }
}