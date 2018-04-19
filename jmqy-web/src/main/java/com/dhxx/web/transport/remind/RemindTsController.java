package com.dhxx.web.transport.remind;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.remind.Remind;
import com.dhxx.facade.service.remind.RemindFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;

/**
 * @author hanrs
 * @date 2017/9/22
 * @description
 * 运输方提醒通知管理
 */
@Controller
@RequestMapping("transport/remind")
@SuppressWarnings("unchecked")
public class RemindTsController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(RemindTsController.class);
	
    @Reference(protocol="dubbo")
    private RemindFacade remindFacade;
    
    //订单提醒
    @RequestMapping(value ="list")
    public String list(Remind remind, Model model, HttpServletRequest request){
    	ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    	remind.setUserId(shiroUser.id);
    	//提醒类型：0：支付提醒 1：执行提醒  2：删除提醒  3：积分过期  4：投诉提醒  5：付款提醒（信用额度）
    	//remind.setIds("1");
    	PageInfo<Remind> reminds = (PageInfo<Remind>) remindFacade.list(remind);
    	model.addAttribute("remind",remind);
        model.addAttribute("reminds", reminds);
    	return "transport/remind/list";
    }
}