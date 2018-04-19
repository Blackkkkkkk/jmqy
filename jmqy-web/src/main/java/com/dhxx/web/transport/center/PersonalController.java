package com.dhxx.web.transport.center;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import com.dhxx.facade.entity.company.Company;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.company.CompanyFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月13日
 * @version 1.01
 * 运输方个人中心管理
 */
@Controller
@RequestMapping("transport/center")
@SuppressWarnings("unchecked")
public class PersonalController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(PersonalController.class);
    
	@Reference(protocol="dubbo")
    private UserFacade userFacade;
	
	@Reference(protocol="dubbo")
    private CompanyFacade companyFacade;
    
	@RequestMapping(value = "personal")
    public String personal(User user, Company company, Model model, HttpServletRequest requst) {
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    	user.setId(shiroUser.id);
		List<User> users= (List<User>) userFacade.find(user);
    	if(!CollectionUtils.isEmpty(users)){
    		user = users.get(0);
    	}
    	model.addAttribute("user", user);
    	company.setCompanyCode(shiroUser.companyCode);
    	List<Company> companys= (List<Company>) companyFacade.find(company);
    	if(!CollectionUtils.isEmpty(companys)){
    		company = companys.get(0);
    	}
    	model.addAttribute("company", company);
        return "transport/center/personal";
    }
	
	//修改用户信息
    @RequestMapping(value ="updateUser", method = RequestMethod.POST)
    public String updateUser(User user, RedirectAttributes redirectAttributes){
    	String message = "更新成功@1";
    	try{
    		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        	user.setId(shiroUser.id);
    		userFacade.update(user);
    	} catch (Exception e) {
	 	    message = message.replace("成功@1", "失败@2");
	 	    log.error(e.getMessage());
    	}
	   redirectAttributes.addFlashAttribute("message", message); 
	   return "redirect:/transport/center/personal";
    }
    
    //用户修改自己密码
    @ResponseBody
    @RequestMapping(value ="updatePw", method = RequestMethod.POST)
    public int updatePw(User user){
    	try{
    		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    		User u = new User();
    		u.setId(shiroUser.id);
    		List<User> users= (List<User>) userFacade.find(u);
        	if(!CollectionUtils.isEmpty(users)){
        		u = users.get(0);
        	}
    		 String password = u.getUserPassword();
    		 u.setUserPassword(user.getUserPassword());
             ShiroUserUtils.checkPassword(u);
             if(!password.equals(u.getUserPassword())){
            	 return -1;
             }
             user.setUserPassword(user.getNewPassword());
             user.setUserAccount(shiroUser.userAccount);
             user.setId(shiroUser.id);
             ShiroUserUtils.encryptPassword(user);
             userFacade.update(user);
    	} catch (Exception e) {
    		log.error(e.getMessage());
    		return -2;
    	}
    	//密码修改成功就登出
    	 Subject currentUser = SecurityUtils.getSubject();
         if (currentUser.getSession() != null){
             ShiroUser shiroUser = (ShiroUser)currentUser.getPrincipal();
             if(shiroUser != null){
                 currentUser.logout();
                 log.debug(shiroUser.getName()+">>>>>>>>>>>>修改密码成功后，自动退出登录");
             }
         }
	   return 0;
    }
	
}
