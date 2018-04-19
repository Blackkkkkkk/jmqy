package com.dhxx.facade.service.transport.car;

import com.dhxx.facade.entity.transport.car.CarTravel;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月12日
 * @version 1.01
 * 车辆行程管理interface
 */
public interface CarTravelFacade {
	
	Object list(CarTravel carTravel);
	
	Object find(CarTravel carTravel);
	
	Object saveOrUpdate(CarTravel carTravel);
	
	Object updateById(CarTravel carTravel);
}
