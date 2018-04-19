package com.sync.service.mapper.oracle.rule;



import com.sync.facade.entity.oracle.rule.RouteDeviate;

import java.util.Map;

public interface RouteDeviateMapper {
    //查找线路偏差值
    Map<String,Object> find(RouteDeviate routeDeviate);
    //更新线路偏差值
    void update(RouteDeviate routeDeviate);
}
