package com.dhxx.facade.entity.balance;

import com.dhxx.facade.entity.SysBaseEntity;

import javax.xml.crypto.Data;
import java.io.Serializable;

public class BalanceDetilBill extends SysBaseEntity {




    private Long id;
    private Long userId;


    private String balanceBefore; //结算调整前
    private String balance;//调整额
    private String balanceAfter;//调整后
    private String companyCode;


    //辅助查询字段
    private String userAccount;



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



    public String getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(String balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(String balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
}
