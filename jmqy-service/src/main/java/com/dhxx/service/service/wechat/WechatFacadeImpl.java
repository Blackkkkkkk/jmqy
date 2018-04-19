package com.dhxx.service.service.wechat;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.common.wechat.WechatUtils;
import com.dhxx.facade.service.wechat.WechatFacade;

@Service(protocol = {"dubbo"})
public class WechatFacadeImpl implements WechatFacade{

	@Override
	public Object acceptMessage(String xmlMsg){
		return WechatUtils.acceptMessage(xmlMsg);
	}
	
	@Override
	public Object createMenu() {
		return WechatUtils.createMenu();
	}

	@Override
	public Object deleteMenu() {
		return WechatUtils.deleteMenu();
	}

}
