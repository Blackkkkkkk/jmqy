package com.dhxx.web.user;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.menu.Menu;
import com.dhxx.facade.entity.transport.driver.Driver;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.menu.MenuFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 
 * @author jhy
 * @description  
 * 
 */
@Controller
@RequestMapping("user")
public class UserController {
    
    @Reference(protocol="dubbo")
    private UserFacade userFacade;

    @Reference(protocol="dubbo")
    private MenuFacade menuFacade;

    @RequestMapping(value = "main")
    public String main(User user, Model model, HttpServletRequest requst) {
        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        requst.getSession().setAttribute("userId", shiroUser.id);  
        String role = shiroUser.roleName;
        if(role.contains("sys")){
            return "redirect:/main";
        }else{
            Menu menu = new Menu();
            menu.setRoleId(Long.parseLong(shiroUser.roleId));
            List<Menu> menus = (List<Menu>) menuFacade.leftMenus(menu);
            if(!CollectionUtils.isEmpty(menus)){
                menu = menus.get(0);
                String url = menu.getMenuPath();
                if(!StringUtils.isNotEmpty(url)){
                    menu = menu.getChildMenus().get(0);
                }
            }
            String path = menu.getMenuPath();
            if(StringUtils.isNotEmpty(path)){
                return "redirect:/"+path;
            }else{
                return "index/index";
            }
        }
    }
    
    @RequestMapping(value = "list")
    public String list(User user, Model model, HttpServletRequest requst) {
    	PageInfo<User> users = (PageInfo<User>) userFacade.list(user);
        model.addAttribute("users", users);
        return "userList";
    }
    
    //校验司机企业工号或者身份证是否唯一
    @ResponseBody
    @RequestMapping(value = "userAccountUnique", method = RequestMethod.POST)
    public Boolean userAccountUnique(User user) {
 	   Long id = user.getId();
 	   user.setId(null);
 	   List<User> users = (List<User>) userFacade.find(user);
 		if(!CollectionUtils.isEmpty(users)){
 			if (id != null && id.equals(users.get(0).getId())) {
 				return true;
 			} else {
 				return false;
 			}
 		}else{
 			return true;
 		}
    }
}
