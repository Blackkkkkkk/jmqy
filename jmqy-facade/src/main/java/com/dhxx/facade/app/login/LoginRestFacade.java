package com.dhxx.facade.app.login;

import javax.servlet.http.HttpServletRequest;

import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.entity.user.UserInfo;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2018年01月31日
 * @version 1.01
 *
 */
public interface LoginRestFacade {

    Object doLogin(UserInfo user, HttpServletRequest requst);
    
    Object sms(User user, HttpServletRequest requst);
    
}
