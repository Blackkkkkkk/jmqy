package com.dhxx.facade.entity.order;

import com.dhxx.facade.entity.SysBaseEntity;


import java.sql.Time;



public class OrderTimeSet extends SysBaseEntity {


    private Integer beforesettime; // 单前时间
    private Integer middlesettime; //单中时间设置
    private Integer aftersettime;   //单后时间设置
    private String ordernotsetStarttime; //默认不接单起始时间
    private String ordernotsetEndtime; //默认不接单结束时间

    public Integer getBeforesettime() {
        return beforesettime;
    }

    public void setBeforesettime(Integer beforesettime) {
        this.beforesettime = beforesettime;
    }

    public Integer getMiddlesettime() {
        return middlesettime;
    }

    public void setMiddlesettime(Integer middlesettime) {
        this.middlesettime = middlesettime;
    }

    public Integer getAftersettime() {
        return aftersettime;
    }

    public void setAftersettime(Integer aftersettime) {
        this.aftersettime = aftersettime;
    }

    public String getOrdernotsetStarttime() {
        return ordernotsetStarttime;
    }

    public void setOrdernotsetStarttime(String ordernotsetStarttime) {
        this.ordernotsetStarttime = ordernotsetStarttime;
    }

    public String getOrdernotsetEndtime() {
        return ordernotsetEndtime;
    }

    public void setOrdernotsetEndtime(String ordernotsetEndtime) {
        this.ordernotsetEndtime = ordernotsetEndtime;
    }

}