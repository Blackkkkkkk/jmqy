package com.dhxx.web.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.common.util.DataUtils;
import com.dhxx.facade.entity.bill.Bill;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.weappOA.Usermessage;
import com.dhxx.facade.service.bill.BillFacade;
import com.dhxx.facade.service.order.OrderFacade;

import com.dhxx.web.utils.MessagePush;
import com.dhxx.web.wechat.PayResultNotice;
import com.dhxx.web.wechat.UniteOrder;
import com.dhxx.web.wechat.UniteOrderResult;
import com.dhxx.web.wechat.WeixinMpUtils;
import com.lijing.wechatpay.impl.PayImpl;
import com.lijing.wechatpay.util.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;




/**
 * 微信支付接口
 * */
@Controller
@RequestMapping(value="/pay")
@SuppressWarnings("unchecked")
public class WeChatPayController {
	
	private static Logger log = LoggerFactory.getLogger(WeChatPayController.class);
	
	@Reference(protocol="dubbo")
    private OrderFacade orderFacade;
	
	@Reference(protocol="dubbo")
	private BillFacade billFacade;
	
	private static String ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder" ;



	@RequestMapping(value =  "test")
	public  String test(){
		System.out.println("test");
		return "pay/wechatPay";
	}


	// 扫码支付
	@RequestMapping(value = "scanPay" )
	public  String scanPay(Model model, HttpServletRequest requst, HttpSession session){
        //把session中存的信息拿出去
		Order order =(Order) session.getAttribute("order");
		//order.setBigOrderCode(order.getBackOrderCode()+1);

		Usermessage usermessage = (Usermessage) session.getAttribute("usermessage");

		//System.out.println("order="+(!StringUtils.isEmpty(order.getBigOrderCode())));
		if(!StringUtils.isEmpty(order.getBigOrderCode())){
            List<Order> orders=(List<Order>)orderFacade.find(order);
			if(!CollectionUtils.isEmpty(orders)){
				order = orders.get(0);

			}
		}
        //改车票号码
		String code = order.getBigOrderCode();


		String attach = "orderPay,"+usermessage.getPhone()+"," + code+","+500;
		//String attach = "orderPay,"+"3000"+","+code+","+500;
		UniteOrderResult result = WeChatPayController.NATIVEPayment(code,"租赁包车","租赁包车", "1", attach);

		//用完把值再存回session中，确保值的同步性
		session.setAttribute("order",order);
		session.setAttribute("usermessage",usermessage);

		model.addAttribute("codeUrl", result.getCode_url());
	    model.addAttribute("result", result);

		return "pay/wechatPay";
	}
	

	public static UniteOrderResult NATIVEPayment(String out_trade_no, String body, String detail, String totalFee, String attach) {
		//这里改回调地址
		UniteOrder order = UniteOrder.nativeUniteOrder(out_trade_no,body,detail,totalFee,attach);
		String reqXML = PayImpl.generateXML(order, WeixinMpUtils.mch_api);
		log.info("请求微信信息："+reqXML);
		String respXML = PayImpl.requestWechat(ORDER_URL, reqXML);
		log.info("微信响应信息："+reqXML);
		UniteOrderResult result = (UniteOrderResult) PayImpl.turnObject(UniteOrderResult.class, respXML);

		System.out.println("result="+result);
		return result;
	}
	



	/**
	 * Description：微信回调URL<br>
	 * User：liqijing <br>
	 * Date：2015-8-15下午07:41:19 <br>
	 */
	//改完上面的地址会有回调
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
					orderFacade.update(order);
					
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
