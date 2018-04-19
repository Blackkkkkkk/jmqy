package com.dhxx.web.login;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.dhxx.common.wechat.WechatUtils;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.entity.weappOA.Usermessage;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.facade.service.weChatOA.WechatOAFacade;
import com.dhxx.web.shiro.InitUsernamePasswordToken;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.dhxx.web.utils.MessagePush;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("WechatLogin")
public class WechatLoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private SessionDAO sessionDAO;

    @Reference(protocol="dubbo")
    private WechatOAFacade wechatOAFacade;

    @RequestMapping(value = "wechatLogin")
    public String wechatLogin(@RequestParam(value = "code") String code, Usermessage usermessage, Model model,
                              RedirectAttributes redirectAttributes, HttpServletRequest request){

        String redurl = null;
        try {

            WechatUtils wechatUtils = new WechatUtils();
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxb2acefba99a3fbcf&secret=20cc17d404c4f9579815e80ecae0064b&code=" + code + "&grant_type=authorization_code ";
            String re = wechatUtils.httpRequest(url, "GET", null);
            JSONObject jsStr = JSONObject.parseObject(re);
            String access_token = jsStr.getString("access_token");
            String openid = jsStr.getString("openid");
            url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN  ";
            re = wechatUtils.httpRequest(url, "GET", null);
            System.out.println("jsStr=" + jsStr);
            jsStr = JSONObject.parseObject(re);
            String unionid = jsStr.getString("unionid");

            if(usermessage.getUnionid()!=null&&usermessage.getUnionid()!=""){
                unionid=usermessage.getUnionid();
            }


            Map<String, Object> map1 = wechatOAFacade.findUserMessage(unionid);
            if(map1==null || map1.size()<1)
            {
                if(unionid!=null&&unionid!=""){
                    usermessage.setUnionid(unionid);
                }
                if(openid!=null&&openid!=""){
                    usermessage.setOpenid(openid);
                }
            }else {
                System.out.println("map1=" + map1);
                usermessage.setPhone(map1.get("WECHAT_PHONE").toString());
                usermessage.setUnionid(map1.get("WECHAT_UNIONID").toString());
                usermessage.setOpenid(map1.get("WECHAT_OPENID").toString());
            }


        }catch (Exception e){

        }

        User user1 = new User();
        user1.setUserAccount("wx"+usermessage.getPhone());

        List<User> list = (List<User>) userFacade.find(user1);
        String password = null;
        if(!org.springframework.util.CollectionUtils.isEmpty(list)){
            user1 = list.get(0);
            password  = user1.getUserPassword();
        }

        InitUsernamePasswordToken token = new InitUsernamePasswordToken("wx"+usermessage.getPhone(),password,2,null);

        try {
            Session ses = null;
            Collection<Session> sessions = sessionDAO.getActiveSessions();
            String userAccount = "wx"+usermessage.getPhone();
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
                user.setUserAccount("wx"+usermessage.getPhone());
                List<User> users = (List<User>)userFacade.find(user);
                if (CollectionUtils.isNotEmpty(users)) {
                    user = users.get(0);
                    MessagePush.sendMessageAuto("logout", user.getId()+"", "");
                }
                ses.stop();//设置session立即失效，即将其踢出系统
            }
            model.addAttribute("message", "0");
            ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            model.addAttribute("role", shiroUser.roleName);


            if(shiroUser.roleName.indexOf("sys") != -1){
                redurl = "main/main";
            }else if(shiroUser.roleName.indexOf("charter") != -1){
                redurl = "charter/order/release";
            }else{
                redurl = "transport/order/pend";
            }

        }catch (AuthenticationException e){
            String error = e.getClass().toString();
            if (error.contains("UnknownAccountException")) {
                redirectAttributes.addFlashAttribute("message",1);
            }
            if (error.contains("IncorrectCredentialsException")) {
                redirectAttributes.addFlashAttribute("message",2);
            }
            if (error.contains("DisabledAccountException")) {
                redirectAttributes.addFlashAttribute("message",3);
            }
            if (error.contains("AuthenticationException")) {
                redirectAttributes.addFlashAttribute("message",4);
            }
            log.error(error+">>>>>>>>>>>>登录出错");

            redurl = "redirect:/index";
        }

           return  redurl;
    }
}
