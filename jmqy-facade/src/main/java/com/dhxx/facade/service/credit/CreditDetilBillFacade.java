package com.dhxx.facade.service.credit;


import com.dhxx.facade.entity.credit.CreditDetilBill;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
public interface CreditDetilBillFacade {



    void update(CreditDetilBill creditDetilBill);

    Object findOne(CreditDetilBill creditDetilBill);

	void save(CreditDetilBill creditDetilBill);

	Object list(CreditDetilBill creditDetilBill);

}
