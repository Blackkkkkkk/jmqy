package com.dhxx.service.biz.wechatOA;

import com.dhxx.facade.entity.weappOA.Usermessage;
import com.dhxx.service.mapper.wechatOA.WechatOaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/17.
 */
@Service
@Transactional
public class WechatOa {

    @Autowired
    private WechatOaMapper wechatOaMapper;
   // private WechatOaMapper test;

    public Long  save(Usermessage usermessage){return  wechatOaMapper.saveUserMessage(usermessage);}

  //  public String find(String s){return  wechatOaMapper.find(s);}
    public String find(String s){return  wechatOaMapper.find(s);}

    public Map<String,Object> findUserMessage(String s){return  wechatOaMapper.findUserMessage(s);}
}
