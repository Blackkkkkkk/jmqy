package com.dhxx.service.biz.transport.car;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dhxx.facade.entity.transport.car.CarTravel;
import com.dhxx.service.mapper.transport.car.CarTravelMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class CarTravelBiz {

    private static Logger log = LoggerFactory.getLogger(CarTravelBiz.class);
    
	@Autowired
    private CarTravelMapper carTravelMapper;

	public PageInfo<CarTravel> list(CarTravel carTravel) {
		log.debug("CarTravelBiz.list()");
		PageHelper.startPage(carTravel.getPageNum(), carTravel.getPageSize());
        List<CarTravel> list = carTravelMapper.list(carTravel);
        PageInfo<CarTravel> pageInfo = new PageInfo<CarTravel>(list);
		return pageInfo;
	}

	public List<CarTravel> find(CarTravel carTravel) {
		log.debug("CarTravelBiz.find()");
		return carTravelMapper.list(carTravel);
	}
	
	public CarTravel saveOrUpdate(CarTravel carTravel) {
		log.debug("CarTravelBiz.saveOrUpdate(),user="+JSONObject.toJSONString(carTravel));
		 carTravelMapper.saveOrUpdate(carTravel);
		return carTravel;
	}
	
	public Long updateById(CarTravel carTravel) {
		log.debug("CarTravelBiz.updateById(),user="+JSONObject.toJSONString(carTravel));
		return carTravelMapper.updateById(carTravel);
	}
	
}
