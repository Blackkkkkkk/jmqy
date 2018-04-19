package com.dhxx.service.biz.transport.driver;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.dhxx.common.constant.Constant;
import com.dhxx.common.util.DateEditorUtils;
import com.dhxx.facade.entity.transport.driver.Driver;
import com.dhxx.service.mapper.transport.driver.DriverMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class DriverBiz {

    private static Logger log = LoggerFactory.getLogger(DriverBiz.class);
    
	@Autowired
    private DriverMapper driverMapper;

	public PageInfo<Driver> list(Driver driver) {
		log.debug("DriverBiz.list()");
		PageHelper.startPage(driver.getPageNum(), driver.getPageSize());
        List<Driver> list = driverMapper.list(driver);
        PageInfo<Driver> pageInfo = new PageInfo<Driver>(list);
		return pageInfo;
	}

	public List<Driver> find(Driver driver) {
		log.debug("DriverBiz.find()");
		return driverMapper.list(driver);
	}
	
	public Driver saveOrUpdate(Driver driver) {
		log.debug("DriverBiz.saveOrUpdate(),user="+JSONObject.toJSONString(driver));
		if(StringUtils.isEmpty(driver.getCode())){
			driver.setCode(Constant.DR_CODE+DateEditorUtils.dateToStr(DateEditorUtils.format_yyyyMMdd, new Date()));
		}
		driverMapper.saveOrUpdate(driver);
		return driver;
	}
	
	public Long updateById(Driver driver) {
		log.debug("DriverBiz.updateById(),user="+JSONObject.toJSONString(driver));
		return driverMapper.updateById(driver);
	}
	
}
