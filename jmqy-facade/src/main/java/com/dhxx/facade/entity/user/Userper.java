package com.dhxx.facade.entity.user;

import com.dhxx.facade.entity.SysBaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jhy
 * @date 2017/9/14
 * @description
 * 用户权限、审批
 */
public class Userper extends SysBaseEntity implements Serializable {

    private Long id;//用户ID
    private Long roleId;//角色
    private String userCode;//用户编码
    private String userAccount;//企业账号
    private Date registerDate;//申请日期
    private Long companyId;//企业ID
    private String companyCode;//企业编码
    private String companyName;//企业名称
    private Long companyType;//企业类型 1个人包主 2包车企业 3运输企业
    private String remark;//备注
    private String address; //地址

    private Long companyStatus;//状态 0:启用 1:禁用 2:不通过
    private String param;//搜索参数
    private String coefficient;  //优惠率


    //User对应的字段
    private  String userName;
    private  String userPhone;
    private  String userStatus;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

    public Long getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Long companyType) {
        this.companyType = companyType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(Long companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

}

