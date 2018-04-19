package com.dhxx.facade.entity.rule;

import com.dhxx.facade.entity.SysBaseEntity;

public class BillingRate extends SysBaseEntity {

    private String rate ; // 计费比率

    private String ruleType; //类型

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
