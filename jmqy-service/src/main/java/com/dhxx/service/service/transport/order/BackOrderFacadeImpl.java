package com.dhxx.service.service.transport.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.transport.order.BackOrder;
import com.dhxx.facade.service.transport.order.BackOrderFacade;
import com.dhxx.service.biz.transport.order.BackOrderBiz;
/**
 * 
 * @author hanrs
 *
 */
@Service(protocol = {"dubbo"})
public class BackOrderFacadeImpl implements BackOrderFacade {
    
	private static Logger log = LoggerFactory.getLogger(BackOrderFacadeImpl.class);
	
    @Autowired
    private BackOrderBiz carTravelBiz;

	@Override
	public Object list(BackOrder backOrder) {
		log.debug("BackOrderFacadeImpl.list()");
		return carTravelBiz.list(backOrder);
	}

	@Override
	public Object find(BackOrder backOrder) {
		log.debug("BackOrderFacadeImpl.find()");
		return carTravelBiz.find(backOrder);
	}

	@Override
	public Object saveOrUpdate(BackOrder backOrder) {
		log.debug("BackOrderFacadeImpl.save()");
		return carTravelBiz.saveOrUpdate(backOrder);
	}
	
	@Override
	public Object update(BackOrder backOrder) {
		log.debug("BackOrderFacadeImpl.updateById()");
		return carTravelBiz.update(backOrder);
	}
}
