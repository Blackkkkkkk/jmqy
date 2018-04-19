package com.dhxx.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.user.UserFacade;
import com.github.pagehelper.PageInfo;

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
    
    @RequestMapping(value = "list")
    public String list(User user, Model model, HttpServletRequest requst) {
    	PageInfo<User> users = (PageInfo<User>) userFacade.list(user);
        model.addAttribute("users", users);
        return "userList";
    }
		
}
