package com.dhxx.web.wechat;

public class WeixinMpUtils {
	
	//易约的士公众号
	public final static String appID = "wxb60edd4839e9701c";
	public final static String appsecret = "7b547e844ad7aece0289fe7e1ef5f349";
	
	//易约的士公众号商户
	public final static String mch_id = "1333801401";
	public final static String mch_api = "hanronsheng880811HanRonSheng8808";
	
	//域名
	//public final static String domain = "http://www.hd1000.cn";
	
	//微信支付回调通知地址
	public final static String Notify_url = "http://119.23.160.82:8080/jmqy-wechat/order/NativeNotifyUrl";
	//public final static String Notify_url = "http://n79unz.natappfree.cc/jmqy-web/charter/wechat/NativeNotifyUrl";
	
	//项目名
	public final static String project ="zhaoche";
	
	//微信网页授权回调域名
	public final static String wxAuthorize = "https://open.weixin.qq.com/connect/oauth2/authorize";
	
	private  static String access_token = null;
	private  static String jsticket = null;
	
	public static String codeURLForC = wxAuthorize + "?appid="+appID+"&redirect_uri="+PropertiesUtil.getDomain()+"/"+project+"/wx/newOrder&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
	public static String codeURLForL = wxAuthorize + "?appid="+appID+"&redirect_uri="+PropertiesUtil.getDomain()+"/"+project+"/me/me&response_type=code&scope=snsapi_base&state=123#wechat_redirect";

	public static String AppUrl = "http://www.easywalkie.com/ycapp/yyds.html";
	

}
