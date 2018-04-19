package com.dhxx.facade.entity.charter.evaluate;

import java.util.Date;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * <p> 类说明 </p>
 * @author dingbin
 * Date: 2017年09月15日
 * @version 1.01
 * 评价表(t_evaluate)
 */
public class Evaluate extends SysBaseEntity{

	private Long id;//主键
	private Long userId;//用户ID
	private String orderCode;//订单编码
	private String toDriverContent;//对司机评语
	private Long toDriverScore;//对司机评分
	private String toCarContent;//对车辆评语
	private Long toCarScore;//对车辆评分
	private String toCharterContent;//运输方对包车方的评语
	private Long transporterUserId;//运输方评价用户id
	private Date createTime;//创建日期
	private String remark;//备注
	 
	//查询属性
	private String beginTime;//开始评价时间
	private String endTime;//结束评价时间
	private String transporterCode;//运输方编码
	private Integer flag;//1:查看包车方评价内容;2:查看运输方评价内容。
	
	//附加属性
	private String charterName;//包车方名
	private String carCode;//车辆编码
	private String carNum;//车牌号
	private String driverName;//司机名称
	private String jobNum;//司机工号


	private String driverCode;//司机编码

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getJobNum() {
		return jobNum;
	}
	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getToDriverContent() {
		return toDriverContent;
	}
	public void setToDriverContent(String toDriverContent) {
		this.toDriverContent = toDriverContent;
	}
	public Long getToDriverScore() {
		return toDriverScore;
	}
	public void setToDriverScore(Long toDriverScore) {
		this.toDriverScore = toDriverScore;
	}
	public String getToCarContent() {
		return toCarContent;
	}
	public void setToCarContent(String toCarContent) {
		this.toCarContent = toCarContent;
	}
	public Long getToCarScore() {
		return toCarScore;
	}
	public void setToCarScore(Long toCarScore) {
		this.toCarScore = toCarScore;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTransporterCode() {
		return transporterCode;
	}
	public void setTransporterCode(String transporterCode) {
		this.transporterCode = transporterCode;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getCharterName() {
		return charterName;
	}
	public void setCharterName(String charterName) {
		this.charterName = charterName;
	}
	public String getToCharterContent() {
		return toCharterContent;
	}
	public void setToCharterContent(String toCharterContent) {
		this.toCharterContent = toCharterContent;
	}
	public Long getTransporterUserId() {
		return transporterUserId;
	}
	public void setTransporterUserId(Long transporterUserId) {
		this.transporterUserId = transporterUserId;
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
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
}
