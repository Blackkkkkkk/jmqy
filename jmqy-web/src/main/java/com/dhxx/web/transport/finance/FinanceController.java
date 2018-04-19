package com.dhxx.web.transport.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.service.order.OrderFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.balance.Balance;
import com.dhxx.facade.entity.bill.Bill;
import com.dhxx.facade.service.balance.BalanceFacade;
import com.dhxx.facade.service.bill.BillFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月18日
 * @version 1.01
 * 运输方财务管理
 */
@Controller
@RequestMapping("transport/finance")
@SuppressWarnings("unchecked")
public class FinanceController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(FinanceController.class);
	
	@Reference(protocol="dubbo")
    private BalanceFacade balanceFacade;
	
	@Reference(protocol="dubbo")
    private BillFacade billFacade;

	@Reference(protocol="dubbo")
	private OrderFacade orderFacade;
    
	//我的账户-余额查询
	@RequestMapping(value = "balance")
    public String balance(@RequestParam(value="flag",defaultValue="0") int flag, Bill bill,  Model model, HttpServletRequest requst) {
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		Balance balance = new Balance();
		balance.setUserId(shiroUser.id);
		List<Balance> balances = (List<Balance>) balanceFacade.find(balance);
		if(!CollectionUtils.isEmpty(balances)){
			balance = balances.get(0);
		}
		bill.setUserId(shiroUser.id);
		PageInfo<Bill> bills = (PageInfo<Bill>) billFacade.list(bill);
		model.addAttribute("flag", flag);
        model.addAttribute("bill", bill);
        model.addAttribute("bills", bills);
    	model.addAttribute("balance", balance);
        return "transport/finance/balance";
    }

	//订单结算查询
	@RequestMapping(value = "settlement")
	public String settlement(Order order,Model model){

		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

		if(shiroUser.roleName.indexOf("sys")>-1){
		}else {
			order.setTransporterCode(shiroUser.companyCode);
		}
	//	order.setTransporterCode(shiroUser.companyCode);
		if(order.getSettlement() == null || order.getSettlement() == 3 ){
			order.setSettlement(3l);
			order.setSettlementList("1,2");
		}
		if(order.getSettlement() ==1 || order.getSettlement()==2){
			order.setSettlementList(null);
		}

		order.setStatus(3l);

		PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);


		model.addAttribute("order",order);
		model.addAttribute("orders", orders);

		if(shiroUser.roleName.indexOf("sys")>-1){
			return "sys/trasportSettlementList";
		}else {
			return "transport/finance/settlementList";
		}
	}
		
}
