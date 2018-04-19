package com.dhxx.facade.service.transport.driver;

import com.dhxx.facade.entity.transport.driver.Driver;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月12日
 * @version 1.01
 * 司机管理interface
 */
public interface DriverFacade {
	
	Object list(Driver driver);
	
	Object find(Driver driver);
	
	Object saveOrUpdate(Driver driver);
	
	Object updateById(Driver driver);
}
