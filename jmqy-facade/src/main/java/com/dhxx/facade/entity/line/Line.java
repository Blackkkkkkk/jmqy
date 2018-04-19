package com.dhxx.facade.entity.line;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月28日
 * @version 1.01
 * 包车方收藏的线路表(t_line)
 */
public class Line extends SysBaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;//主键
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
	private Integer times;//次数
	private Integer type;//1收藏，2搜索
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String remark;//备注
	
	//附加属性
	private Integer top;//前几条路线
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public String getStartArea() {
		return startArea;
	}


	public void setStartArea(String startArea) {
		this.startArea = startArea;
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


	public String getEndArea() {
		return endArea;
	}


	public void setEndArea(String endArea) {
		this.endArea = endArea;
	}


	public String getViaPoint() {
		return viaPoint;
	}


	public void setViaPoint(String viaPoint) {
		this.viaPoint = viaPoint;
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


	public Integer getTop() {
		return top;
	}


	public void setTop(Integer top) {
		this.top = top;
	}


	public Integer getTimes() {
		return times;
	}


	public void setTimes(Integer times) {
		this.times = times;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
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
	
}