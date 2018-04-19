package com.dhxx.web.main;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.menu.Menu;
import com.dhxx.facade.service.menu.MenuFacade;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jhy
 * @date 2017/9/11
 * @description
 * 后台管理
 */
@Controller
@RequestMapping("main")
public class MainController {

    @Reference(protocol="dubbo")
    private MenuFacade menuFacade;

    @RequestMapping(value = "")
    public String main(Model model, HttpServletRequest request) {
        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String role = shiroUser.roleName;
        List<Menu> menus = new ArrayList<Menu>();
        Menu menu = new Menu();
        if(StringUtils.isNotEmpty(role)){
            if("sys_admin".equals(role)){
                role = role.substring(0, role.indexOf('_'));
                menu.setRemark(role);
                menu.setCompanyid(shiroUser.companyId);
                menus = (List<Menu>) menuFacade.findMenus(menu);
            }else{
            	menu.setRoleId(Long.parseLong(shiroUser.roleId));
                menu.setCompanyid(shiroUser.companyId);
                menus = (List<Menu>) menuFacade.leftMenus(menu);
            }
        }
        if(!CollectionUtils.isEmpty(menus)){
            menu = menus.get(0);
            String url = menu.getMenuPath();
            if(!StringUtils.isNotEmpty(url)){
                menu = menu.getChildMenus().get(0);
            }
        }
        model.addAttribute("menu",menu);
        model.addAttribute("menus",menus);
        return "main/main";
    }
}
