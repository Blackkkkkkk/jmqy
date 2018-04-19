package com.dhxx.service.mapper.wechatOA;

import com.dhxx.facade.entity.weappOA.Usermessage;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/17.
 */
public interface WechatOaMapper {

    Long  saveUserMessage(Usermessage usermessage); //保存用户信息

    String  find(String s);                         //查找是否存在用户

    Map<String,Object> findUserMessage(String s);                //查看历史订单的时候，如果未有usermessage信息，就用openid去数据库查找

}
