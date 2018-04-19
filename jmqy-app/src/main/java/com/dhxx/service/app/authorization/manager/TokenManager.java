package com.dhxx.service.app.authorization.manager;

import com.dhxx.facade.entity.user.UserInfo;

/**
 * 对Token进行操作的接口
 * @author jhy
 * @date 2016/9/25.
 */
public interface TokenManager {

    /**
     * 创建一个token关联上指定用户
     * @param userId 指定用户的id
     * @return 生成的token
     */
    public void createToken(UserInfo userInfo) throws Exception;

    /**
     * 检查token是否有效
     * @param model token
     * @return 是否有效
     */
    public boolean checkToken(UserInfo model);

    /**
     * 从字符串中解析token
     * @param authentication 加密后的字符串
     * @return
     */
    public UserInfo getToken(String authentication);

    /**
     * 清除token
     * @param userId 登录用户的id
     */
    public void deleteToken(String token);
    
    
    /**
     * 清除token
     * @param model token
     */
    public boolean deleteByUser(UserInfo model);

}
