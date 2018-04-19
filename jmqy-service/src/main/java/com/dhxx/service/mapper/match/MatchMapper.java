package com.dhxx.service.mapper.match;

import java.util.List;

import com.dhxx.facade.entity.match.Cars;
import com.dhxx.facade.entity.match.Match;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.facade.entity.transport.driver.Driver;

public interface MatchMapper {
	
    //查找推荐车辆
    List<Match> selectByCarTypes(Order order);

    //查找推荐路线
	List<Cars> selectLines(Order order);
	
	//查找空闲车辆
	List<Car> selectCars(Order order);
	
	//查找空闲司机
	List<Driver> selectDrivers(Order order);

}
