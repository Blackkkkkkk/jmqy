package com.dhxx.facade.entity.transport.certificate;

import java.io.Serializable;
import java.util.Date;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月18日
 * @version 1.01
 * 证件管理
 */

public class Certificate extends SysBaseEntity implements Serializable{

    /** 属性说明 */
    private static final long serialVersionUID = 1L;
    
    private Long id;//主体表id
    private String code;//主体编码
    private String name;//主体名称
    private Date effect;//生效日期
    private Date invalid;//截止日期
    private Integer subjectType;//主体类型：1司机，2车辆
    private Integer type;//证件类型：1上岗证，2驾驶证，3行驶证，4营运证，5保险单
    private Integer status;//证件状态：0正常，1即将过期，2已经过期
    private Integer expireDays;//离过期还有多少天数
    
    private String transporterCode;//运输方编码
    
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getEffect() {
		return effect;
	}
	public void setEffect(Date effect) {
		this.effect = effect;
	}
	public Date getInvalid() {
		return invalid;
	}
	public void setInvalid(Date invalid) {
		this.invalid = invalid;
	}
	public Integer getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getExpireDays() {
		return expireDays;
	}
	public void setExpireDays(Integer expireDays) {
		this.expireDays = expireDays;
	}
	public String getTransporterCode() {
		return transporterCode;
	}
	public void setTransporterCode(String transporterCode) {
		this.transporterCode = transporterCode;
	}
}
