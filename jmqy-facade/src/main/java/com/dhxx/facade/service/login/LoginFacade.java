package com.dhxx.facade.service.login;

import com.dhxx.facade.entity.user.User;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月07日
 * @version 1.01
 * 登陆管理interface
 */
public interface LoginFacade {
    
    Object login(User user);
    
}
