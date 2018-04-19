package com.dhxx.service.mapper.transport.car;

import java.util.List;

import com.dhxx.facade.entity.transport.car.Car;

public interface CarMapper {

	List<Car> list(Car Car);
	
	Long saveOrUpdate(Car Car);

	Long updateById(Car car);

	String findcartype(String s);

    //车型的选项框
	List<Car> carList(Car Car);
}
