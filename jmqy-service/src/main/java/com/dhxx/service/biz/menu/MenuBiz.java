package com.dhxx.service.biz.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhxx.facade.entity.menu.Menu;
import com.dhxx.service.mapper.menu.MenuMapper;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月08日
 * @version 1.01
 *
 */

@Service
@Transactional
public class MenuBiz {
    
	@Autowired
	private MenuMapper menuMapper;

	public List<Menu> findMenus(Menu menu){
		return menuMapper.findMenus(menu);
	}

    public List<Menu> leftMenus(Menu menu) {
	    return menuMapper.leftMenus(menu);
    }

    public List<Menu> haveMenus(Menu menu) {
	    return menuMapper.haveMenus(menu);
    }
}
