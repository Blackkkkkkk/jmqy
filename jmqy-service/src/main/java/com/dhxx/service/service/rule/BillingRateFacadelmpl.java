package com.dhxx.service.service.rule;


import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.rule.BillingRate;
import com.dhxx.facade.service.rule.BillingRateFacade;
import com.dhxx.service.biz.rule.BillingRateBiz;
import org.springframework.beans.factory.annotation.Autowired;

@Service(protocol = {"dubbo"})
public class BillingRateFacadelmpl  implements BillingRateFacade{

    @Autowired
    private BillingRateBiz billingRateBiz;

    public void update(BillingRate billingRate){ billingRateBiz.update(billingRate);}

    public BillingRate find(BillingRate billingRate){ return  billingRateBiz.find(billingRate);}
}
