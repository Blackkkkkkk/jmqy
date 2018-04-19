package com.dhxx.web.charter.complaint;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.charter.complaint.Complaint;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.service.charter.complaint.ComplaintFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;

/**
 * 订单投诉
 * @author dingbin
 * @description  
 * 
 */
@Controller
@RequestMapping("charter/complaint")
public class ComplaintController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(ComplaintController.class);
	
	@Reference(protocol="dubbo")
    private ComplaintFacade complaintFacade;
	
	@Reference(protocol="dubbo")
    private OrderFacade orderFacade;
    
	//保持投诉信息
	@ResponseBody
    @RequestMapping(value="saveComplaint",method = RequestMethod.POST)
    public Map<String,Object> saveComplaint (Complaint complaint,Model model,HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>(); 
    	boolean state = true;
    	 try {
    		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    		complaint.setUserId(shiroUser.id);//用户编号
    		complaint.setCreateTime(new Date());
    		Complaint temp = (Complaint)complaintFacade.save(complaint);
    		
    		//设置对应订单投诉记录
    		Order order = new Order();
    		order.setOrderCode(complaint.getOrderCode());
    		order.setOrderPlacer(shiroUser.id+"");
    		order.setComplaintId(temp.getId());
    		orderFacade.update(order);
		} catch (Exception e) {
			state = false;
	    	log.error(e.getMessage());
		}
    	 map.put("state", state);
    	return map;
    }
}