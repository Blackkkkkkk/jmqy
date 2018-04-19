package com.dhxx.facade.entity.user;

import java.io.Serializable;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月07日
 * @version 1.01
 * app登陆用户信息
 */

public class UserInfo implements Serializable{

    /** 属性说明 */
    private static final long serialVersionUID = 1L;

    private Long id;// 主键
	private String userAccount;// 用户账号
	private String userName;// 用户名
	private String phone;// 手机号
	private String role;// 角色 
	private Integer type;//用户类型 # 1:司机，2：乘客
	private String companyCode;//企业编码
	private String companyName;//企业名称
	private String token;
	private String encoder;
	private String code;
	
	public String getEncoder() {
		return encoder;
	}
	public void setEncoder(String encoder) {
		this.encoder = encoder;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

}
