package com.dhxx.service.service.rule;


import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.rule.RouteDeviate;

import com.dhxx.facade.service.rule.RouteDeviateFacade;
import com.dhxx.service.biz.rule.RouteDeviateBiz;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service(protocol = {"dubbo"})
public class RouteDeviateFacadelmpl implements RouteDeviateFacade{

    @Autowired
    RouteDeviateBiz routeDeviateBiz;

   public void update(RouteDeviate routeDeviate){ routeDeviateBiz.update(routeDeviate);}

   public Map<String,Object> find(RouteDeviate routeDeviate){return  routeDeviateBiz.find(routeDeviate);}

}
