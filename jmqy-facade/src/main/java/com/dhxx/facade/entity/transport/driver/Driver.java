package com.dhxx.facade.entity.transport.driver;

import java.io.Serializable;
import java.util.Date;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月12日
 * @version 1.01
 * 司机管理(t_driver)
 */

public class Driver extends SysBaseEntity implements Serializable{

    /** 属性说明 */
    private static final long serialVersionUID = 1L;
    
    private Long id;//表id		
    private String code;//司机编码;
    private String jobNum;//工号;
    private String headshot;// 头像;
    private String name;//姓名;
    private String sex;//性别:男，女;
    private Date birthDate;//出生日期
    private String phone;//手机号码;
    private String idCard;//身份证;
    private String idCardPros;//身份证正面;
    private String idCardCons;//身份证反面;
    private String drivingLicense;//驾驶证;
    private Date drivingLicenseEffect;//驾驶证生效日期;
    private Date drivingLicenseInvalid;//驾驶证截止日期;
    private String workLicense;//上岗证;
    private Date workLicenseEffect;//上岗证生效日期;
    private Date workLicenseInvalid;//上岗证截止日期;
    private String status;//状态：0在职，1休假，2离职;
    private String companyCode;//所属企业编码	
    private String remark;//备注;
    
    private Long reId;//表id	
    private String jobNumPre;//工号前缀;
    
    private String message;//提示信息

	//查询字段
	private  String companyName;// 公司名称
	private  String searchType;//是否模糊查询 0：不模糊 1：模糊

	private String changeType;//1:新增，2修改

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getHeadshot() {
		return headshot;
	}

	public void setHeadshot(String headshot) {
		this.headshot = headshot;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIdCardPros() {
		return idCardPros;
	}

	public void setIdCardPros(String idCardPros) {
		this.idCardPros = idCardPros;
	}

	public String getIdCardCons() {
		return idCardCons;
	}

	public void setIdCardCons(String idCardCons) {
		this.idCardCons = idCardCons;
	}

	public String getDrivingLicense() {
		return drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
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

	public String getWorkLicense() {
		return workLicense;
	}

	public void setWorkLicense(String workLicense) {
		this.workLicense = workLicense;
	}

	public Date getWorkLicenseEffect() {
		return workLicenseEffect;
	}

	public void setWorkLicenseEffect(Date workLicenseEffect) {
		this.workLicenseEffect = workLicenseEffect;
	}

	public Date getWorkLicenseInvalid() {
		return workLicenseInvalid;
	}

	public void setWorkLicenseInvalid(Date workLicenseInvalid) {
		this.workLicenseInvalid = workLicenseInvalid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

	public String getJobNumPre() {
		return jobNumPre;
	}

	public void setJobNumPre(String jobNumPre) {
		this.jobNumPre = jobNumPre;
	}


}
