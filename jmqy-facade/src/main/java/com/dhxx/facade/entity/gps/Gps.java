package com.dhxx.facade.entity.gps;

import java.util.Date;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2018年01月22日
 * @version 1.01
 * gps数据月表(t_gps_201801)
 */

public class Gps extends SysBaseEntity{
	
	private Long id; //id
	private String carNum;//车牌号码
	private Date gpsTime; //GPS时间
	private String lg; //经度
	private String lt; //纬度
	private Double speed; //速度
	private Double being; // 方向
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public Date getGpsTime() {
		return gpsTime;
	}
	public void setGpsTime(Date gpsTime) {
		this.gpsTime = gpsTime;
	}
	public String getLg() {
		return lg;
	}
	public void setLg(String lg) {
		this.lg = lg;
	}
	public String getLt() {
		return lt;
	}
	public void setLt(String lt) {
		this.lt = lt;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public Double getBeing() {
		return being;
	}
	public void setBeing(Double being) {
		this.being = being;
	}
	
}
