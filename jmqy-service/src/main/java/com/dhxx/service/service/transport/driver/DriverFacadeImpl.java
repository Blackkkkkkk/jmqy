package com.dhxx.service.service.transport.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.transport.driver.Driver;
import com.dhxx.facade.service.transport.driver.DriverFacade;
import com.dhxx.service.biz.transport.driver.DriverBiz;
/**
 * 
 * @author hanrs
 *
 */
@Service(protocol = {"dubbo"})
public class DriverFacadeImpl implements DriverFacade {
    
	private static Logger log = LoggerFactory.getLogger(DriverFacadeImpl.class);
	
    @Autowired
    private DriverBiz driverBiz;

	@Override
	public Object list(Driver driver) {
		log.debug("DriverFacadeImpl.list()");
		return driverBiz.list(driver);
	}

	@Override
	public Object find(Driver driver) {
		log.debug("DriverFacadeImpl.find()");
		return driverBiz.find(driver);
	}

	@Override
	public Object saveOrUpdate(Driver driver) {
		log.debug("DriverFacadeImpl.save()");
		return driverBiz.saveOrUpdate(driver);
	}
	
	@Override
	public Object updateById(Driver driver) {
		log.debug("DriverFacadeImpl.updateById()");
		return driverBiz.updateById(driver);
	}
}
