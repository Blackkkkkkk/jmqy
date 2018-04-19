package com.dhxx.facade.service.transport.car;

import com.dhxx.facade.entity.transport.car.Car;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月12日
 * @version 1.01
 * 车辆管理interface
 */
public interface CarFacade {
	
	Object list(Car car);
	
	Object find(Car car);
	
	Object saveOrUpdate(Car car);
	
	Object updateById(Car car);

	String findcartype(String s);

	Object carList(Car car);
}
