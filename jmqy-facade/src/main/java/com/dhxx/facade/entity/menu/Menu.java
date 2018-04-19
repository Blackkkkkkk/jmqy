package com.dhxx.facade.entity.menu;

import java.io.Serializable;
import java.util.List;

/**
 * <p> 类说明 </p>
 * @author jhy
 * Date: 2017年08月07日
 * @version 1.01
 * 菜单管理(t_menu)
 */

public class Menu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Long id;//表id
    private String menuItem;//菜单项
    private String menuPath;//菜单路径
    private Long parentId;//菜单父类id
    private Long sort;//排序
    private Long status;//状态: 0:启用 1:禁用
    private String remark;//备注 sys：平台 transport：运输 charter：包车
    
    //附属
    private String remarks;//备注 sys：平台 transport：运输 charter：包车
    private Long roleId;//角色id
    private Long companyid;//所属公司的id;
    private List<Menu> childMenus;//二级菜单

    public Long getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Long companyid) {
        this.companyid = companyid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Menu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<Menu> childMenus) {
        this.childMenus = childMenus;
    }

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
