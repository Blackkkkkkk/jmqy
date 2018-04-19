package com.dhxx.service.biz.rule;

import com.dhxx.facade.entity.rule.RouteDeviate;
import com.dhxx.service.mapper.rule.RouteDeviateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
@Transactional
public class RouteDeviateBiz {


    @Autowired
    private RouteDeviateMapper routeDeviateMapper;

    public void  update(RouteDeviate routeDeviate){routeDeviateMapper.update(routeDeviate);}

    public Map<String,Object> find(RouteDeviate routeDeviate){ return  routeDeviateMapper.find(routeDeviate);}
}
