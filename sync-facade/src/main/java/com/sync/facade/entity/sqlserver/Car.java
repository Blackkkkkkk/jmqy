package com.sync.facade.entity.sqlserver;

import java.io.Serializable;
import java.util.Date;

public class Car  implements Serializable {

    private int id;
    private String orderCode;     //订单号
    private String longLat;       //实时经纬度合集
    private String longLatPlan;   //规划路线的经纬度合集
    private String deviation;     //是否有偏差 0：没偏差 1： 有偏差

    //辅助查询字段
    private String carNum;      //查询车牌号
    private String boaringTime;   // 上车时间
    private String debusTime;//下车时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getLongLat() {
        return longLat;
    }

    public void setLongLat(String longLat) {
        this.longLat = longLat;
    }

    public String getLongLatPlan() {
        return longLatPlan;
    }

    public void setLongLatPlan(String longLatPlan) {
        this.longLatPlan = longLatPlan;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getBoaringTime() {
        return boaringTime;
    }

    public void setBoaringTime(String boaringTime) {
        this.boaringTime = boaringTime;
    }

    public String getDebusTime() {
        return debusTime;
    }

    public void setDebusTime(String debusTime) {
        this.debusTime = debusTime;
    }
}
