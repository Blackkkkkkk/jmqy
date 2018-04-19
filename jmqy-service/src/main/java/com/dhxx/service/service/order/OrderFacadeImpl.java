package com.dhxx.service.service.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.service.biz.order.OrderBiz;
/**
 * 
 * @author dingbin
 *
 */
@Service(protocol = {"dubbo"})
public class OrderFacadeImpl implements OrderFacade {
    
	private static Logger log = LoggerFactory.getLogger(OrderFacadeImpl.class);
	
    @Autowired
    private OrderBiz orderBiz;

	@Override
	public Object list(Order order) {
		log.info("OrderFacadeImpl.list()");
		return orderBiz.list(order);
	}

	@Override
	public Object find(Order order) {
		log.info("OrderFacadeImpl.find()");
		return orderBiz.find(order);
	}

	@Override
	public Object save(Order order) {
		log.info("OrderFacadeImpl.save()");
		orderBiz.save(order);
		return order;
	}

    @Override
    public Object remind(Order order) {
        return orderBiz.remind(order);
    }

    @Override
	public void update(Order order) {
		log.info("OrderFacadeImpl.update()");
		orderBiz.update(order);
		
	}

	@Override
	public void delete(Order order) {
		log.info("OrderFacadeImpl.delete()");
		orderBiz.delete(order);
	}

	@Override
	public Object statistics(Order order) {
		return orderBiz.statistics(order);
	}
	
	@Override
	public Object findCarByScore(Order order) {
		log.info("OrderFacadeImpl.findCarByScore()");
		return orderBiz.findCarByScore(order);
	}


	@Override
	public void updateCreDitPay(Order order) {
		log.info("OrderFacadeImpl.updateCreDitPay()");
		 orderBiz.updateCreDitPay(order);
	}
	@Override
	public  Integer selectCount(Order order){
		log.info("OrderFacadeImpl.selectCount()");
		return orderBiz.selectCount(order);
	}

	@Override
	public Object refund(Order order) {
		return orderBiz.refund(order);
	}
}
