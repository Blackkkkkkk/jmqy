package com.dhxx.service.biz.rule;


import com.dhxx.facade.entity.rule.BillingRate;
import com.dhxx.service.mapper.rule.BillingRateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BillingRateBiz {

    @Autowired
    private BillingRateMapper billingRateMapper;

    public void  update(BillingRate billingRate){billingRateMapper.update(billingRate);}

    public  BillingRate find(BillingRate billingRate){ return  billingRateMapper.find(billingRate);}
}
