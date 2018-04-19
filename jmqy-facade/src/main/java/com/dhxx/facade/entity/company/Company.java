package com.dhxx.facade.entity.company;

import com.dhxx.facade.entity.SysBaseEntity;

import java.util.Date;
import java.io.Serializable;

/**
 * <p> 类说明 </p>
 * @author dingbin
 * Date: 2017年09月17日
 * @version 1.01
 * 企业表(t_company)
 */
public class Company extends SysBaseEntity implements Serializable{

	private Long id;
	private Long userId;//用户ID
	private String companyCode;//企业编码
	private String companyName;//企业名称
	private String businessPic; // 企业营业执照附件
	private String companyAddress;//企业地址
	private String jobNumPre;//企业工号前缀
	private Long type;//企业类型：0平台方 1个人包主 2包车企业 3运输企业
	private Long status;//状态: -1待审核  0:启用 1:禁用 2:不通过
	private Date registerDate;//注册时间
    private String remark;//备注
    private String telephone; //座机

    private String userAccount;//联系人（主账号）
    private String param;//搜索参数

    //辅助搜索字段
    private  String actionType;//1是退款方案新增时候的select选择框，过滤已有的企业

    private String  selectType;// 平台方的运输企业选择区别司机和车辆的标识符 1 车辆  2司机

    private String changeType;//1:新增，2修改

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
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

    public String getBusinessPic() {
		return businessPic;
	}

	public void setBusinessPic(String businessPic) {
		this.businessPic = businessPic;
	}

	public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getJobNumPre() {
		return jobNumPre;
	}

	public void setJobNumPre(String jobNumPre) {
		this.jobNumPre = jobNumPre;
	}

	public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}