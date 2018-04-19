package com.dhxx.facade.service.bill;

import com.dhxx.facade.entity.bill.Bill;

public interface BillFacade {

	Object list(Bill bill);

	Object find(Bill bill);

	Object save(Bill bill);

}
