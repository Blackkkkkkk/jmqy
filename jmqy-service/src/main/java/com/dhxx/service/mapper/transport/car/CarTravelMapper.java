package com.dhxx.service.mapper.transport.car;

import java.util.List;

import com.dhxx.facade.entity.transport.car.CarTravel;

public interface CarTravelMapper {

	List<CarTravel> list(CarTravel CarTravel);
	
	Long saveOrUpdate(CarTravel CarTravel);

	Long updateById(CarTravel carTravel);
	
}
