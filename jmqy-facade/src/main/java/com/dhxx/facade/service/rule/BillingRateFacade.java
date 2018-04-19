package com.dhxx.facade.service.rule;

import com.dhxx.facade.entity.rule.BillingRate;

public interface BillingRateFacade {

    void update(BillingRate billingRate);

    BillingRate find(BillingRate billingRate);
}
