package com.dhxx.facade.entity.transport.car;

import java.util.Date;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月15日
 * @version 1.01
 * 车辆管理(t_car)
 */

public class Car extends SysBaseEntity{

    private Long id;//表id		
    private String carCode;//车辆编码
    private String vin;//车辆识别代码（vin），vin是英文vehicle identification number（车辆识别码）的缩写。
    private String carNum;//车牌号码
    private String driverCode;//司机编码;
    private String carType;//车辆类型：小车4座，小车6座，中巴19座...		
    private Integer seatNumber;//车座数		
    private String carPhoto;//车辆照片	
    private String carPhoto1;//车辆照片
    private String carPhoto2;//车辆照片	
    private String carPhoto3;//车辆照片
	private String carPhoto4;//车辆内景照片
	private String carPhoto5;//车辆内景照片
    private String drivingLicensePros;//行驶证正面
    private String drivingLicenseCons;//行驶证反面
    private Date drivingLicenseEffect;//行驶证生效日期
    private Date  drivingLicenseInvalid;//行驶证截止日期
    private String operationLicense;//运营许可证
    private Date operationLicenseEffect;//运营许可证生效日期
    private Date operationLicenseInvalid;//运营许可证截止日期
    private String xianlupai;//线路牌	
    private String xianlupaiPros;//线路牌正面
    private String xianlupaiCons;//线路牌反面
    private String policy;//保险单
    private Date policyEffect;//保险单生效日期
    private Date policyInvalid;//保险单截止日期
    private String site;//位置
    private String lng;//经度
    private String lat;//纬度
    private String area;//区域
    private String city;//市级
	private String companyCode;//所属企业编码	
    private Integer charterType;//包车类型		
    private String additional;//附加选项: 餐费,住宿费,高速路费,保险,水		
    private Date madeDate;//出厂日期		
    private Integer status;//状态：0正常，1停用
    private String remark;//备注	
    
    //附加属性
    private String beginTime;//开始出厂日期
	private String endTime;//结束出厂日期
    private String driverName;//司机名称
    private Integer counts;//近一年接单数
	private String searchType;//模糊搜索字段，0：不模糊   1：模糊查询
    
    private String message;//提示信息

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getCarPhoto4() {
		return carPhoto4;
	}

	public void setCarPhoto4(String carPhoto4) {
		this.carPhoto4 = carPhoto4;
	}

	public String getCarPhoto5() {
		return carPhoto5;
	}

	public void setCarPhoto5(String carPhoto5) {
		this.carPhoto5 = carPhoto5;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
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
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Integer getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getCarPhoto() {
		return carPhoto;
	}
	public void setCarPhoto(String carPhoto) {
		this.carPhoto = carPhoto;
	}
	public String getCarPhoto1() {
		return carPhoto1;
	}
	public void setCarPhoto1(String carPhoto1) {
		this.carPhoto1 = carPhoto1;
	}
	public String getCarPhoto2() {
		return carPhoto2;
	}
	public void setCarPhoto2(String carPhoto2) {
		this.carPhoto2 = carPhoto2;
	}
	public String getCarPhoto3() {
		return carPhoto3;
	}
	public void setCarPhoto3(String carPhoto3) {
		this.carPhoto3 = carPhoto3;
	}
	public String getDrivingLicensePros() {
		return drivingLicensePros;
	}
	public void setDrivingLicensePros(String drivingLicensePros) {
		this.drivingLicensePros = drivingLicensePros;
	}
	public String getDrivingLicenseCons() {
		return drivingLicenseCons;
	}
	public void setDrivingLicenseCons(String drivingLicenseCons) {
		this.drivingLicenseCons = drivingLicenseCons;
	}
	public Date getDrivingLicenseEffect() {
		return drivingLicenseEffect;
	}
	public void setDrivingLicenseEffect(Date drivingLicenseEffect) {
		this.drivingLicenseEffect = drivingLicenseEffect;
	}
	public Date getDrivingLicenseInvalid() {
		return drivingLicenseInvalid;
	}
	public void setDrivingLicenseInvalid(Date drivingLicenseInvalid) {
		this.drivingLicenseInvalid = drivingLicenseInvalid;
	}
	public String getOperationLicense() {
		return operationLicense;
	}
	public void setOperationLicense(String operationLicense) {
		this.operationLicense = operationLicense;
	}
	public Date getOperationLicenseEffect() {
		return operationLicenseEffect;
	}
	public void setOperationLicenseEffect(Date operationLicenseEffect) {
		this.operationLicenseEffect = operationLicenseEffect;
	}
	public Date getOperationLicenseInvalid() {
		return operationLicenseInvalid;
	}
	public void setOperationLicenseInvalid(Date operationLicenseInvalid) {
		this.operationLicenseInvalid = operationLicenseInvalid;
	}
	public String getXianlupai() {
		return xianlupai;
	}
	public void setXianlupai(String xianlupai) {
		this.xianlupai = xianlupai;
	}
	public String getXianlupaiPros() {
		return xianlupaiPros;
	}
	public void setXianlupaiPros(String xianlupaiPros) {
		this.xianlupaiPros = xianlupaiPros;
	}
	public String getXianlupaiCons() {
		return xianlupaiCons;
	}
	public void setXianlupaiCons(String xianlupaiCons) {
		this.xianlupaiCons = xianlupaiCons;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public Date getPolicyEffect() {
		return policyEffect;
	}
	public void setPolicyEffect(Date policyEffect) {
		this.policyEffect = policyEffect;
	}
	public Date getPolicyInvalid() {
		return policyInvalid;
	}
	public void setPolicyInvalid(Date policyInvalid) {
		this.policyInvalid = policyInvalid;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Integer getCharterType() {
		return charterType;
	}
	public void setCharterType(Integer charterType) {
		this.charterType = charterType;
	}
	public String getAdditional() {
		return additional;
	}
	public void setAdditional(String additional) {
		this.additional = additional;
	}
	public Date getMadeDate() {
		return madeDate;
	}
	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
}
