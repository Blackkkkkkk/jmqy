package com.dhxx.service.service.credit;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.service.credit.CreditDetilBillFacade;
import com.dhxx.facade.service.credit.CreditFacade;
import com.dhxx.service.biz.credit.CreditBiz;
import com.dhxx.service.biz.credit.CreditDetilBillBiz;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
@Service(protocol = {"dubbo"})
public class CreditDetilBillFacadeImpl implements CreditDetilBillFacade {

    @Autowired
    private CreditDetilBillBiz creditDetilBillBiz;



    @Override
    public void update(CreditDetilBill creditDetilBill) {
        creditDetilBillBiz.update(creditDetilBill);
    }
    
    @Override
    public void save(CreditDetilBill creditDetilBill) {
        creditDetilBillBiz.save(creditDetilBill);
    }

    @Override
    public Object findOne(CreditDetilBill creditDetilBill) {
        return creditDetilBillBiz.findOne(creditDetilBill);
    }

    @Override
    public Object list(CreditDetilBill creditDetilBill) {
        return creditDetilBillBiz.list(creditDetilBill);
    }


}
