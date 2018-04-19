package com.dhxx.service.mapper.order;

import java.util.List;

import com.dhxx.facade.entity.order.Order;

public interface OrderMapper {
	
	List<Order> find(Order order);
	
	void update(Order order);
	
	void save(Order order);
	
	void delete(Order order);
	//订单提醒
    List<Order> remind(Order order);
    
    Integer statistics(Order order);
    
    //查找推荐车辆
    List<Order> findCarByScore(Order order);
    
    void checkMatch();

	void checkAccept();

	//取消订单锁定
	void checkCancel();
	void checkCancelBackOrder();

	void updateCreDitPay(Order order);

	Integer selectCount(Order order);
	//退款
	List<Order> refund(Order order);

    //查询需要发送短信的订单
    List<Order> checkOrder(Order order);

}
