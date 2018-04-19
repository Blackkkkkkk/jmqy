package com.dhxx.web.charter.pay;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhxx.common.sms.SingletonSMSClient;
import com.dhxx.common.wechat.WechatUtils;
import com.dhxx.facade.entity.balance.BalanceDetilBill;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.entity.remind.Remind;
import com.dhxx.facade.entity.transport.order.BackOrder;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.balance.BalanceDetilBillFacade;
import com.dhxx.facade.service.credit.CreditDetilBillFacade;
import com.dhxx.facade.service.credit.CreditFacade;
import com.dhxx.facade.service.publicCode.wechatpay.PublicCodeWechatPay;
import com.dhxx.facade.service.remind.RemindFacade;
import com.dhxx.facade.service.transport.order.BackOrderFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.github.pagehelper.PageInfo;
import org.jboss.resteasy.annotations.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.common.util.DataUtils;
import com.dhxx.facade.entity.bill.Bill;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.service.bill.BillFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.dhxx.web.utils.MessagePush;
import com.dhxx.web.utils.SMSNotifyUtil;
import com.dhxx.web.wechat.PayResultNotice;
import com.dhxx.web.wechat.UniteOrder;
import com.dhxx.web.wechat.UniteOrderResult;
import com.dhxx.web.wechat.WeixinMpUtils;
import com.lijing.wechatpay.impl.PayImpl;
import com.lijing.wechatpay.util.io.IOUtils;

import com.dhxx.facade.service.publicCode.wechatpay.PublicCodeWechatPay;






/**
 * 微信支付接口
 * */
@Controller
@RequestMapping(value="charter/wechat")
@SuppressWarnings("unchecked")
public class WeChatPayController {
	
	private static Logger log = LoggerFactory.getLogger(WeChatPayController.class);
	
	@Reference(protocol="dubbo")
    private OrderFacade orderFacade;
	
	@Reference(protocol="dubbo")
	private BillFacade billFacade;

	@Reference(protocol="dubbo")
	private CreditFacade creditFacade;


	@Reference(protocol="dubbo")
	private RemindFacade remindFacade;

	@Reference(protocol="dubbo")
	private UserFacade userFacade;

	@Reference(protocol="dubbo")
	private BackOrderFacade backOrderFacade;

	@Reference(protocol="dubbo")
	private PublicCodeWechatPay publicCodeWechatPay;

	@Reference(protocol="dubbo")
	private BalanceDetilBillFacade balanceDetilBillFacade;

	@Reference(protocol="dubbo")
	private CreditDetilBillFacade creditDetilBillFacade;

	@Autowired
	private SMSNotifyUtil sMSNotifyUtil;
	
	private static String ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder" ;

	//支付状态
	private  String re =null;
	//返回支付二维码的地址；
	private  String Src = null;

	//新的订单支付方式
	@RequestMapping(value = "newscanPay", method = RequestMethod.GET)
	public String newscanPay(Order order, Model model, HttpServletRequest requst,
							 @RequestParam(value =  "actiontype") String actiontype ) {
		System.out.println(!StringUtils.isEmpty(order.getOrderCode()));
		if(!StringUtils.isEmpty(order.getBigOrderCode()) || !StringUtils.isEmpty(order.getOrderCode())){
			List<Order> orders=(List<Order>)orderFacade.find(order);
			if(!CollectionUtils.isEmpty(orders)){
				order = orders.get(0);
			}else{
			}
		}else {
			//额度支付没有订单编码，设置个新的订单编码
			order.setBigOrderCode("CL-"+DataUtils.getCurrentTimeStr());
		}

		Src = ReturnRSrc(order.getBigOrderCode(),order.getPrices()+"");

		model.addAttribute("src",Src);
		model.addAttribute("code",order.getBigOrderCode());
		model.addAttribute("prices",order.getPrices());
		model.addAttribute("actiontype",actiontype);
	//	model.addAttribute("ordercode",order.getOrderCode());



		return "charter/pay/wechatPay";
	}

	//判断是否支付成功
	@ResponseBody
	@RequestMapping(value ="NewPayResult",method = RequestMethod.POST)
	public Map<String, Object> NewPayResult(Order order,Model model ,HttpServletRequest requst) {
		Map<String, Object> map = new HashMap<String, Object>();


		ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

		String state=publicCodeWechatPay.NewPayResult(order,shiroUser.id,Double.parseDouble(order.getPrices()),null);


		map.put("state",state);

		return map;
	}


	//额度结算
	@ResponseBody
	@RequestMapping(value = "creDitPay", method = RequestMethod.POST)
	public Map<String, Object> creDitPay(Order order,Model model ,HttpServletRequest requst) {
		Map<String, Object> map = new HashMap<String, Object>();

		re =ReturnResult(order.getBigOrderCode());

		if(!re.equals("F")){

			//TODO:未做订单金额效验
			ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();


			//额度支付没有订单编码，设置个新的订单编码
			order.setBigOrderCode("CL-"+DataUtils.getCurrentTimeStr());


			Bill bill = new Bill();
			bill.setBillType("0");//包车方订单支付

			Double amount = Double.parseDouble(order.getPrices()+"");
			BigDecimal bigDecimal = new BigDecimal(amount);
			double money = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			bill.setMoney(money+"");//金额
			bill.setTradeMode("2");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联
			bill.setOrderCode(order.getBigOrderCode());//大订单编号
			bill.setThreeOrderNo(order.getBigOrderCode());//第三方订单编号,订单号就是支付接口的订单号
			bill.setUserId(Long.parseLong(shiroUser.id+""));//使用ID
			bill.setCompanyCode(shiroUser.companyCode);
			billFacade.save(bill);




			Credit credit = new Credit();

			User user = new User();
			user.setCompanyCode(shiroUser.companyCode);
			user.setType(1);

			List<User> users=(List<User>)userFacade.find(user);
			UserId:
			for (int i = 0; i <users.size() ; i++) {
				user = users.get(i);
				credit.setUserId(user.getId());
				credit = (Credit) creditFacade.findOne(credit);
				if(credit != null){
                        break UserId;
				}

			}

			String balanceBefore = credit.getStockCredit()+"";  // 结算之前
			String balanceAfter = credit.getTotalCredit()+"";   // 结算总额度
			String balance = order.getPrices()+"";        //调整额

			//支付完金额，变更额度状态
			credit.setConsumeCredit(0.0);
			credit.setStockCredit(credit.getTotalCredit());
			creditFacade.update(credit);


			// 更新费用结算的订单状态
			Order or = new Order();
			or.setCharterCode(shiroUser.companyCode);
			//or.setOrderPlacer(shiroUser.id+"");
			//or.setStatus(3L);//已完成
			or.setPaymentStatus(1L);//已支付
			orderFacade.updateCreDitPay(or);



			//保存信用额度流水情况表
			CreditDetilBill creditDetilBill = new CreditDetilBill();

			creditDetilBill.setUserId(shiroUser.id);
			creditDetilBill.setCompanyCode(shiroUser.companyCode);
			creditDetilBill.setCredit(balance);
			creditDetilBill.setCreditAfter(balanceAfter);
			creditDetilBill.setCreditBefore(balanceBefore);
			creditDetilBill.setCreateDate(new Date());
			creditDetilBill.setType("2");
			creditDetilBill.setPayMode("2");
			creditDetilBill.setBigOrderCode(order.getBigOrderCode());

			creditDetilBillFacade.save(creditDetilBill);


			map.put("state","success");
		}
		else {
			map.put("state","fail");
		}


		return map;
	}




    // 费用结算
    @ResponseBody
	@RequestMapping(value = "costPay", method = RequestMethod.POST)
	public Map<String, Object> costPay(Order order,Model model ,HttpServletRequest requst) {
		Map<String, Object> map = new HashMap<String, Object>();

		re =ReturnResult(order.getBigOrderCode());

		if(!re.equals("F")){

			//TODO:未做订单金额效验
			ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();




			Bill bill = new Bill();
			bill.setBillType("0");//包车方订单支付

			Double amount = Double.parseDouble(order.getPrices()+"");
			BigDecimal bigDecimal = new BigDecimal(amount);
			double money = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			bill.setMoney(money+"");//金额
			bill.setTradeMode("2");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联
			bill.setOrderCode(order.getBigOrderCode());//大订单编号
			bill.setThreeOrderNo(order.getBigOrderCode());//第三方订单编号,订单号就是支付接口的订单号
			bill.setUserId(Long.parseLong(shiroUser.id+""));//使用ID
			bill.setCompanyCode(shiroUser.companyCode);
			billFacade.save(bill);


			Credit credit = new Credit();

			User user = new User();
			user.setCompanyCode(shiroUser.companyCode);
			user.setType(1);

			List<User> users=(List<User>)userFacade.find(user);
			UserId:
			for (int i = 0; i <users.size() ; i++) {
				user = users.get(i);
				credit.setUserId(user.getId());
				credit = (Credit) creditFacade.findOne(credit);
				if(credit != null){
					break UserId;
				}

			}

			String balanceBefore = credit.getStockCredit()+"";  //调整前
			String balance = order.getPrices()+"";        //调整额

			//支付完金额，变更额度状态

			Double Finallymoney = Double.parseDouble(credit.getStockCredit()+"") + Double.parseDouble(order.getPrices()+"");
			credit.setStockCredit(Finallymoney);

			String balanceAfter =Finallymoney+"";   //调整后
			credit.setConsumeCredit(Double.parseDouble(credit.getTotalCredit()+"")-Finallymoney);
			creditFacade.update(credit);

			order.setPaymentStatus(2L);//未支付
			orderFacade.update(order);


			//保存信用额度流水情况表

			CreditDetilBill creditDetilBill = new CreditDetilBill();

			creditDetilBill.setUserId(shiroUser.id);
			creditDetilBill.setCompanyCode(shiroUser.companyCode);
			creditDetilBill.setCredit(balance);
			creditDetilBill.setCreditAfter(balanceAfter);
			creditDetilBill.setCreditBefore(balanceBefore);
			creditDetilBill.setCreateDate(new Date());
			creditDetilBill.setType("2");
			creditDetilBill.setPayMode("2");
			creditDetilBill.setBigOrderCode(order.getBigOrderCode());

			creditDetilBillFacade.save(creditDetilBill);

			map.put("state","success");
		}
		else {
			map.put("state","fail");
		}

		System.out.println("11="+map.get("state"));

		return map;
	}

	//生成支付二维码

	public  String ReturnRSrc( String BigOrderCode,String Amount) {


		String code = BigOrderCode;
		String amount = Amount;
		amount = "0.01";

		String url = "http://219.130.135.53:8090/WXImage.ASPX?orderid=" + code + "&size=200&money=" + amount;
		//String url = "http://219.130.135.53:8090/WXImage.ASPX?orderid=" + code + "&size=200&money=100";
		String res = null;
		try {
			String html = getSrc.getOneHtml(url);
			res = getSrc.match(html, "img", "src");

		} catch (Exception e) {

		}


		return res;
	}

   //请求接口并返回支付状态
   //TODO 未做金额支付判断
	public  String ReturnResult( String BigOrderCode){
		//请求端口查询是否支付成功
		WechatUtils wechatUtils = new WechatUtils();
		String url = "http://mobile.jmqyjt.com/Interface/PayStateall.aspx?order="+BigOrderCode;
		String re=wechatUtils.httpRequest(url,"GET",null);

		re.trim();

		return  re;
	}

	//订单支付
	@RequestMapping(value = "scanPay", method = RequestMethod.GET)
	public String scanPay(Order order,Model model, HttpServletRequest requst) {
		if(!StringUtils.isEmpty(order.getBigOrderCode())){
    		List<Order> orders=(List<Order>)orderFacade.find(order);
    		if(!CollectionUtils.isEmpty(orders)){
    			order = orders.get(0);
    		}
    	}
		String code = order.getBigOrderCode();
		String attach = "orderPay,"+ShiroUserUtils.getUserId()+"," + code+","+order.getAmount();
		UniteOrderResult result = WeChatPayController.NATIVEPayment(code,"租赁包车","租赁包车", "1", attach);
    	model.addAttribute("codeUrl", result.getCode_url());
    	model.addAttribute("result", result);
		return "charter/pay/wechatPay";
	}
	/**
	 * Description：扫码支付含模式一二<br>
	 * User：liqijing <br>
	 * Date：2015-8-14下午02:16:47 <br>
	 */
	public static UniteOrderResult NATIVEPayment(String out_trade_no, String body, String detail, String totalFee, String attach) {
		//totalFee 不能有小数点
	//	totalFee = totalFee.substring(0,totalFee.indexOf("."));
		UniteOrder order = UniteOrder.nativeUniteOrder(out_trade_no,body,detail,totalFee,attach);
		String reqXML = PayImpl.generateXML(order, WeixinMpUtils.mch_api);
		log.info("请求微信信息："+reqXML);
		String respXML = PayImpl.requestWechat(ORDER_URL, reqXML);
		log.info("微信响应信息："+reqXML);
		UniteOrderResult result = (UniteOrderResult) PayImpl.turnObject(UniteOrderResult.class, respXML);
		return result;
	}
	
/*
	//额度结算支付
	@RequestMapping(value = "creDitPay", method = RequestMethod.GET)
	public String creDitPay(Double amount,Model model, HttpServletRequest requst) {
		String code = DataUtils.getCurrentTimeStr();
		//double amount = ((null == order.getAmount()) ? 0.1 : (order.getAmount()*100)); 
		String attach = ShiroUserUtils.getUserId()+"," + code;
		log.info("请求微信信息code："+code);
		UniteOrderResult result = WeChatPayController.NATIVEPayment(code,"租赁包车","租赁包车", amount*100+"", attach);
    	log.info("请求微信信息getCode_url："+result.getCode_url());
    	model.addAttribute("result",result);
    	model.addAttribute("codeUrl", result.getCode_url());
		return "charter/pay/wechatPay";
	}
*/
	/**
	 * Description：微信回调URL<br>
	 * User：liqijing <br>
	 * Date：2015-8-15下午07:41:19 <br>
	 */
	@ResponseBody
	@RequestMapping(value = "NativeNotifyUrl")
	public void NativeNotifyUrl(HttpServletRequest request, HttpServletResponse response){
		try{
			PrintWriter out = response.getWriter();
			String respXML = IOUtils.toString(request.getInputStream(),request.getCharacterEncoding());
			log.info("微信回调通知："+respXML);

			PayResultNotice resultNtice = (PayResultNotice) PayImpl.turnObject(PayResultNotice.class, respXML);
			if("SUCCESS".equals(resultNtice.getReturn_code())){
				String[] attach = resultNtice.getAttach().split(",");
				if("orderPay".equals(attach[0])){//订单支付
					String bigOrderCode = resultNtice.getOut_trade_no();
					//TODO:未做订单金额效验
					Order order = new Order();
					order.setBigOrderCode(bigOrderCode);//大订单编号
					order.setPaymentStatus(2L);//已支付
					order.setPaymentType(1L);//现付
					order.setStatus(0L);//待接受
					order.setPlaceTime(new Date());//下单时间
					orderFacade.update(order);
					
					order = new Order();
					order.setBigOrderCode(bigOrderCode);//大订单编号
					sMSNotifyUtil.send(order, 1);//包车方下单之后通知他的车辆和司机的联系方式;
					
					Bill bill = new Bill();  
					bill.setBillType("0");//包车方订单支付
					String price ="1";
					//若是测试金额1，则取存放数组的2序列值，否则就直接拿微信返回的实际金额
					if(null != attach[3] && !"".equals(attach[3].trim())){
						price = attach[3].trim();
					}else{
						price = resultNtice.getTotal_fee().trim();
					}
					Double amount = Double.parseDouble(price);
					BigDecimal bigDecimal = new BigDecimal(amount/100);   
					double money = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					bill.setMoney(money+"");//金额
					bill.setTradeMode("2");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联
					bill.setOrderCode(bigOrderCode);//大订单编号
					bill.setThreeOrderNo(resultNtice.getTransaction_id());//第三方订单编号
					bill.setUserId(Long.parseLong(attach[1]));//使用ID
					billFacade.save(bill);
					MessagePush.sendMessageAuto("nativeNotify", attach[1], "SUCCESS"+","+attach[2]+","+resultNtice.getTransaction_id());
				}
				log.debug("支付成功："+resultNtice.getReturn_code());
				log.debug("通知微信："+PayImpl.notify_urlSUCCESS());
				out.write(PayImpl.notify_urlSUCCESS());
			}else{
				String[] attach = resultNtice.getAttach().split(",");
				if("orderPay".equals(attach[0])){//订单支付
					MessagePush.sendMessageAuto("nativeNotify", attach[1], "FAIL"+","+attach[2]+","+resultNtice.getTransaction_id());
				}
				log.debug("支付失败："+resultNtice.getReturn_code());
				log.debug("通知微信："+PayImpl.notify_urlSUCCESS());
				out.write(PayImpl.notify_urlFail());
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	
}
