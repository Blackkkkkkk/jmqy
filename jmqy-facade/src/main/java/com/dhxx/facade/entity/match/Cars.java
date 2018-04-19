package com.dhxx.facade.entity.match;

import java.io.Serializable;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年10月21日
 * @version 1.01
 * 车辆和线路
 */
public class Cars implements Serializable{
	
	private static final long serialVersionUID = 9064344945896830018L;
	
	private String carCode;//车辆自编码
	private String carType;//车辆类型
	private String carNum;//车牌号码
	private String driverCode;//司机编码
	private Integer seatNumber;//车座数
	private String startPoint;//起始点
	private String startArea;//起始点区域
	private String startCity;//起始点地级市
	private String endPoint;//目的地
	private String endArea;//目的地区域
	private String endCity;//目的地地级市
	private Double prices;//价格
	private Integer type;//1车辆，2线路
	private String orderCode;//空程订单编码
	private String scores;//车辆的平均分
	private String scoreSum;//车辆评价条数
	private String carPhoto;//车辆照片
	private String companyName;//公司名称
	private String companyCode;//公司编码

	//计价后的金额以十位为单位，进至百位，如1764进为1800，1746进为1700。
	public Double getPrices() {
		if (prices != null) {
			if (prices > 100) {
				return Math.round(prices/100.0)*100.0;
			} else {
				return prices;
			}
		}
		return null;
	}


	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCarPhoto() {
		return carPhoto;
	}

	public void setCarPhoto(String carPhoto) {
		this.carPhoto = carPhoto;
	}

	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public Integer getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}
	public String getStartArea() {
		return startArea;
	}
	public void setStartArea(String startArea) {
		this.startArea = startArea;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getEndArea() {
		return endArea;
	}
	public void setEndArea(String endArea) {
		this.endArea = endArea;
	}
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	/*public Double getPrices() {
		return prices;
	}*/
	public void setPrices(Double prices) {
		this.prices = prices;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getScores() {
		return scores;
	}
	public void setScores(String scores) {
		this.scores = scores;
	}
	public String getScoreSum() {
		return scoreSum;
	}
	public void setScoreSum(String scoreSum) {
		this.scoreSum = scoreSum;
	}
	
}