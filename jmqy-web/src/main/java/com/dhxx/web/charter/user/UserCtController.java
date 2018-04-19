package com.dhxx.web.charter.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.dhxx.facade.entity.role.Role;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.role.RoleFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;

/**
 * @author hanrs
 * @date 2017/10/13
 * @description
 * 包车方用户管理
 */
@Controller
@RequestMapping("charter/user")
@SuppressWarnings("unchecked")
public class UserCtController {
	
	private static Logger log = LoggerFactory.getLogger(UserCtController.class);

    @Reference(protocol="dubbo")
    private UserFacade userFacade;
    
    @Reference(protocol="dubbo")
    private RoleFacade roleFacade;

    //包车方的自己创建用户列表
    @RequestMapping(value = "list")
    public String list(User user, Model model, HttpServletRequest requst) {
    	ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    	user.setCompanyCode(shiroUser.companyCode);
    	user.setType(0);
        PageInfo<User> users = (PageInfo<User>) userFacade.list(user);
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        return "charter/user/list";
    }
    
    @RequestMapping(value = "form")
    public String form(User user, Model model, HttpServletRequest requst) {
		if(user.getId() != null){
			List<User> users = (List<User>) userFacade.find(user);
			if(!CollectionUtils.isEmpty(users)){
				user = users.get(0);
			}
		}
		model.addAttribute("user", user);
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		Role role = new Role();
    	role.setCompanyCode(shiroUser.companyCode);
    	role.setRoleType(1);
        model.addAttribute("roles", roleFacade.find(role));
        return "charter/user/form";
    }
    
    //更新或者新增
    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    public String saveOrUpdate(User user, Model model, RedirectAttributes redirectAttributes){
 	   String message = (user.getId() == null?"新建成功@1":"更新成功@1");
 	   try{
 		   if(user.getId() == null){
 			  ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
 			  user.setCompanyCode(shiroUser.companyCode);
 			  user.setRegisterDate(new Date());
 			  user.setType(0);
 			  user.setStatus(0);
 			  ShiroUserUtils.encryptPassword(user);
 			  userFacade.save(user);
 		   }else{
 			  userFacade.update(user);
 		   }
        } catch (Exception e) {
     	   message = message.replace("成功@1", "失败@2");
     	   log.error(e.getMessage());
        }
 	   redirectAttributes.addFlashAttribute("message", message); 
        return "redirect:/charter/user/form";
    }
    
    /*
	* status:
	* 0:启用 1:禁用 2:不通过
	*/
   @ResponseBody
   @RequestMapping(value = "updateById", method = RequestMethod.POST)
   public Long updateById(User user, Model model) {
	   try{
		   return (Long) userFacade.update(user);
       } catch (Exception e) {
    	   log.error(e.getMessage());
    	   return -1l;
       }
   }

}
