package com.dhxx.service.mapper.transport.order;

import java.util.List;

import com.dhxx.facade.entity.transport.order.BackOrder;

public interface BackOrderMapper {

	List<BackOrder> list(BackOrder backOrder);
	
	Long saveOrUpdate(BackOrder backOrder);

	Long update(BackOrder backOrder);
	
	void checkMatch();
	
}
