package com.dhxx.facade.entity.transport.car;

import java.io.Serializable;
import java.util.Date;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月16日
 * @version 1.01
 * 车辆行程管理(t_car_travel)
 */

public class CarTravel extends SysBaseEntity implements Serializable{

    /** 属性说明 */
    private static final long serialVersionUID = 1L;
    
    private Long id;//表id		
    private String carCode;//车辆编码
    private String carNum;//车牌号码
    private String driverJobNum;//司机工号
    private Date startTime;//开始时间
    private Date endTime;//结束时间
    private Integer carState;//车辆状况： 1保养，2维修，3事故，4 自运营，
    private String remark;//备注	
    
    private String driverName;//司机姓名
    private String message;//提示信息
    private Long reId;//表id	
    
    //查询属性
  	private String beginTime;//开始时间
  	private String endTimes;//结束时间
    private String searchType;//模糊查询字段  0：模糊 1：精确

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDriverJobNum() {
		return driverJobNum;
	}

	public void setDriverJobNum(String driverJobNum) {
		this.driverJobNum = driverJobNum;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getCarState() {
		return carState;
	}

	public void setCarState(Integer carState) {
		this.carState = carState;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getReId() {
		return reId;
	}

	public void setReId(Long reId) {
		this.reId = reId;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(String endTimes) {
		this.endTimes = endTimes;
	}
}
