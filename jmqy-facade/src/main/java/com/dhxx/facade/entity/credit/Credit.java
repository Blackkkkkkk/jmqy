package com.dhxx.facade.entity.credit;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 * 信用额度
 */
public class Credit extends SysBaseEntity {

    private Long userId;//用户编码
    private Double totalCredit;//总信用额度
    private Double consumeCredit;//已消费额度
    private Double stockCredit;//剩余额度

    //附加属性
    private Long companyType;//企业类型 1个人包主 2包车企业 3运输企业
    private String companyName;//公司名称
    private String userAccount;//企业账号
    private String companyCode;//企业编码；
    private String param;//搜索参数

    private String TabNum;//Tab页 1：消费情况  2：结算情况 3：调整情况
    private String actionTypa;//1:平台方 2：包车方


    public String getActionTypa() {
        return actionTypa;
    }

    public void setActionTypa(String actionTypa) {
        this.actionTypa = actionTypa;
    }

    public String getTabNum() {
        return TabNum;
    }

    public void setTabNum(String tabNum) {
        TabNum = tabNum;
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

    public Double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(Double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public Double getConsumeCredit() {
        return consumeCredit;
    }

    public void setConsumeCredit(Double consumeCredit) {
        this.consumeCredit = consumeCredit;
    }

    public Double getStockCredit() {
        return stockCredit;
    }

    public void setStockCredit(Double stockCredit) {
        this.stockCredit = stockCredit;
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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
