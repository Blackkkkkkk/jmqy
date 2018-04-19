package com.dhxx.facade.entity.user;

import java.io.Serializable;
import java.util.Date;

import com.dhxx.facade.entity.SysBaseEntity;
import com.dhxx.facade.entity.company.Company;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月07日
 * @version 1.01
 * 用户管理(t_user)
 */

public class User extends SysBaseEntity implements Serializable{

    /** 属性说明 */
    private static final long serialVersionUID = 1L;

    private Long id;// 主键
    private String userCode;//用户编码
	private String userAccount;// 用户账号
	private String userName;// 用户名
	private String userPassword;// 密码
	private String salt;// 密码干扰
	private String phone;// 手机号
	private String role;// 角色 
	private Integer type;//用户类型 # 0:普通账号 1:主体账号
	private String idCard;// 身份证号
	private String companyCode;//企业编码
	private Integer status;// 状态 0:启用 1:禁用 2:不通过
	private Date registerDate;// 注册时间
    private String loginType;//登录方式：1 PC端 2微信端

    private String  Userid; // 用户表的ID

	private String newPassword;// 新密码：只做修改密码的时候使用
    /*
     *role(角色):
     *sys_admin	超级管理员(系统初始化)
     *sys_user 管理员用户(系统初始化)
     *charter 普通包车方(系统初始化)
     *charter_admin 会员包车方管理员(系统初始化)
     *charter_user 会员包车方用户(系统初始化)
     *transport_admin 运输方管理账号(系统初始化)
     *transport_user 运输方用户账号(系统初始化)
    */
	private String roleName;//角色名称
	private Integer roleStatus;//角色状态: 0:启用 1:禁用
    private Long companyType;//企业类型
	private Long companyId;//企业id
	private String companyName;//企业名称
	private Integer sex;//性别 0:女 1:男
    private String email;//邮箱
    private String address;//公司地址
    private String jobNumPre;//企业工号前缀


    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(Integer roleStatus) {
		this.roleStatus = roleStatus;
	}

	public Long getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Long companyType) {
        this.companyType = companyType;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJobNumPre() {
        return jobNumPre;
    }

    public void setJobNumPre(String jobNumPre) {
        this.jobNumPre = jobNumPre;
    }
}
