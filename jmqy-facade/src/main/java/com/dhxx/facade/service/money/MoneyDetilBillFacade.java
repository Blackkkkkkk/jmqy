package com.dhxx.facade.service.money;


import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.entity.money.MoneyDetilBill;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
public interface MoneyDetilBillFacade {



    void update(MoneyDetilBill moneyDetilBill);

    Object findOne(MoneyDetilBill moneyDetilBill);

	void save(MoneyDetilBill moneyDetilBill);

	Object list(MoneyDetilBill moneyDetilBill);

}
