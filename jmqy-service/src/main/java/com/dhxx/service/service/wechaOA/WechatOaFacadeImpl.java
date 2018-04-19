package com.dhxx.service.service.wechaOA;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.weappOA.Usermessage;
import com.dhxx.facade.service.weChatOA.WechatOAFacade;
import com.dhxx.service.biz.wechatOA.WechatOa;
import com.dhxx.service.mapper.wechatOA.WechatOaMapper;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Map;

/**
 * Created by Administrator on 2017/11/17.
 */

@Service(protocol = {"dubbo"})
public class WechatOaFacadeImpl  implements WechatOAFacade{

    @Autowired
    private WechatOa wechatOa;

    @Override
    public Object save(Usermessage usermessage) {
       return wechatOa.save(usermessage);
    }
  //  public String  find(Usermessage usermessage){return }
    @Override
    public  String find(String s){return wechatOa.find(s);}

    @Override
    public Map<String,Object> findUserMessage(String s){return wechatOa.findUserMessage(s);}
}
