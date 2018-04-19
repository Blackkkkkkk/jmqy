package com.dhxx.service.service.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.service.match.MatchFacade;
import com.dhxx.service.biz.match.MatchBiz;
/**
 * 
 * @author hanrs
 *
 */
@Service(protocol = {"dubbo"})
public class MatchFacadeImpl implements MatchFacade {
    
	private static Logger log = LoggerFactory.getLogger(MatchFacadeImpl.class);
	
    @Autowired
    private MatchBiz matchBiz;
	
	@Override
	public Object selectByCarTypes(Order order) {
		log.info("MatchFacadeImpl.selectByCarTypes()");
		return matchBiz.selectByCarTypes(order);
	}
	
	@Override
	public Object selectLines(Order order) {
		log.info("MatchFacadeImpl.selectLines()");
		return matchBiz.selectLines(order);
	}
	
	@Override
	public Object selectCars(Order order) {
		log.info("MatchFacadeImpl.selectCars()");
		return matchBiz.selectCars(order);
	}
	
	@Override
	public Object selectDrivers(Order order) {
		log.info("MatchFacadeImpl.selectDrivers()");
		return matchBiz.selectDrivers(order);
	}
	
}
