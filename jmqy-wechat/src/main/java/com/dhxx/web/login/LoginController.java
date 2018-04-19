package com.dhxx.web.login;

import java.rmi.RemoteException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.dhxx.common.util.Md5Tool;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.utils.SingletonSMSClient;
import com.dhxx.common.wechat.WechatUtils;
import com.dhxx.facade.entity.weappOA.Usermessage;

import com.dhxx.facade.service.weChatOA.WechatOAFacade;
import com.dhxx.web.utils.wechatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.dhxx.web.shiro.ShiroUserUtils;




/**
 * Created by Administrator on 2017/11/7.
 */
@SessionAttributes(value={"usermessage"},types={Usermessage.class})
@Controller
@RequestMapping("login")
public class LoginController{
    @Reference(protocol="dubbo")
    private WechatOAFacade wechatOAFacade;

    @Reference(protocol="dubbo")
    private UserFacade userFacade;

    @RequestMapping(value = "")
    public  String login(Usermessage usermessage ,HttpSession session,@RequestParam(value = "code",required = false) String code){

        if (code!=null&&code!="") {

           usermessage=setName(code,usermessage);

        }

        session.setAttribute("usermessage",usermessage);

        return "login/login";
    }

    @RequestMapping(value = "index")
    public  String index(HttpSession session,@RequestParam(value = "code" ,required = false) String code){


        Usermessage usermessage =(Usermessage)session.getAttribute("usermessage");
        //设置用户名
        if (usermessage==null||usermessage.getPhone()==null){

            usermessage=new Usermessage();

            if (code!=null&&code!="") {

                usermessage=setName(code,usermessage);
            }
        }else {
            usermessage =(Usermessage) session.getAttribute("usermessage");
        }

        session.setAttribute("usermessage",usermessage);

        return "index/index";
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
        System.out.println("state="+state);
        map.put("state", state);

        return map;
    }



    @ResponseBody
    @RequestMapping(value="checkCode1")
    // @ModelAttribute
    public Map<String, Object> checkCode(Usermessage usermessage,HttpSession session) {

        Map<String, Object> map = new HashMap<String, Object>();

        String code = usermessage.getPhonecode();
        boolean state = true;
        try {

            System.out.println("1111");
            String[] arr = usermessage.getCodeEncoder().split("@");
            code = Md5Tool.getMd5(usermessage.getPhone()+usermessage.getPhonecode());
            if(!code.equals(arr[0])){

                state = false;
            }else{
                Long now = new Date().getTime();
                long minute = (now - Long.valueOf(arr[1]))/(1000*60);

                if(minute > 5){
                    System.out.println("minute"+minute);
                    state = false;
                }

            }
        } catch (Exception e) {

            state = false;
        }

        System.out.println("state="+state);
        if (state){
            System.out.println("进来；额");
            //判断用户是否存在，不存在则新建用户信息
            System.out.println("loginController获取Unionid="+usermessage.getUnionid());
            System.out.println("11111="+userFacade.wechatfind("13148913794"));
            if(usermessage.getUnionid()!=null) {
                if (wechatOAFacade.find(usermessage.getUnionid()) == null) {
                    System.out.println("1113");
                    wechatOAFacade.save(usermessage);
                }
                    //判断数据库是否存在账户
                    User  us = new User();//数据库校验USER有没用户
                    User user = new User();//新存用户的信息
                    us.setUserAccount(usermessage.getPhone());
                    System.out.println("22="+userFacade.wechatfind(usermessage.getPhone()+""));
                    String booleanS= userFacade.wechatfind(usermessage.getPhone()+"");

                    if(booleanS==null) {
                        user.setUserAccount("wx"+usermessage.getPhone());
                        user.setUserName(usermessage.getPhone());
                        user.setPhone(usermessage.getPhone());
                        user.setRoleName("charter");
                        user.setUserPassword(usermessage.getPhone());
                        System.out.println("保存USER");

                        try {
                            ShiroUserUtils.encryptPassword(user);
                            System.out.println("user="+user);
                            userFacade.init(user);
                            System.out.println("保存USER成功");
                        } catch (Exception e) {
                            e.printStackTrace();
                            state = false;
                        }

                    }





            }
            session.setAttribute("usermessage",usermessage);
        }
        map.put("state", state);
        map.put("Userid",usermessage.getPhone());

        return map;
    }


    @ResponseBody
    @RequestMapping(value="checkCode2")
    // @ModelAttribute
    public Map<String, Object> checkCode2(Usermessage usermessage1, HttpServletRequest request,  HttpSession session){



        Usermessage usermessage =(Usermessage) session.getAttribute("usermessage");


        System.out.println(usermessage);
        if(usermessage==null){
           usermessage = new Usermessage();
        }


        usermessage.setPhone(usermessage1.getPhone());
        usermessage.setCodeEncoder(usermessage1.getCodeEncoder());
        usermessage.setPhonecode(usermessage1.getPhonecode());

        //


       /* if (usermessage1.getPhone()!=null&&usermessage1.getPhone()!=""){

        }*/



     //   session.removeAttribute("usermessage");
        Map<String, Object> map = new HashMap<String, Object>();

     //   String code=usermessage.getUnionid();

        map=checkCode(usermessage,session);

        return  map;
    }

    @RequestMapping(value = "LoginAgain")
    public ModelAndView LoginAgain(SessionStatus status) {
        ModelAndView mav = new ModelAndView("login/login");
        status.setComplete();
        return mav;

    }


//设置用户名
public   Usermessage  setName(String code,Usermessage usermessage){

    WechatUtils wechatUtils = new WechatUtils();
    //获取openid 和access_token
    String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WechatUtils.getWeixinAppid() + "&secret=" + WechatUtils.getWeixinAppsecret() + "&code=" + code + "&grant_type=authorization_code ";
    String re = wechatUtils.httpRequest(url, "GET", null);
    JSONObject jsStr = JSONObject.parseObject(re);
    String access_token = jsStr.getString("access_token");
    String openid = jsStr.getString("openid");

    // 获取unionid
    url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN  ";
    re = wechatUtils.httpRequest(url, "GET", null);
    System.out.println("jsStr=" + jsStr);
    jsStr = JSONObject.parseObject(re);
    String unionid = jsStr.getString("unionid");

    if(usermessage.getUnionid()!=null&&usermessage.getUnionid()!=""){
        unionid=usermessage.getUnionid();
    }

    System.out.println("方法里面的unionid="+unionid);

  //  if(usermessage.getUnionid()!=null&usermessage.getUnionid()!="")
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
        usermessage.setPhone(map1.get("WECHAT_PHONE").toString());
        usermessage.setUnionid(map1.get("WECHAT_UNIONID").toString());
        usermessage.setOpenid(map1.get("WECHAT_OPENID").toString());
    }




    System.out.println("方法Uniusermessage="+usermessage.getUnionid());
    System.out.println("方法Openusermessage="+usermessage.getOpenid());

    return  usermessage;

}



}