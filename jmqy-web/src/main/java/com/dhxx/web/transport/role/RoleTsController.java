package com.dhxx.web.transport.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.user.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.menu.Menu;
import com.dhxx.facade.entity.permission.Permission;
import com.dhxx.facade.entity.role.Role;
import com.dhxx.facade.service.menu.MenuFacade;
import com.dhxx.facade.service.permission.PermissionFacade;
import com.dhxx.facade.service.role.RoleFacade;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;

/**
 * @author hanrs
 * @date 2017/9/22
 * @description
 * 运输方角色管理
 */
@Controller
@RequestMapping("transport/role")
@SuppressWarnings("unchecked")
public class RoleTsController {
	
	private static Logger log = LoggerFactory.getLogger(RoleTsController.class);

    @Reference(protocol="dubbo")
    private RoleFacade roleFacade;
    
    @Reference(protocol="dubbo")
    private PermissionFacade permissionFacade;

    @Reference(protocol="dubbo")
    private MenuFacade menuFacade;

    @Reference(protocol = "dubbo")
    private UserFacade userFacade;

    //运输方自己创建的角色列表
    @RequestMapping(value = "list")
    public String list(Role role, Model model, HttpServletRequest requst) {
    	ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    	role.setCompanyCode(shiroUser.companyCode);
    	role.setRoleType(1);
        PageInfo<Role> roles = (PageInfo<Role>) roleFacade.list(role);
        model.addAttribute("role", role);
        model.addAttribute("roles", roles);
        return "transport/role/list";
    }
    
    //新增或者修改
    @RequestMapping(value = "form")
    public String form(Role role, Model model, HttpServletRequest requst) {
		if(role.getId() != null){
			List<Role> roles = (List<Role>) roleFacade.find(role);
			if(!CollectionUtils.isEmpty(roles)){
				role = roles.get(0);
			}
		}
		model.addAttribute("role", role);
        return "transport/role/form";
    }
    
    //角色授权
    @RequestMapping(value = "authorize")
    public String authorize(Role role, Model model, HttpServletRequest requst) {
    	 Menu menu = new Menu();
         menu.setRemark("transport");
         List<Menu> menus = (List<Menu>) menuFacade.findMenus(menu);
         model.addAttribute("menus",menus);
         PageInfo<Menu> pageInfo = new PageInfo<Menu>(menus);
         model.addAttribute("pageInfo",pageInfo);
         model.addAttribute("role",role);
         List<Long> menuIds = (List<Long>) permissionFacade.menuIds(role.getId());
         model.addAttribute("menuIds",menuIds);
        return "transport/role/authorize";
    }
    
    //提交角色授权
    @ResponseBody
    @RequestMapping(value="doAuthorize")
    public Map<String, Object> doAuthorize(Permission p) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        /*    User us = new User();
            us.setId(shiroUser.id);
            List<User> list = (List<User>) userFacade.find(us);
            if (!CollectionUtils.isEmpty(list)) {
                us = list.get(0);
            }*/

            p.setCompanyId(shiroUser.companyId+"");
            permissionFacade.save(p);
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }
    
    //更新或者新增
    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    public String saveOrUpdate(Role role, Model model, RedirectAttributes redirectAttributes){
 	   String message = (role.getId() == null?"新建":"更新")+"成功@1";
 	   try{
 		   if(role.getId() == null){
               ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
               role.setCompanyCode(shiroUser.companyCode);
               role.setRoleType(1);
               role.setStatus(0);
               Object obj = roleFacade.save(role);

               Role role1 = (Role)obj;
               Permission permission = new Permission();
               permission.setCompanyId(String.valueOf(shiroUser.companyId));
               permission.setIds("35,36");
               permission.setRoleId(role1.getId());
               permissionFacade.save(permission);

 		   }else{
 			  roleFacade.update(role);
 		   }
        } catch (Exception e) {
     	   message = message.replace("成功@1", "失败@2");
     	   log.error(e.getMessage());
        }
 	    redirectAttributes.addFlashAttribute("message", message); 
        return "redirect:/transport/role/form";
    }
    
    /*
	* status:
	* 0:启用 1:禁用
	*/
   @ResponseBody
   @RequestMapping(value = "updateById", method = RequestMethod.POST)
   public Long updateById(Role role, Model model) {
	   try{
		   return (Long) roleFacade.update(role);
       } catch (Exception e) {
    	   log.error(e.getMessage());
    	   return -1l;
       }
   }

}
