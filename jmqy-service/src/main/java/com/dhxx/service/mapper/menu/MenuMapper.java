package com.dhxx.service.mapper.menu;

import java.util.List;

import com.dhxx.facade.entity.menu.Menu;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月08日
 * @version 1.01
 *
 */
public interface MenuMapper {
    
	List<Menu> findMenus(Menu menu);

    List<Menu> leftMenus(Menu menu);

    List<Menu> haveMenus(Menu menu);
    
    String findMenuIds();
}
