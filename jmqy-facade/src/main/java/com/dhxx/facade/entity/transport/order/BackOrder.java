package com.dhxx.facade.entity.transport.order;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月11日
 * @version 1.01
 * 返程单表(t_back_order)
 */
public class BackOrder extends SysBaseEntity{
	
	private Long id;//主键
	private String orderCode;//订单编码
	private String startPoint;//起始点
	private String startLng;//起始点经度
	private String startLat;//起始点纬度
	private String startArea;//起始点区域
	private String startCity;//起始点地级市
	private String endPoint;//目的地
	private String endLng;//目的地经度
	private String endLat;//目的地纬度
	private String endArea;//目的地区域
	private String endCity;//目的地地级市
	private String viaPoint;//途经点：地点@经度，纬度；地点@经度，纬度；
	private Date earliestDepartureTime;//最早出发时间
	private Date latestDepartureTime ;//最晚出发时间
	private String carCode;//车辆自编码
	private String carNum;//车牌号码
	private String driverCode;//司机编码
	private Integer seatNumber;//车座数
	private Double discountPrices;//优惠价,折扣价
	private String range;//允许偏离公里数（默认30%）
	private Long distance;//预估路程
	private Long duration;//预估时长
	private Integer status;//状态：-1取消，0待匹配，1已匹配，2完成 ，3 锁定订单
	private Integer effeTime;//可接空程单有效接单时间
	private Date effeSeTime;//可接空程单有效被搜索时间
	private Integer dwellTime;//停留时间
	private String charterOrderCode;//包车订单编码
	private Date charterBoardTime;//去程上车时间
	private String remark;//备注
	
	private String carType;//车辆类型
	private Integer type;//1：空程，2单程
	private Date reserveUpTime;//预留上车时间
	private Date reserveOffTime;//预留下车时间
	private Date cancelTime;//订单取消锁定时间
	
	//查询属性
	private String orderCodeSe;//订单编码搜索
	
	//附属属性
	private Date boardingTime;//订单表的上车时间
	private Integer top;//前几条
	private String beginTime;//开始出发时间
	private String endTime;//结束出发时间


	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}
	public String getStartLng() {
		return startLng;
	}
	public void setStartLng(String startLng) {
		this.startLng = startLng;
	}
	public String getStartLat() {
		return startLat;
	}
	public void setStartLat(String startLat) {
		this.startLat = startLat;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getEndLng() {
		return endLng;
	}
	public void setEndLng(String endLng) {
		this.endLng = endLng;
	}
	public String getEndLat() {
		return endLat;
	}
	public void setEndLat(String endLat) {
		this.endLat = endLat;
	}
	public String getViaPoint() {
		return viaPoint;
	}
	public void setViaPoint(String viaPoint) {
		this.viaPoint = viaPoint;
	}
	public Date getEarliestDepartureTime() {
		return earliestDepartureTime;
	}
	public Long getDistance() {
		return distance;
	}
	public void setDistance(Long distance) {
		this.distance = distance;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public void setEarliestDepartureTime(Date earliestDepartureTime) {
		this.earliestDepartureTime = earliestDepartureTime;
	}
	public Date getLatestDepartureTime() {
		return latestDepartureTime;
	}
	public void setLatestDepartureTime(Date latestDepartureTime) {
		this.latestDepartureTime = latestDepartureTime;
	}
	public String getStartArea() {
		return startArea;
	}
	public void setStartArea(String startArea) {
		this.startArea = startArea;
	}
	public String getEndArea() {
		return endArea;
	}
	public void setEndArea(String endArea) {
		this.endArea = endArea;
	}
	public Integer getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}
	public Double getDiscountPrices() {
		return discountPrices;
	}
	public void setDiscountPrices(Double discountPrices) {
		this.discountPrices = discountPrices;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderCodeSe() {
		return orderCodeSe;
	}
	public void setOrderCodeSe(String orderCodeSe) {
		this.orderCodeSe = orderCodeSe;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
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
	public String getViaPointStr(){
		if(!StringUtils.isEmpty(viaPoint)){
			String[] arr = viaPoint.split(";");
			String str = "";
			for(String s: arr){
				str +=s.split("@")[0]+",";
			}
			return str.substring(0,str.length()-1);
			
		}
		return null;
	}
	
	public Date getBoardingTime() {
		return boardingTime;
	}
	public void setBoardingTime(Date boardingTime) {
		this.boardingTime = boardingTime;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getEffeTime() {
		return effeTime;
	}
	public void setEffeTime(Integer effeTime) {
		this.effeTime = effeTime;
	}
	public Integer getDwellTime() {
		return dwellTime;
	}
	public void setDwellTime(Integer dwellTime) {
		this.dwellTime = dwellTime;
	}
	public String getCharterOrderCode() {
		return charterOrderCode;
	}
	public void setCharterOrderCode(String charterOrderCode) {
		this.charterOrderCode = charterOrderCode;
	}
	public Date getEffeSeTime() {
		return effeSeTime;
	}
	public void setEffeSeTime(Date effeSeTime) {
		this.effeSeTime = effeSeTime;
	}
	public Date getCharterBoardTime() {
		return charterBoardTime;
	}
	public void setCharterBoardTime(Date charterBoardTime) {
		this.charterBoardTime = charterBoardTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getReserveUpTime() {
		return reserveUpTime;
	}
	public void setReserveUpTime(Date reserveUpTime) {
		this.reserveUpTime = reserveUpTime;
	}
	public Date getReserveOffTime() {
		return reserveOffTime;
	}
	public void setReserveOffTime(Date reserveOffTime) {
		this.reserveOffTime = reserveOffTime;
	}
	
}