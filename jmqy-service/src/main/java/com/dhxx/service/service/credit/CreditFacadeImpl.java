package com.dhxx.service.service.credit;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.service.credit.CreditFacade;
import com.dhxx.service.biz.credit.CreditBiz;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
@Service(protocol = {"dubbo"})
public class CreditFacadeImpl implements CreditFacade {

    @Autowired
    private CreditBiz creditBiz;

    @Override
    public Object list(Credit credit) {
        return creditBiz.list(credit);
    }

    @Override
    public void update(Credit credit) {
        creditBiz.update(credit);
    }
    
    @Override
    public void save(Credit credit) {
        creditBiz.save(credit);
    }

    @Override
    public Object findOne(Credit credit) {
        return creditBiz.findOne(credit);
    }


    @Override
    public Object companyCreditList(Credit credit) {
        return creditBiz.companyCreditList(credit);
    }
}
