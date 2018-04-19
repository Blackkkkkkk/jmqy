package com.dhxx.web.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dhxx.common.constant.Constant;
import com.dhxx.facade.entity.user.User;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;


@Component
@Transactional
public class ShiroUserUtils {

	//取得当前用户
	public static ShiroUser getShiroUser(){
		return (ShiroUser)SecurityUtils.getSubject().getPrincipal();
	}
		
	//取得当前用户的id
	public static Long getUserId(){
		ShiroUser shiroUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		return shiroUser.id;
	}
	
	//取得当前用户名
	public static String getName(){
		ShiroUser shiroUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		return shiroUser.userName;
	}
	
	//取得当前用户的登录名
	public static String getLoginName(){
		ShiroUser shiroUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		return shiroUser.userAccount;
	}
		
	public static void encryptPassword(User user) {  
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        user.setSalt(randomNumberGenerator.nextBytes().toHex());  
        String credentialsSalt = user.getUserAccount()+user.getSalt();
        String newPassword = new SimpleHash(Constant.HASH_ALGORITHM, user.getUserPassword(),
                        ByteSource.Util.bytes(credentialsSalt), Constant.HASH_INTERATIONS).toHex();  
        user.setUserPassword(newPassword);  
    }
	
	public static void checkPassword(User user) {  
        String credentialsSalt = user.getUserAccount()+user.getSalt();
        String newPassword = new SimpleHash(Constant.HASH_ALGORITHM, user.getUserPassword(),
                        ByteSource.Util.bytes(credentialsSalt), Constant.HASH_INTERATIONS).toHex();  
        user.setUserPassword(newPassword);  
    }
	
}
