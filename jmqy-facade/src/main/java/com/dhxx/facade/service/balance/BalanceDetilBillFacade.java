package com.dhxx.facade.service.balance;


import com.dhxx.facade.entity.balance.BalanceDetilBill;


/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
public interface BalanceDetilBillFacade {



    void update(BalanceDetilBill balanceDetilBill);

    Object findOne(BalanceDetilBill balanceDetilBill);

	void save(BalanceDetilBill balanceDetilBill);

	Object list(BalanceDetilBill balanceDetilBill);

}
