package com.dhxx.service.service.balance;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.balance.BalanceDetilBill;
import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.service.balance.BalanceDetilBillFacade;
import com.dhxx.facade.service.credit.CreditDetilBillFacade;
import com.dhxx.service.biz.balance.BalanceDetilBillBiz;
import com.dhxx.service.biz.credit.CreditDetilBillBiz;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
@Service(protocol = {"dubbo"})
public class BalanceDetilBillFacadeImpl implements BalanceDetilBillFacade {

    @Autowired
    private BalanceDetilBillBiz balanceDetilBillBiz;



    @Override
    public void update(BalanceDetilBill balanceDetilBill) {
        balanceDetilBillBiz.update(balanceDetilBill);
    }
    
    @Override
    public void save(BalanceDetilBill balanceDetilBill) {
        balanceDetilBillBiz.save(balanceDetilBill);
    }

    @Override
    public Object findOne(BalanceDetilBill balanceDetilBill) {
        return balanceDetilBillBiz.findOne(balanceDetilBill);
    }

    @Override
    public Object list(BalanceDetilBill balanceDetilBill) {
        return balanceDetilBillBiz.list(balanceDetilBill);
    }


}
