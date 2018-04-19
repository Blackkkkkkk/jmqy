package com.dhxx.facade.entity.money;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * @author xiewq
 * @date 2018/3/16
 * @description
 * 余额额度
 */
public class Money extends SysBaseEntity {

    private Long userId;//用户编码
    private Double totalMoney;//总金额
    private Double consumeMoney;//已消费金额
    private Double stockMoney;//剩余金额
    private Double money;//充值金额

    //附加属性
    private Long companyType;//企业类型 1个人包主 2包车企业 3运输企业
    private String companyName;//公司名称
    private String userAccount;//企业账号
    private String companyCode;//企业编码；
    private String param;//搜索参数

    private String TabNum;//Tab页 1：消费情况  2：结算情况 3：调整情况
    private String actionTypa;//1:平台方 2：包车方
    private String tradeMode;//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联 5申请退款


    public String getTradeMode() {
        return tradeMode;
    }

    public void setTradeMode(String tradeMode) {
        this.tradeMode = tradeMode;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Double getConsumeMoney() {
        return consumeMoney;
    }

    public void setConsumeMoney(Double consumeMoney) {
        this.consumeMoney = consumeMoney;
    }

    public Double getStockMoney() {
        return stockMoney;
    }

    public void setStockMoney(Double stockMoney) {
        this.stockMoney = stockMoney;
    }

    public Long getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Long companyType) {
        this.companyType = companyType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getTabNum() {
        return TabNum;
    }

    public void setTabNum(String tabNum) {
        TabNum = tabNum;
    }

    public String getActionTypa() {
        return actionTypa;
    }

    public void setActionTypa(String actionTypa) {
        this.actionTypa = actionTypa;
    }
}
