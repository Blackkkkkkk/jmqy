package com.dhxx.service.mapper.transport.driver;

import java.util.List;

import com.dhxx.facade.entity.transport.driver.Driver;

public interface DriverMapper {

	List<Driver> list(Driver Driver);
	
	Long saveOrUpdate(Driver Driver);

	Long updateById(Driver driver);
	
}
