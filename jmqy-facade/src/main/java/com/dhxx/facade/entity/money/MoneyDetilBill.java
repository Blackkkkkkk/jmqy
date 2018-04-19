package com.dhxx.facade.entity.money;

import com.dhxx.facade.entity.SysBaseEntity;

public class MoneyDetilBill extends SysBaseEntity {
    private Long id;
    private Long userId;//用户编码
    private String moneyBefore;   //调整前
    private String money;         //调整额
    private String moneyAfter;    //调整后
    private String companyCode;
    private String balance;    //余额
    private String payMode;//额度结算方式: 0账户余额，2微信，3支付宝，4银联 5申请退款 6是下单扣除额度 7 管理员调整
    private String type;//1:下订单 ，2：结算 3：管理员调 4 退款

    private String bigOrderCode;//大订单,主订单编码


    //辅助字段

    private String userAccount;

    private String actionTypa;//1:平台方 2：包车方


    public String getBigOrderCode() {
        return bigOrderCode;
    }

    public void setBigOrderCode(String bigOrderCode) {
        this.bigOrderCode = bigOrderCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMoneyBefore() {
        return moneyBefore;
    }

    public void setMoneyBefore(String moneyBefore) {
        this.moneyBefore = moneyBefore;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoneyAfter() {
        return moneyAfter;
    }

    public void setMoneyAfter(String moneyAfter) {
        this.moneyAfter = moneyAfter;
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

    public String getActionTypa() {
        return actionTypa;
    }

    public void setActionTypa(String actionTypa) {
        this.actionTypa = actionTypa;
    }
}
