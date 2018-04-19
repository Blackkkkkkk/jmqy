package com.dhxx.facade.service.money;


import com.dhxx.facade.entity.money.Money;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
public interface MoneyFacade {

    Object list(Money money);

    void update(Money money);

    Object findOne(Money money);

	void save(Money money);

    Object companyCreditList(Money money);

}
