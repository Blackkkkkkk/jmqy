package com.dhxx.service.app.authorization.manager.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dhxx.facade.entity.user.UserInfo;
import com.dhxx.service.app.authorization.manager.TokenManager;

/**
 * 通过Redis存储和验证token的实现类
 * 
 * @see com.scienjus.authorization.manager.TokenManager
 * @author ScienJus
 * @date 2015/7/31.
 */
@Component
public class RedisTokenManager implements TokenManager {

    private static final String ACCESS = "access:";
    private static final String USER = "user:";
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        // 泛型设置成Long后必须更改对应的序列化方案
        redisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    @SuppressWarnings("unused")
    public Long findTokenByUser(UserInfo userInfo) {
        String token = (String) redisTemplate.boundValueOps(USER + userInfo.getPhone()).get();
        Long time = redisTemplate.boundValueOps(ACCESS + userInfo.getToken()).getExpire();
        return time;
    }
    
    public boolean deleteByUser(UserInfo userInfo) {
        String token = (String) redisTemplate.boundValueOps(USER + userInfo.getPhone()).get();
        System.out.println(token);
        if (!StringUtils.isEmpty(token)) {
        	redisTemplate.delete(token);
        	redisTemplate.delete(USER + userInfo.getPhone());
        	return true;
		} 
        return false;
    }

    public void createToken(UserInfo userInfo) throws Exception {
    	deleteByUser(userInfo);
        redisTemplate.boundValueOps(ACCESS + userInfo.getToken()).set(userInfo, 7, TimeUnit.DAYS);
        redisTemplate.boundValueOps(USER + userInfo.getPhone()).set(ACCESS + userInfo.getToken(), 7, TimeUnit.DAYS);

    }

    public UserInfo getToken(String token) {
        if (token == null || token.length() == 0) {
            return null;
        }

        UserInfo userInfo = (UserInfo) redisTemplate.boundValueOps(ACCESS + token).get();
        return userInfo;
    }

    public boolean checkToken(UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        }
        // 如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redisTemplate.boundValueOps(ACCESS + userInfo.getToken()).expire(7, TimeUnit.DAYS);
        return true;
    }

    public void deleteToken(String token) {
        redisTemplate.delete(ACCESS + token);
    }

}
