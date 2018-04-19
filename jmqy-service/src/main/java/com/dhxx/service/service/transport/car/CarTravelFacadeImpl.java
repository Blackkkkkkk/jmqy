package com.dhxx.service.service.transport.car;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.transport.car.CarTravel;
import com.dhxx.facade.service.transport.car.CarTravelFacade;
import com.dhxx.service.biz.transport.car.CarTravelBiz;
/**
 * 
 * @author hanrs
 *
 */
@Service(protocol = {"dubbo"})
public class CarTravelFacadeImpl implements CarTravelFacade {
    
	private static Logger log = LoggerFactory.getLogger(CarTravelFacadeImpl.class);
	
    @Autowired
    private CarTravelBiz carTravelBiz;

	@Override
	public Object list(CarTravel carTravel) {
		log.debug("CarTravelFacadeImpl.list()");
		return carTravelBiz.list(carTravel);
	}

	@Override
	public Object find(CarTravel carTravel) {
		log.debug("CarTravelFacadeImpl.find()");
		return carTravelBiz.find(carTravel);
	}

	@Override
	public Object saveOrUpdate(CarTravel carTravel) {
		log.debug("CarTravelFacadeImpl.save()");
		return carTravelBiz.saveOrUpdate(carTravel);
	}
	
	@Override
	public Object updateById(CarTravel carTravel) {
		log.debug("CarTravelFacadeImpl.updateById()");
		return carTravelBiz.updateById(carTravel);
	}
}
