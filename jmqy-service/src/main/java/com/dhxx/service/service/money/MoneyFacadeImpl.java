package com.dhxx.service.service.money;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.money.Money;
import com.dhxx.facade.service.credit.CreditFacade;
import com.dhxx.facade.service.money.MoneyFacade;
import com.dhxx.service.biz.credit.CreditBiz;
import com.dhxx.service.biz.money.MoneyBiz;
import com.dhxx.service.service.user.UserFacadeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
@Service(protocol = {"dubbo"})
public class MoneyFacadeImpl implements MoneyFacade {
    private static Logger log = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Autowired
    private MoneyBiz moneyBiz;

    @Override
    public Object list(Money money) {
        return moneyBiz.list(money);
    }

    @Override
    public void update(Money money) {
        moneyBiz.update(money);
    }
    
    @Override
    public void save(Money money) {
        moneyBiz.save(money);
    }

    @Override
    public Object findOne(Money money) {

        log.debug("MoneyFacadeImpl.findOne()");
        return moneyBiz.findOne(money);
    }


    @Override
    public Object companyCreditList(Money money) {
        return moneyBiz.companyCreditList(money);
    }
}
