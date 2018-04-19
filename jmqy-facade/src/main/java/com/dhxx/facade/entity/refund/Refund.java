package com.dhxx.facade.entity.refund;

import com.dhxx.facade.entity.SysBaseEntity;


import java.math.BigDecimal;
import java.util.Date;


public class Refund extends SysBaseEntity {
    private Long id;
    private String companyCode;          //公司编码
    private String userAccount;          //退款账号
    private String orderCode;         //退款订单号
    private String refundCode;           //退款编号
    private Date refundApplyTime;       //退款申请时间
    private Date refundRealityTime;     //退款到账时间
    private String refundStatus;          //退款状态  0退款中，1退款完成
    private String updateStatus;         //数据处理状态  0未同步，1同步完成
    private Double refundMoney;        //申请退款金额
    private Double refundRealityMoney;   //实际退款金额
    private Double coefficient;          //退款系数;
    private Long payType;//支付方式    1:全额  2：余额  3记账

    private Long UserId;        //退款账号ID

    public Long getPayType() {
        return payType;
    }

    public void setPayType(Long payType) {
        this.payType = payType;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getRefundCode() {
        return refundCode;
    }

    public void setRefundCode(String refundCode) {
        this.refundCode = refundCode;
    }

    public Date getRefundApplyTime() {
        return refundApplyTime;
    }

    public void setRefundApplyTime(Date refundApplyTime) {
        this.refundApplyTime = refundApplyTime;
    }

    public Date getRefundRealityTime() {
        return refundRealityTime;
    }

    public void setRefundRealityTime(Date refundRealityTime) {
        this.refundRealityTime = refundRealityTime;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Double getRefundRealityMoney() {
        return refundRealityMoney;
    }

    public void setRefundRealityMoney(Double refundRealityMoney) {
        this.refundRealityMoney = refundRealityMoney;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }
}
