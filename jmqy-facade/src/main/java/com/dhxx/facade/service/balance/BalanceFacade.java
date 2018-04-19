package com.dhxx.facade.service.balance;

import com.dhxx.facade.entity.balance.Balance;

public interface BalanceFacade {

	Object find(Balance balance);

	Object saveOrUpdate(Balance balance);

}
