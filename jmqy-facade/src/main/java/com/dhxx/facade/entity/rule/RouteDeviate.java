package com.dhxx.facade.entity.rule;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * @author xwq
 * @date 2018/1/25
 * @description
 * 线路偏差值设置
 */

public class RouteDeviate extends SysBaseEntity {
    private String id ;
    private String routeDeviateValue;   //线路偏差点设置
    private String routeDeviateDistance; //线路偏差距离设置

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteDeviateValue() {
        return routeDeviateValue;
    }

    public void setRouteDeviateValue(String routeDeviateValue) {
        this.routeDeviateValue = routeDeviateValue;
    }

    public String getRouteDeviateDistance() {
        return routeDeviateDistance;
    }

    public void setRouteDeviateDistance(String routeDeviateDistance) {
        this.routeDeviateDistance = routeDeviateDistance;
    }
}
