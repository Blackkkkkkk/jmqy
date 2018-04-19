package com.dhxx.facade.entity.credit;

import com.dhxx.facade.entity.SysBaseEntity;

import java.util.Date;

public class CreditDetilBill extends SysBaseEntity {
    private Long id;
    private Long userId;//用户编码
    private String creditBefore;   //调整前
    private String credit;         //调整额
    private String creditAfter;    //调整后
    private String companyCode; // 公司编码
    private String bigOrderCode;//大订单,主订单编码

    private String payMode;//额度结算方式: 0账户余额，2微信，3支付宝，4银联 5申请退款 6是下单扣除额度 7是管理调整额度
    private String type;//1:下订单 ，2：结算 3：管理员调 4：退款


    //辅助字段

    private String userAccount;

    private String actionTypa;//1:平台方 2：包车方


    public String getBigOrderCode() {
        return bigOrderCode;
    }

    public void setBigOrderCode(String bigOrderCode) {
        this.bigOrderCode = bigOrderCode;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActionTypa() {
        return actionTypa;
    }

    public void setActionTypa(String actionTypa) {
        this.actionTypa = actionTypa;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditBefore() {
        return creditBefore;
    }

    public void setCreditBefore(String creditBefore) {
        this.creditBefore = creditBefore;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCreditAfter() {
        return creditAfter;
    }

    public void setCreditAfter(String creditAfter) {
        this.creditAfter = creditAfter;
    }
}
