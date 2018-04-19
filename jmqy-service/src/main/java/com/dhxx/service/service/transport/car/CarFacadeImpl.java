package com.dhxx.service.service.transport.car;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.facade.service.transport.car.CarFacade;
import com.dhxx.service.biz.transport.car.CarBiz;
/**
 * 
 * @author hanrs
 *
 */
@Service(protocol = {"dubbo"})
public class CarFacadeImpl implements CarFacade {
    
	private static Logger log = LoggerFactory.getLogger(CarFacadeImpl.class);
	
    @Autowired
    private CarBiz carBiz;

	@Override
	public Object list(Car car) {
		log.debug("CarFacadeImpl.list()");
		return carBiz.list(car);
	}

	@Override
	public Object find(Car car) {
		log.debug("CarFacadeImpl.find()");
		return carBiz.find(car);
	}

	@Override
	public Object saveOrUpdate(Car car) {
		log.debug("CarFacadeImpl.save()");
		return carBiz.saveOrUpdate(car);
	}
	
	@Override
	public Object updateById(Car car) {
		log.debug("CarFacadeImpl.updateById()");
		return carBiz.updateById(car);
	}
	@Override
	public  String findcartype(String s){
		return  carBiz.findcartype(s);
	}


	@Override
	public Object carList(Car car) {
		log.debug("CarFacadeImpl.carList()");
		return carBiz.carList(car);
	}
}
