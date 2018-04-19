package com.dhxx.service.mapper.rule;

import com.dhxx.facade.entity.rule.BillingRate;

public interface BillingRateMapper {

    void update(BillingRate billingRate);

    BillingRate find(BillingRate billingRate);
}
