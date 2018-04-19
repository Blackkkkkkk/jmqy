package com.dhxx.web.charter.invoice;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.common.util.DataUtils;
import com.dhxx.facade.entity.charter.invoice.Address;
import com.dhxx.facade.entity.charter.invoice.Invoice;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.service.charter.invoice.AddressFacade;
import com.dhxx.facade.service.charter.invoice.InvoiceFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单发票
 * @author dingbin
 * @description  
 * 
 */
@Controller
@RequestMapping("charter/invoice")
public class InvoiceController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(InvoiceController.class);
	
    @Reference(protocol="dubbo")
    private InvoiceFacade invoiceFacade;
    @Reference(protocol="dubbo")
    private OrderFacade orderFacade;

	@Reference(protocol="dubbo")
	private AddressFacade addressFacade;

    //保持发票信息
    @ResponseBody
    @RequestMapping(value="saveInvoice",method = RequestMethod.POST)
    public Map<String, Object> saveInvoice(Invoice invoice,Model model,HttpServletRequest request) throws UnsupportedEncodingException {
    	Map<String, Object> map = new HashMap<String, Object>(); 
    	boolean state = true;
    	 try {
    		 ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    		 invoice.setUserId(shiroUser.id);//用户编号
			 invoice.setOrderStatus("1");
    		 invoice.setCreateTime(new Date());
    		 Invoice temp =(Invoice)invoiceFacade.save(invoice);
    		 
    		//设置对应订单投诉记录
     		Order order = new Order();
     		order.setOrderCode(invoice.getOrderCode());
     		order.setOrderPlacer(shiroUser.id+"");
     		order.setInvoiceId(temp.getId());
     		orderFacade.update(order);
		} catch (Exception e) {
			state = false;
	    	log.error(e.getMessage());
		}
    	map.put("state", state);
    	return map;
    }



	//保存发票信息模板
	@ResponseBody
	@RequestMapping(value="saveInvoiceTemplate",method = RequestMethod.POST)
	public Map<String, Object> saveInvoiceTemplate(Invoice invoice, Model model, HttpServletRequest request,
												   @RequestParam(value = "actionType" ,required = false) String actionType) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean state = true;
		try {
			ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
			if(actionType.equals("2")) {
				invoice.setUserId(shiroUser.id);//用户编号
				invoiceFacade.saveInvoiceTem(invoice);
			}else{
				invoice.setUserId(shiroUser.id);//用户编号
				invoiceFacade.delInvoiceTem(invoice);
				invoiceFacade.saveInvoiceTem(invoice);
			}

		} catch (Exception e) {
			state = false;
			log.error(e.getMessage());
		}
		map.put("state", state);
		return map;
	}





	//查看发票信息
	@RequestMapping(value = "detil")
	public String invoiceList(Invoice invoice, Model model, HttpServletRequest request) {
    	Map<String ,Object> map = new HashMap<String ,Object>();
		map = invoiceFacade.find(invoice);
		model.addAttribute("invoice",map);
	//	PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
	//	model.addAttribute("order",order);
	//	model.addAttribute("orders", orders);
		return "charter/order/invoice_detil";
	}



}