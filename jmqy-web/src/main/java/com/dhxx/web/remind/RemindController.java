package com.dhxx.web.remind;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.remind.Remind;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.remind.RemindFacade;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.github.pagehelper.PageInfo;

/**
 * @author jhy
 * @date 2017/9/17
 * @description
 */
@Controller
@RequestMapping("remind")
public class RemindController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(RemindController.class);

    @Reference(protocol="dubbo")
    private OrderFacade orderFacade;

    @Reference(protocol="dubbo")
    private RemindFacade remindFacade;

    @RequestMapping(value = "order")
    public String charter(Order order, Model model, HttpServletRequest requst) {
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.remind(order);
        model.addAttribute("order",order);
        model.addAttribute("orders", orders);
        return "remind/order";
    }
    
    @RequestMapping(value = "list")
    public String list() {
    	ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    	Long companyType = shiroUser.companyType;
    	if (companyType == 1 || companyType == 2) {
    		return "redirect:/charter/order/remindList";
		}
    	if (companyType == 3) {
    		return "redirect:/transport/remind/list";
		}
        return "redirect:/index";
    }

    @ResponseBody
    @RequestMapping(value="remind")
    public Map<String, Object> remind(Remind remind) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            remindFacade.save(remind);
        } catch (Exception e) {
        	log.debug(e.getMessage());
            state = false;
        }
        map.put("state", state);
        return map;
    }
    
    @ResponseBody
    @RequestMapping(value="update")
    public Map<String, Object> update(Remind remind) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            remindFacade.update(remind);
        } catch (Exception e) {
        	log.debug(e.getMessage());
            state = false;
        }
        map.put("state", state);
        return map;
    }
    
    @ResponseBody
    @RequestMapping(value="statistics")
    public Integer statistics(Remind remind) {
        try {
        	ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        	remind.setUserId(shiroUser.id);
        	remind.setStatus(0);
        	return (Integer) remindFacade.statistics(remind);
        } catch (Exception e) {
        	log.debug(e.getMessage());
            return 0;
        }
    }

}

