package com.dhxx.service.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.service.biz.user.UserBiz;
/**
 * 
 * @author hanrs
 *
 */
@Service(protocol = {"dubbo"})
public class UserFacadeImpl implements UserFacade {
    
	private static Logger log = LoggerFactory.getLogger(UserFacadeImpl.class);
	
    @Autowired
    private UserBiz userBiz;

	@Override
	public Object list(User user) {
		log.debug("UserFacadeImpl.list()");
		return userBiz.list(user);
	}

	@Override
	public Object find(User user) {
		log.debug("UserFacadeImpl.find()");
		return userBiz.find(user);
	}

	@Override
	public Object save(User user) {
		log.debug("UserFacadeImpl.save()");
        userBiz.save(user);
		return user;
	}

    @Override
    public void init(User user) {
        userBiz.init(user);;
    }

    @Override
	public Object update(User user) {
        return userBiz.update(user);
	}
	
	@Override
	public void delete(User user) {
        userBiz.delete(user);
	}

    @Override
    public Object authInfo() {
        return null;
    }

    @Override
	public  String wechatfind(String s){return  userBiz.wechatfind(s);}
}
