package com.dhxx.facade.service.credit;

import com.dhxx.facade.entity.credit.Credit;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
public interface CreditFacade {

    Object list(Credit credit);

    void update(Credit credit);

    Object findOne(Credit credit);

	void save(Credit credit);

    Object companyCreditList(Credit credit);

}
