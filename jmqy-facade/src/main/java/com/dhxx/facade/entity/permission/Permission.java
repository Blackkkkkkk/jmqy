package com.dhxx.facade.entity.permission;

import com.dhxx.facade.entity.SysBaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月07日
 * @version 1.01
 * 权限管理(t_permission)
 */
public class Permission extends SysBaseEntity {
    
    private Long menuId;//菜单Id
    private Long roleId;//角色Id
    private String companyId;//公司Id

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
