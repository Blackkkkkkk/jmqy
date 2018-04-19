package com.dhxx.web.register;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.common.sms.SingletonSMSClient;
import com.dhxx.common.util.Md5Tool;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.shiro.ShiroUserUtils;

/**
 * @author jhy
 * @date 2017/9/20
 * @description
 * 用户注册
 */
@Controller
@RequestMapping("register")
public class RegisterController {
	
	private static Logger log = LoggerFactory.getLogger(RegisterController.class);

    @Reference(protocol="dubbo")
    private UserFacade userFacade;

    //注册页面
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String init(Model model, HttpServletRequest request){
        return "register/register";
    }

    //保存注册信息
    @ResponseBody
    @RequestMapping(value="init")
    public Map<String, Object> init(User user){
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            ShiroUserUtils.encryptPassword(user);
            userFacade.init(user);
            map.put("role", user.getRoleName());
            map.put("name", user.getUserAccount());
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

    //发送短信获取验证码
    @ResponseBody
    @RequestMapping(value="getSms")
    public Map<String, Object> getSms(@RequestParam("phone") String phone) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        String smsCode = SingletonSMSClient.getRandNum(6);
        try {
            int i = SingletonSMSClient.getClient().sendSMS(new String[] { phone }, SingletonSMSClient.msg.replace("code", smsCode), "",5);
            if(i!=0){
                state = false;
            }else{
                String code = Md5Tool.getMd5(phone+smsCode)+"@"+new Date().getTime();
                map.put("code", code);
            }
        } catch (RemoteException e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

    //校验账号是否存在
    @ResponseBody
    @RequestMapping(value="checkLogin")
    public boolean checkLogin(User user) {
        try {
            List<User> list = (List<User>) userFacade.find(user);
            if(CollectionUtils.isEmpty(list)){
            	return true;
            }else {
            	return false;
			}
        } catch (Exception e) {
        	log.error(e.getMessage());
        	return false;
        }
    }

    //校验验证码
    @ResponseBody
    @RequestMapping(value="checkCode")
    public Map<String, Object> checkCode(@RequestParam("code") String code,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("codeEncoder") String codeEncoder) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            String[] arr = codeEncoder.split("@");
            code = Md5Tool.getMd5(phone+code);
            if(!code.equals(arr[0])){
                state = false;
            }else{
                Long now = new Date().getTime();
                long minute = (now - Long.valueOf(arr[1]))/(1000*60);
                if(minute > 5){
                    state = false;
                }
            }
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

}
