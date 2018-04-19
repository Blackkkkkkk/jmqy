package com.dhxx.web.charter.evaluate;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.charter.evaluate.Evaluate;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.service.charter.evaluate.EvaluateFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;

/**
 * 订单评价
 * @author dingbin
 * @description  
 * 
 */
@Controller
@RequestMapping("charter/evaluate")
public class EvaluateController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(EvaluateController.class);
	
	@Reference(protocol="dubbo")
    private EvaluateFacade evaluateFacade;
	
	@Reference(protocol="dubbo")
    private OrderFacade orderFacade;
	
	//保持评价信息
	@ResponseBody
    @RequestMapping(value="saveEvaluate",method = RequestMethod.POST)
    public Map<String, Object> saveEvaluate(Evaluate evaluate,Model model,HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>(); 
		boolean state = true;
		try {
    		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    		evaluate.setUserId(shiroUser.id);//用户编号
    		evaluate.setCreateTime(new Date());
    		Evaluate temp = (Evaluate)evaluateFacade.save(evaluate);
    		
    		//因mapper调整，需先查询
    		List<Evaluate> evaluateList  = (List<Evaluate>)evaluateFacade.find(evaluate);
    		if(!CollectionUtils.isEmpty(evaluateList)){
    			Order order = new Order();
        		order.setOrderCode(evaluate.getOrderCode());
        		order.setOrderPlacer(shiroUser.id+"");
        		order.setEvaluateId(evaluateList.get(0).getId());
        		orderFacade.update(order);
    		}
		} catch (Exception e) {
			state = false;
			e.printStackTrace();
	    	log.error(e.getMessage());
		}
		map.put("state", state);
    	return map;
    }
	
}