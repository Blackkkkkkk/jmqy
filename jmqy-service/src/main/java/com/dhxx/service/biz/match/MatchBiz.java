package com.dhxx.service.biz.match;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhxx.facade.entity.match.Cars;
import com.dhxx.facade.entity.match.Match;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.facade.entity.transport.driver.Driver;
import com.dhxx.service.mapper.match.MatchMapper;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年10月25日
 * @version 1.01
 *
 */
@Service
@Transactional
public class MatchBiz {
	
	private static Logger log = LoggerFactory.getLogger(MatchBiz.class);
	
    @Autowired
    private MatchMapper matchMapper;
    
    public List<Match> selectByCarTypes(Order order){
    	return matchMapper.selectByCarTypes(order);
    }

	public List<Cars> selectLines(Order order) {
		return matchMapper.selectLines(order);
	}
	
	public List<Car> selectCars(Order order) {
		return matchMapper.selectCars(order);
	}
	
	public List<Driver> selectDrivers(Order order) {
		return matchMapper.selectDrivers(order);
	}
	
}
