package com.dhxx.web.wechat;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dhxx.web.shiro.ShiroUserUtils;
import com.dhxx.web.utils.MessagePush;
import com.lijing.wechatpay.conn.PaymentTools;
import com.lijing.wechatpay.impl.PayImpl;
import com.lijing.wechatpay.util.io.IOUtils;


/**
 * 微信支付接口
 * */
@Controller
@RequestMapping(value="/wechat")
public class PayController {
	
	private static String ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder" ;
	
	@RequestMapping(value = "scanPay", method = RequestMethod.GET)
	public String scanPay(Model model, HttpServletRequest requst) {
		String code = PaymentTools.businessOrderNumber();
		String attach = ShiroUserUtils.getUserId()+"," + code;
		System.out.println("请求微信信息code："+code);
		UniteOrderResult result = PayController.NATIVEPayment(code,"租赁包车","租赁包车", "1", attach);
    	System.out.println("请求微信信息getCode_url："+result.getCode_url());
    	model.addAttribute("codeUrl", result.getCode_url());
		return "wechat/scanPay";
}
	
	/**
	 * Description：扫码支付含模式一二<br>
	 * User：liqijing <br>
	 * Date：2015-8-14下午02:16:47 <br>
	 */
	public static UniteOrderResult NATIVEPayment(String out_trade_no, String body, String detail, String totalFee, String attach) {
			UniteOrder order = UniteOrder.nativeUniteOrder(out_trade_no,body,detail,totalFee,attach);
			String reqXML = PayImpl.generateXML(order, WeixinMpUtils.mch_api);
			System.out.println("请求微信信息："+reqXML);
			String respXML = PayImpl.requestWechat(ORDER_URL, reqXML);
			System.out.println("微信响应信息："+reqXML);
			UniteOrderResult result = (UniteOrderResult) PayImpl.turnObject(UniteOrderResult.class, respXML);
			return result;
	}

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
			System.out.println("微信回调通知："+respXML);
			
			PayResultNotice resultNtice = (PayResultNotice) PayImpl.turnObject(PayResultNotice.class, respXML);
			if("SUCCESS".equals(resultNtice.getReturn_code())){
				String order = resultNtice.getOut_trade_no();
				String[] attach = resultNtice.getAttach().split(",");
				MessagePush.sendMessageAuto("nativeNotify", attach[0], "SUCCESS"+","+attach[1]+","+resultNtice.getTransaction_id());
				System.out.println("支付成功："+resultNtice.getReturn_code());
				System.out.println("通知微信："+PayImpl.notify_urlSUCCESS());
				out.write(PayImpl.notify_urlSUCCESS());
			}else{
				String[] attach = resultNtice.getAttach().split(",");
				MessagePush.sendMessageAuto("nativeNotify", attach[0], "FAIL"+","+attach[1]+","+resultNtice.getTransaction_id());
				System.out.println("支付失败："+resultNtice.getReturn_code());
				System.out.println("通知微信："+PayImpl.notify_urlSUCCESS());
				out.write(PayImpl.notify_urlFail());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
