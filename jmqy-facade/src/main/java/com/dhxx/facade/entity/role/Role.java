package com.dhxx.facade.entity.role;

import java.io.Serializable;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月22日
 * @version 1.01
 * 角色管理 (t_role)
 */
public class Role extends SysBaseEntity implements Serializable {
    
    /** 属性说明 */
    private static final long serialVersionUID = 1L;
    
    private Long id;// 主键
    private String roleName;//  角色名称
    private String roleDescribe;//  描述
    private Integer roleType;//  角色类型：0:public-公共的，1:private-私人的
    private String companyCode;//  所属企业编码(当角色类型：private-私人的时候为必须)
    private Long companyId;//企业id
	private Integer status;//状态: 0:启用 1:禁用
    private String remark;//备注
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescribe() {
		return roleDescribe;
	}
	public void setRoleDescribe(String roleDescribe) {
		this.roleDescribe = roleDescribe;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
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
    
}
