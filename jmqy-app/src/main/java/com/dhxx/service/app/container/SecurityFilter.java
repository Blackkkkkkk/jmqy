package com.dhxx.service.app.container;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.dhxx.facade.entity.user.UserInfo;
import com.dhxx.service.app.authorization.annotation.Authorization;
import com.dhxx.service.app.authorization.manager.TokenManager;

public class SecurityFilter implements ContainerRequestFilter {

    private static Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    @Context
    private transient HttpServletRequest servletRequest;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String token = servletRequest.getHeader("token");

        ApplicationContext context = ServiceBean.getSpringContext();
        TokenManager manager = context.getBean(TokenManager.class);

        UserInfo userInfo = null;
        try {
            userInfo = manager.getToken(token);// 从redis获取
            if (manager.checkToken(userInfo)) {
                log.info("the user Account is " + userInfo.getUserAccount() + " using token is " + token);
                requestContext.setProperty("userInfo", userInfo);
                return;
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        userInfo = new UserInfo();
        userInfo.setUserName("test");
        requestContext.setProperty("userInfo", userInfo);
        Method method = resourceInfo.getResourceMethod();
        if (method.getAnnotation(Authorization.class) != null) {
            log.info("token>>>>>>>>>" + token + ">>>>>>>>>>用户未登录");
            Response response = bulidUnauthResponse("");
            requestContext.abortWith(response);
            return;
        }

        return;
    }

    private Response bulidUnauthResponse(String context) {
        return Response.ok().status(401).entity(context).build();
    }

}