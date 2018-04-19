package com.dhxx.service.service.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.menu.Menu;
import com.dhxx.facade.service.menu.MenuFacade;
import com.dhxx.service.biz.menu.MenuBiz;

@Service(protocol = {"dubbo"})
public class MenuFacadeImpl implements MenuFacade{
    
	@Autowired
	private MenuBiz menuBiz;
	
	@Override
	public List<Menu> findMenus(Menu menu) {
		return menuBiz.findMenus(menu);
	}

    @Override
    public Object leftMenus(Menu menu) {
        return menuBiz.leftMenus(menu);
    }

    @Override
    public Object haveMenus(Menu menu) {
        return menuBiz.haveMenus(menu);
    }

}
