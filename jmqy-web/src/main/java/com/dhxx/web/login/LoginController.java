package com.dhxx.web.login;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.dhxx.common.wechat.WechatUtils;
import com.dhxx.facade.entity.weappOA.Usermessage;
import com.dhxx.facade.service.weChatOA.WechatOAFacade;
import com.dhxx.web.shiro.InitUsernamePasswordToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.dhxx.common.util.RemoteAddrUtils;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.dhxx.web.utils.MessagePush;


/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月08日
 * @version 1.01
 *
 */
@Controller
@RequestMapping("login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
   	private UserFacade userFacade;
    
    @Autowired
	private SessionDAO sessionDAO;




    @RequestMapping(value = "", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request){
        String result = "login/login";
        ShiroUser user = ShiroUserUtils.getShiroUser();
        if(user != null){
            result = "redirect:/index";
        }else {
            log.debug("游客进入登录页面>>>>>>>>>>>>"+RemoteAddrUtils.getIp(request));
        }
        return result;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username, 
                       @RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password,
                       Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
       // UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        //根据用户名，去数据库查询哈希盐值
        User user1 = new User();
             user1.setUserAccount(username);

        List<User> list = (List<User>) userFacade.find(user1);
        String salt = null;
        if(!org.springframework.util.CollectionUtils.isEmpty(list)){
            user1 = list.get(0);
            salt  = user1.getSalt();
        }
        //继承UsernamePasswordToken类，新增登录类型，和哈希盐值变量
       InitUsernamePasswordToken token = new InitUsernamePasswordToken(username,password,1,salt);

        try {
            Session ses = null;
			Collection<Session> sessions = sessionDAO.getActiveSessions();
			String userAccount = username;
			for(Session s:sessions){
				if(userAccount.equals(String.valueOf(s.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
					ses = s;
					break;
				}
			}

			Subject subject = SecurityUtils.getSubject();
            System.out.println("token="+token);
            subject.login(token);
			if(ses != null){
				User user = new User();
				user.setUserAccount(username);
				List<User> users = (List<User>)userFacade.find(user);
				if (CollectionUtils.isNotEmpty(users)) {
					user = users.get(0);
					MessagePush.sendMessageAuto("logout", user.getId()+"", "");
				}
				ses.stop();//设置session立即失效，即将其踢出系统
			}
            model.addAttribute("message", "0");
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            model.addAttribute("role", shiroUser.roleName);
        }catch (AuthenticationException e){
            String error = e.getClass().toString();
            if (error.contains("UnknownAccountException")) {

                model.addAttribute("message", "1");
            }
            if (error.contains("IncorrectCredentialsException")) {
                model.addAttribute("message", "2");
            }
            if (error.contains("DisabledAccountException")) {
                model.addAttribute("message", "3");
            }
            if (error.contains("AuthenticationException")) {
                model.addAttribute("message", "4");
            }
            log.error(error+">>>>>>>>>>>>登录出错");
        }
        return "login/login";
    }



    @RequestMapping(value="out")
    public String logout(Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.getSession() != null){
            ShiroUser shiroUser = (ShiroUser)currentUser.getPrincipal();
            if(shiroUser != null){
                currentUser.logout();
                log.debug(shiroUser.getName()+">>>>>>>>>>>>退出登录");
            }
        }
        return "redirect:/index";
    }



}
