package com.dhxx.facade.entity.finance;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * @author jhy
 * @date 2017/9/19
 * @description
 * 财务管理
 */
public class Finance extends SysBaseEntity {

    private Long userId;//企业主账号
    private String companyName;//企业名称
    private Double amount;//应收
    private Double payable;//应付
    private Double commission;//佣金
    private String param;//搜索参数

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPayable() {
        return payable;
    }

    public void setPayable(Double payable) {
        this.payable = payable;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

}
