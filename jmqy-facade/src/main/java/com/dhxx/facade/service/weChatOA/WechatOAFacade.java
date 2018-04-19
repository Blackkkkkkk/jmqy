package com.dhxx.facade.service.weChatOA;

import com.dhxx.facade.entity.weappOA.Usermessage;

import java.util.Map;

/**
 * Created by xiaoqiang on 2017/11/19.
 */
public interface WechatOAFacade {
    Object save(Usermessage usermessage);

    String find(String s);

    Map<String,Object> findUserMessage(String s);
}
