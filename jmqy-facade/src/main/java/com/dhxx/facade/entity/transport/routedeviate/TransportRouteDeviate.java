package com.dhxx.facade.entity.transport.routedeviate;

import com.dhxx.facade.entity.SysBaseEntity;

import java.util.Date;

public class TransportRouteDeviate extends SysBaseEntity {


    private Long id;
    private String deviation;        //是否偏差  1偏差  0没偏差
    private String handlingRemark;    //处理意见
    private Date creationDate;       //处理日期
    private String responsible;      //处理人
    private String orderCode;        //订单号
    private String companyCode;      //公司编码

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getDeviation() {
        return deviation;
    }

    public void setDeviation(String deviation) {
        this.deviation = deviation;
    }

    public String getHandlingRemark() {
        return handlingRemark;
    }

    public void setHandlingRemark(String handlingRemark) {
        this.handlingRemark = handlingRemark;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
}
