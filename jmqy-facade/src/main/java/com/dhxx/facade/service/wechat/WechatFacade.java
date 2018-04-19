package com.dhxx.facade.service.wechat;

/**
 * 
 * @author hanrs
 *
 */
public interface WechatFacade {
	
	Object acceptMessage(String xmlMsg);

	Object deleteMenu();

	Object createMenu();
	
	
    
}
