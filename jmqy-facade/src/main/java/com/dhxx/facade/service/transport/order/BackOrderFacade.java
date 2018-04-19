package com.dhxx.facade.service.transport.order;

import com.dhxx.facade.entity.transport.order.BackOrder;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月12日
 * @version 1.01
 * 回程单管理interface
 */
public interface BackOrderFacade {
	
	Object list(BackOrder backOrder);
	
	Object find(BackOrder backOrder);
	
	Object saveOrUpdate(BackOrder backOrder);
	
	Object update(BackOrder backOrder);
}
