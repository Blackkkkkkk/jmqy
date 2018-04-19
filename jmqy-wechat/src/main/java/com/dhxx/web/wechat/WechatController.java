package com.dhxx.web.wechat;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.service.wechat.WechatFacade;

@Controller
@RequestMapping("wechat")
public class WechatController {
	
	private static Logger log = LoggerFactory.getLogger(WechatController.class);
	
	 @Reference(protocol="dubbo")
	 private WechatFacade wechatFacade;
	
	@ResponseBody
	@RequestMapping(value = "token", method = RequestMethod.GET)
	public String tokenByGet(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		log.debug(signature);
		log.debug(timestamp);
		log.debug(nonce);
		return echostr;
	}
	
	@ResponseBody
	@RequestMapping(value = "createMenu", method = RequestMethod.GET)
	public Object createMenu(HttpServletRequest request) {
		return wechatFacade.createMenu();
	}
	
	@ResponseBody
	@RequestMapping(value = "deleteMenu", method = RequestMethod.GET)
	public Object deleteMenu(HttpServletRequest request) {
		return wechatFacade.deleteMenu();
	}
	
	@ResponseBody
	@RequestMapping(value = "token", method = RequestMethod.POST)
	public void tokenByPost(HttpServletRequest request, HttpServletResponse response) {
		log.debug("消息管理");
		try {
			StringBuilder xmlMsg = new StringBuilder();
			ServletInputStream in = request.getInputStream();
			byte[] b = new byte[4096];
			for (int n; (n = in.read(b)) != -1;) {
				xmlMsg.append(new String(b, 0, n, "UTF-8"));
			}
			String outMsg = (String) wechatFacade.acceptMessage(xmlMsg.toString());
			if(outMsg != null){
				response.getWriter().write(outMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
