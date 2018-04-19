package com.dhxx.facade.entity.permission;

import java.io.Serializable;
import java.util.List;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月07日
 * @version 1.01
 * shiro权限管理
 */

public class AuthInfo implements Serializable {

    /** 属性说明 */
    private static final long serialVersionUID = 1L;
    
    private Long menuId;
    private String menuItem; // 菜单项
    private String menuUrl;
    private String roles;
    private Long parentId; // 父类id
	private String pic;//图标
    private List<String> roleList;
    
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(String menuItem) {
		this.menuItem = menuItem;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public List<String> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
    
}
