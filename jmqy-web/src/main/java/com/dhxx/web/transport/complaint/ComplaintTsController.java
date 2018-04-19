package com.dhxx.web.transport.complaint;

import java.util.List;

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
import com.dhxx.facade.entity.charter.complaint.Complaint;
import com.dhxx.facade.entity.charter.evaluate.Evaluate;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.service.charter.complaint.ComplaintFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月20日
 * @version 1.01
 * 运输方投诉管理
 */
@Controller
@RequestMapping("transport/complaint")
@SuppressWarnings("unchecked")
public class ComplaintTsController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(ComplaintTsController.class);
	
	@Reference(protocol="dubbo")
    private ComplaintFacade complaintFacade;
    
	//运输方收到的投诉信息
	@RequestMapping(value="list")
    public String list(Complaint complaint,Model model,HttpServletRequest request){
    	ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    	complaint.setTransporterCode(shiroUser.companyCode);
    	PageInfo<Complaint> complaints = (PageInfo<Complaint>) complaintFacade.list(complaint);
        model.addAttribute("complaint", complaint);
        model.addAttribute("complaints", complaints);
		System.out.println(complaints.getTotal());
		return "transport/complaint/list";
    }
	
	//投诉信息详情
	@RequestMapping(value="detail")
    public String detail(Complaint complaint,Model model,HttpServletRequest request){
    	List<Complaint> complaints = (List<Complaint>) complaintFacade.find(complaint);
    	if(!CollectionUtils.isEmpty(complaints)){
    		complaint = complaints.get(0);
    	}
        model.addAttribute("complaint", complaint);
        return "transport/complaint/detail";
    }
	
	//拒绝未接订单
    @ResponseBody
    @RequestMapping(value = "reply", method = RequestMethod.POST)
    public Integer reply(Complaint complaint, Model model) {
 	   try{
 		   ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
 		   complaint.setReplyUserId(shiroUser.id);
 		   complaintFacade.update(complaint);
        } catch (Exception e) {
     	   log.error(e.getMessage());
     	   return -1;
        }
 	   return 0;
    }
	
	
}