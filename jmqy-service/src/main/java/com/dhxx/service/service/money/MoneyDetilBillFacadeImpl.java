package com.dhxx.service.service.money;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.entity.money.MoneyDetilBill;
import com.dhxx.facade.service.credit.CreditDetilBillFacade;
import com.dhxx.facade.service.money.MoneyDetilBillFacade;
import com.dhxx.service.biz.credit.CreditDetilBillBiz;
import com.dhxx.service.biz.money.MoneyDetilBillBiz;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
@Service(protocol = {"dubbo"})
public class MoneyDetilBillFacadeImpl implements MoneyDetilBillFacade {

    @Autowired
    private MoneyDetilBillBiz moneyDetilBillBiz;



    @Override
    public void update(MoneyDetilBill moneyDetilBill) {
        moneyDetilBillBiz.update(moneyDetilBill);
    }
    
    @Override
    public void save(MoneyDetilBill moneyDetilBill) {
        moneyDetilBillBiz.save(moneyDetilBill);
    }

    @Override
    public Object findOne(MoneyDetilBill moneyDetilBill) {
        return moneyDetilBillBiz.findOne(moneyDetilBill);
    }

    @Override
    public Object list(MoneyDetilBill moneyDetilBill) {
        return moneyDetilBillBiz.list(moneyDetilBill);
    }


}
