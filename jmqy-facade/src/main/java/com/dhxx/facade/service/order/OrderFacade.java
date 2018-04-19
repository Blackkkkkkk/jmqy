package com.dhxx.facade.service.order;

import com.dhxx.facade.entity.order.Order;

/**
 * <p> 类说明 </p>
 * @author dingbin
 * Date: 2017年09月11日
 * @version 1.01
 * 订单管理interface
 */
public interface OrderFacade {

	
	Object list(Order order);
	
	Object find(Order order);
	
	Object save(Order order);

	Object remind(Order order);

	void update(Order order);
	
	void delete(Order order);
	
	Object statistics(Order order);
	
	Object findCarByScore(Order order);

	void updateCreDitPay(Order order);

	Integer selectCount(Order order);

	Object refund(Order order);
}
