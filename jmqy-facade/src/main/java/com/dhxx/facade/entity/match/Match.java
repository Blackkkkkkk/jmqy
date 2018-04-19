package com.dhxx.facade.entity.match;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年10月21日
 * @version 1.01
 * 搜索匹配
 */
public class Match implements Serializable{
	
	private static final long serialVersionUID = 9064344945896830018L;
	
	private String carType;//车辆类型
	private String carAmount;//车辆数量
	
	private List<Cars> cars;//搜索出来的列表
	
	public Integer getSeatNumber() {
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(carType);   
		return Integer.parseInt(m.replaceAll("").trim())-1;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarAmount() {
		return carAmount;
	}

	public void setCarAmount(String carAmount) {
		this.carAmount = carAmount;
	}

	public List<Cars> getCars() {
		return cars;
	}

	public void setCars(List<Cars> cars) {
		this.cars = cars;
	}
	
}