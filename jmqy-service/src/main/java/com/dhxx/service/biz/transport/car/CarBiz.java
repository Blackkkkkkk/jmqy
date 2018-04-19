package com.dhxx.service.biz.transport.car;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.service.mapper.transport.car.CarMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class CarBiz {

    private static Logger log = LoggerFactory.getLogger(CarBiz.class);
    
	@Autowired
    private CarMapper carMapper;

	public PageInfo<Car> list(Car car) {
		log.debug("CarBiz.list()");
		PageHelper.startPage(car.getPageNum(), car.getPageSize());
        List<Car> list = carMapper.list(car);
        PageInfo<Car> pageInfo = new PageInfo<Car>(list);
		return pageInfo;
	}

	public List<Car> find(Car car) {
		log.debug("CarBiz.find()");
		return carMapper.list(car);
	}
	
	public Long saveOrUpdate(Car car) {
		log.debug("CarBiz.saveOrUpdate(),user="+JSONObject.toJSONString(car));
		return carMapper.saveOrUpdate(car);
	}
	
	public Long updateById(Car car) {
		log.debug("CarBiz.updateById(),user="+JSONObject.toJSONString(car));
		return carMapper.updateById(car);
	}

	public  String findcartype(String s){

		log.debug("CarBiz.findcartype(),s="+s);
		return carMapper.findcartype(s);
	}

	public List<Car> carList(Car car) {
		log.debug("CarBiz.carList()");
		return carMapper.carList(car);
	}

}
