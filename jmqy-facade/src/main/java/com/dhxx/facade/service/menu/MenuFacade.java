package com.dhxx.facade.service.menu;

import com.dhxx.facade.entity.menu.Menu;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月08日
 * @version 1.01
 * 菜单管理interface
 */

public interface MenuFacade {
    
	Object findMenus(Menu menu);//查询菜单

	Object leftMenus(Menu menu);//查询左侧菜单

    Object haveMenus(Menu menu);//判断是否有权限菜单

}
