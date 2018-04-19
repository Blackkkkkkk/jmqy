package com.dhxx.facade.entity.log;

import com.dhxx.facade.entity.SysBaseEntity;

import java.util.Date;
import java.io.Serializable;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年10月21日
 * @version 1.01
 * 日志表(t_logs)
 */
public class Logs extends SysBaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String logRes; //日志模块
	private String logType;//日志类型
	private Long userId;//用户ID
	private String userName;//用户名
	private Long companyId;//企业ID
	private Date logTime;//日志时间
    private String remark;//备注
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogRes() {
		return logRes;
	}
	public void setLogRes(String logRes) {
		this.logRes = logRes;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}