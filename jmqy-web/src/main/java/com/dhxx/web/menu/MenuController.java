package com.dhxx.web.menu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.menu.Menu;
import com.dhxx.facade.entity.permission.Permission;
import com.dhxx.facade.service.menu.MenuFacade;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jhy
 * @date 2017/9/23
 * @description
 * 菜单管理
 */
@Controller
@RequestMapping("menu")
public class MenuController {

    @Reference(protocol="dubbo")
    private MenuFacade menuFacade;

    @ResponseBody
    @RequestMapping(value="left")
    public Map<String, Object> left() {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            Menu menu = new Menu();
            menu.setRemark(shiroUser.roleId);
            menu.setRoleId(Long.parseLong(shiroUser.roleId));
            List<Menu> menuList = (List<Menu>) menuFacade.leftMenus(menu);
            map.put("menus", menuList);
            String roleName = shiroUser.roleName;
            if(roleName.contains("charter")){
                map.put("asideTitle", "个人中心");
            }
            if(roleName.contains("transport")){
                map.put("asideTitle", "管理中心");
            }
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

}
