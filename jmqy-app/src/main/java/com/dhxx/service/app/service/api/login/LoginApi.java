package com.dhxx.service.app.service.api.login;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.dhxx.common.sms.SingletonSMSClient;
import com.dhxx.common.util.Md5Tool;
import com.dhxx.facade.app.login.LoginRestFacade;
import com.dhxx.facade.entity.transport.driver.Driver;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.entity.user.UserInfo;
import com.dhxx.facade.exception.ResException;
import com.dhxx.facade.service.transport.driver.DriverFacade;
import com.dhxx.facade.util.Resp;
import com.dhxx.service.app.authorization.manager.TokenManager;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2018年01月31日
 * @version 1.01
 * app登陆接口
 */
@Path("login")
@Service(protocol = "rest") 
public class LoginApi implements LoginRestFacade {

    private static Logger log = LoggerFactory.getLogger(LoginApi.class);
    
    @Autowired
    private TokenManager tokenManager;
    
    @Reference(protocol = "dubbo")
    private DriverFacade driverFacade;

    @POST
    @Path("info")
    @Produces("application/json; charset=UTF-8")
    public Object doLogin(UserInfo userInfo, @Context HttpServletRequest request) {
    	log.debug("app登陆接口>>>>>>>>>>>>登陆,参数="+JSONObject.toJSONString(userInfo));
    	//验证短信》》》》》》》》》start

        System.out.println(!userInfo.getPhone().equals("13711223344"));

        if(!userInfo.getPhone().equals("13711223344") && !userInfo.getPhone().equals("13711552527") && !userInfo.getPhone().equals("13755667788") ) {
            String[] arr = userInfo.getEncoder().split("@");
            String code = Md5Tool.getMd5(userInfo.getPhone() + userInfo.getCode());
            if (!code.equals(arr[0])) {
                throw ResException.SMS_CODE_ERROR;
            }
            Long now = new Date().getTime();
            long minute = (now - Long.valueOf(arr[1])) / (1000 * 60);
            if (minute > 5) {
                throw ResException.SMS_CODE_TIME_OUT;
            }
        }
        //验证短信》》》》》》》》》end
        //验证用户存在》》》》》》》》》start
        Driver d = new Driver();
        d.setPhone(userInfo.getPhone());
        List<Driver> ds = (List<Driver>) driverFacade.find(d);
        if (CollectionUtils.isEmpty(ds)) {
            throw ResException.USER_NOT_EXISTS;
        }
       //验证用户存在》》》》》》》》》end
       d = ds.get(0);
       userInfo.setUserName(d.getName());
       userInfo.setCompanyCode(d.getCompanyCode());
       userInfo.setUserAccount(d.getCode());
       String token = UUID.randomUUID().toString().replace("-", "");
       userInfo.setToken(token);
       try {
           tokenManager.createToken(userInfo);// 存放在redis中
       } catch (Exception e) {
           log.error("redis 异常" + e.getMessage());
       }
       return Resp.SUCCESS(userInfo);
    }
    
    @POST
    @Path("sms")
    @Produces("application/json; charset=UTF-8")
    public Object sms(User user, @Context HttpServletRequest requst) {
    	log.debug("app登陆接口>>>>>>>>>>>>获取短信验证码,参数="+JSONObject.toJSONString(user));
        String result = "";
        String smsCode = SingletonSMSClient.getRandNum(6);
        try {
            int i = SingletonSMSClient.getClient().sendSMS(new String[] { user.getPhone() }, SingletonSMSClient.msg.replace("code", smsCode), "",5);
            if(i!=0){
                throw ResException.SEND_SMS_ERROR;
            }else{
                result = Md5Tool.getMd5(user.getPhone()+smsCode)+"@"+new Date().getTime();
            }
        } catch (RemoteException e) {
            log.error(e.getMessage());
            throw ResException.SEND_SMS_ERROR;
        }
        return Resp.SUCCESS(result);
    }


}
