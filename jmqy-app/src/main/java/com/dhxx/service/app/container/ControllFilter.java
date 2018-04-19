package com.dhxx.service.app.container;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import com.dhxx.facade.constant.Constant;

/**
 * <p> 类说明 </p>
 * @author jhy
 * Date: 2017年3月31日
 * @version 1.01
 * 设置允许跨域
 */
public class ControllFilter implements ContainerResponseFilter {

    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {  
        response.getHeaders().add("Access-Control-Allow-Origin", Constant.IP);  
        response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, token");  
        response.getHeaders().add("Access-Control-Allow-Methods", "POST");  
        response.getHeaders().add("Access-Control-Max-Age", "3600");  
    } 
    
}
