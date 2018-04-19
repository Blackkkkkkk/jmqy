package com.sync.service.biz.order.rule;


import com.sync.facade.entity.oracle.rule.RouteDeviate;
import com.sync.service.mapper.oracle.rule.RouteDeviateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
@Transactional
public class RouteDeviateBiz {


    @Autowired
    private RouteDeviateMapper routeDeviateMapper;


    public Map<String,Object> find(RouteDeviate routeDeviate){ return  routeDeviateMapper.find(routeDeviate);}
}
