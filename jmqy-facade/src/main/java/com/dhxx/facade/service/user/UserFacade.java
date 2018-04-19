package com.dhxx.facade.service.user;

import com.dhxx.facade.entity.user.User;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月07日
 * @version 1.01
 * 用户管理interface
 */
public interface UserFacade {
	
	Object list(User user);
	
	Object find(User user);
	
	Object save(User user);

	void init(User user);

	Object update(User user);
	
	void delete(User user);

    Object authInfo();

    String wechatfind(String s);

}
