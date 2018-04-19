package com.dhxx.common.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;

public class WechatUtils {
	
	private static Logger log = LoggerFactory.getLogger(WechatUtils.class);
	
	/**
	* AppID(五邑旅游集散中心公众号应用ID)
	*/
	public static final String WEIXIN_APPID = "wxeb4b373394856bd3";

	//用于微信授权登录获取的值
	public static String getWeixinAppid() {
		return WEIXIN_APPID;
	}

	public static String getWeixinAppsecret() {
		return WEIXIN_APPSECRET;
	}

	/**

	* AppSecret(五邑旅游集散中心公众号应用密钥)
	*/
	public static final String WEIXIN_APPSECRET = "0b08ea3c14a3060df370a547ed868cb1";
	/**
	* 微信网页授权回调域名
	*/
	public final static String WEIXIN_AUTHORIZE = "https://open.weixin.qq.com/connect/oauth2/authorize";
	/**
	* 公众平台的所需的access_token的生成API调用url,http请求方式: GET
	*/
	public final static String TOKEN_API = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WEIXIN_APPID+"&secret="+WEIXIN_APPSECRET;
	/**
	* 公众平台获取用户的openid的API调用url,http请求方式: GET
	*/
	public final static String OPENID_API = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WEIXIN_APPID+"&secret="+WEIXIN_APPSECRET+"&code=oauth2_code&grant_type=authorization_code";
	/**
	* 公众平台删除菜单的API调用url,http请求方式: GET
	*/
	public final static String DELETE_MENU_API = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=accessToken";
	/**
	* 公众平台创建菜单的API调用url,http请求方式: POST
	*/
	public final static String CREATE_MENU_API = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=accessToken";
	/**
	* 公众平台的所需的jsapi_ticket的生成API调用url,http请求方式: GET
	*/
	public final static String TICKET_API = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=accessToken&type=jsapi";
	/**
	* 微信模板消息接口
	*/
	public final static String TEMPLATE_URL="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=accessToken";
	/**
	* access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token
	*/
	public static String ACCESS_TOKEN;
	/**
	* jsapi_ticket是公众号用于调用微信JS接口的临时票据。
	*/
	public static String JSAPI_TICKET;
	
	//域名
	public final static String domain = "http://nginx.easywalkie.com";
	
	//项目名
	public final static String project ="jmqy-wechat";
	
	//最新发布地址
	public static String searchURL = domain+"/search";
	
	//APP下载地址
	public static String downloadURL = domain+"/download";
	
	//我的信息
	public static String userURL = domain+"/user";
	
	//我的业务
	public static String myBusinessURL = "http://mobile.jmqyjt.com/School";
	
	//历史信息
	public static String historyURL = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIxNDc1NzAwMQ==&scene=123#wechat_redirect";
	
	//获取access_token
	public static String getAccessToken(){
		try {
			System.out.println("TOKEN_API="+TOKEN_API);
			JSONObject object = JSON.parseObject(httpRequest(TOKEN_API, "GET", null));

			ACCESS_TOKEN = object.getString("access_token");
			getJsTicket();//获取jsticket
			log.debug("ACCESS_TOKEN: "+ACCESS_TOKEN);
			return ACCESS_TOKEN; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	/** 
	 * 获取jsticket 
	 * @return 
	 * @throws URISyntaxException 
	 * @throws Exception 
	 * @throws ClientProtocolException 
	 */  
	public static String getJsTicket(){
		try {
			JSONObject object = JSON.parseObject(httpRequest(TICKET_API.replaceAll("accessToken", ACCESS_TOKEN), "GET", null));
			JSAPI_TICKET = object.getString("ticket");
			log.debug("JSAPI_TICKET: "+JSAPI_TICKET);
			return JSAPI_TICKET; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;  
	}  
	    
	    
	/** 
	* 获取jsapi相关参数map 
	* @param jsapi_ticket 
	* @param url 
	* @return 
	*/  
   public static Map<String, Object> getJsSignMap(HttpServletRequest request) {  
       String nonce_str = UUID.randomUUID().toString();  
       String timestamp = Long.toString(System.currentTimeMillis() / 1000);  
       String s = "jsapi_ticket=" + JSAPI_TICKET + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + getRequestFullURL(request);  
       String signature = SHA1.encode(s);  
       Map<String, Object> map = new HashMap<String, Object>();
       map.put("appID", WEIXIN_APPID); 
       map.put("url", getRequestFullURL(request));  
       map.put("jsapi_ticket", JSAPI_TICKET);  
	   map.put("nonceStr", nonce_str);  
	   map.put("timestamp", timestamp);  
	   map.put("signature", signature);  
	   return map;
   }  
		
   	/**
	 * 创建Menu
	 * @Title: createMenu
	 * @Description: 创建Menu
	 * @param @return
	 * @param @throws IOException    设定文件
	 * @return String    返回类型
	 * @throws
	  */
	public static String createMenu() {


		String index ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxeb4b373394856bd3&redirect_uri=http%3a%2f%2frent.easywalkie.com%2fjmqy-wechat%2flogin%2findex&response_type=code&scope=snsapi_userinfo&state=";
		String search ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxeb4b373394856bd3&redirect_uri=http%3a%2f%2frent.easywalkie.com%2fjmqy-wechat%2fsearch%2fwechatapp&response_type=code&scope=snsapi_userinfo&state=";
		String loginUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxeb4b373394856bd3&redirect_uri=http%3a%2f%2frent.easywalkie.com%2fjmqy-wechat%2flogin&response_type=code&scope=snsapi_userinfo&state=";

		String history = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxeb4b373394856bd3&redirect_uri=http%3a%2f%2frent.easywalkie.com%2fjmqy-wechat%2fuser%2flist1%2f-2&response_type=code&scope=snsapi_userinfo&state=";

		System.out.println("history="+getAccessToken());
	//	String history = "jmqy-wechat/user/history"
		String menu = "{\"button\":[" +
				 "{\"name\":\"微客票\",\"sub_button\":["+
				 "{\"type\":\"view\",\"name\":\"首页\",\"url\":\""+index+"\"},"+
				 "{\"type\":\"view\",\"name\":\"个人订单\",\"url\":\""+history+"\"},"+
				"{\"type\":\"view\",\"name\":\"登录\",\"url\":\""+loginUrl+"\"}"+
				 "]}" +
				 ",{\"name\":\"微巴士\",\"sub_button\":["+
				 "{\"type\":\"view\",\"name\":\"定制巴士\",\"url\":\""+myBusinessURL+"\"},"+
				 "{\"type\":\"view\",\"name\":\"租赁包车\",\"url\":\""+search+"\"}" +
				 "]}" + 
				 ",{\"name\":\"微服务\",\"sub_button\":["+
				 "{\"type\":\"view\",\"name\":\"汽运驾培\",\"url\":\""+myBusinessURL+"\"},"+
				 "{\"type\":\"view\",\"name\":\"在线客服\",\"url\":\""+myBusinessURL+"\"}" +
				 "]}" + 
				 "]}";
		try {
			String message = httpRequest(CREATE_MENU_API.replaceAll("accessToken", getAccessToken()), "POST", menu);
			System.out.println("11="+message);
			return "createMenu返回信息:"+message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "createMenu 失败";   
	  }
	
	 /**
	 * 删除当前Menu
	* @Title: deleteMenu
	* @Description: 删除当前Menu
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	 public static String deleteMenu(){
		try {
			String message = httpRequest(DELETE_MENU_API.replaceAll("accessToken", ACCESS_TOKEN), "GET", null);
			return "deleteMenu返回信息:"+message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "deleteMenu 失败";   
	 }
	   
	//通过code换取网页授权access_token
	public static String getOpenIdByCode(String code) {
		try {
			JSONObject object = JSON.parseObject(httpRequest(OPENID_API.replaceAll("oauth2_code", code), "GET", null));
			return object.getString("openid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;  
	}
	
	
	
	/**      
	 * 描述:获取 request 请求的全部路径包括？但是不包括#后面的
	 * <pre>
	 * 举例：https://mp.weixin.qq.com/cgi-bin/home?t=home/index&lang=zh_CN&token=608540619#wechat_redirect
	 * 获取到：https://mp.weixin.qq.com/cgi-bin/home?t=home/index&lang=zh_CN&token=608540619
	 * </pre>
	 * @param request
	 * @return String
	 * @throws IOException      
	 */
	public static String getRequestFullURL(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL(); 
		if (request.getQueryString() != null) {
			url.append("?");  
			url.append(request.getQueryString()); 
		} 
		return url.toString().split("#")[0];
	}
	
	/**      
	 * 开发者调用模板消息接口时需提供模板ID:_aOV_1j-X0XiGsf3qE1YkJCzOS791YoDYXg5eg2RqEg
	 * 标题：匹配提醒
	 */
	public static boolean sendMatchTemplate(String openId, String first){
		Template tem=new Template();
		tem.setTemplateId("_aOV_1j-X0XiGsf3qE1YkJCzOS791YoDYXg5eg2RqEg");
		tem.setTopColor("#00DD00");
		tem.setToUser(openId);
		tem.setUrl(WechatUtils.downloadURL);
				
		List<TemplateParam> paras=new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("first",first,"#FF3333"));
		paras.add(new TemplateParam("keyword1","系统自动匹配","#0044BB"));
		paras.add(new TemplateParam("keyword2","匹配中","#0044BB"));
		paras.add(new TemplateParam("remark","请点击下载app查看详情！","#AAAAAA"));
				
		tem.setTemplateParamList(paras);
				
		return sendTemplateMsg(tem);
	}

	/**      
	 * 描述:模板消息接口 - 微信公众平台
	 */
	public static boolean sendTemplateMsg(Template template){
		boolean flag=false;
		JSONObject object = JSON.parseObject(httpRequest(TEMPLATE_URL.replaceAll("accessToken", ACCESS_TOKEN), "POST", template.toJSON()));
		if(object!=null){
			int errorCode=object.getInteger("errcode");
			String errorMessage=object.getString("errmsg");
			if(errorCode==0){
				flag=true;
			}else{
				log.debug("模板消息发送失败:"+errorCode+","+errorMessage);
				flag=false;
			}
		}
		return flag;
	}
	
	 public static String httpRequest(String requestUrl, String requestMethod, String outputStr)
	  {
	    StringBuffer buffer = new StringBuffer();
	    try
	    {
	      URL url = new URL(requestUrl);
	      HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();

	      httpUrlConn.setDoOutput(true);
	      httpUrlConn.setDoInput(true);
	      httpUrlConn.setUseCaches(false);

	      httpUrlConn.setRequestMethod(requestMethod);

	      if ("GET".equalsIgnoreCase(requestMethod)) {
	        httpUrlConn.connect();
	      }

	      if (outputStr != null) {
	        OutputStream outputStream = httpUrlConn.getOutputStream();

	        outputStream.write(outputStr.getBytes("UTF-8"));
	        outputStream.close();
	      }

	      InputStream inputStream = httpUrlConn.getInputStream();
	      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

	      String str = null;
	      while ((str = bufferedReader.readLine()) != null) {
	        buffer.append(str);
	      }
	      bufferedReader.close();
	      inputStreamReader.close();

	      inputStream.close();
	      inputStream = null;
	      httpUrlConn.disconnect();
	    } catch (ConnectException ce) {
	    	log.error("Weixin server connection timed out.");
	    } catch (Exception e) {
	    	log.error("https request error:{}", e);
	    }
	    return buffer.toString();
	  }
	 
	 public static String acceptMessage(String xmlMsg){
			// 处理接收消息
			log.debug("xmlMsg="+xmlMsg);
			// 将POST流转换为XStream对象
			XStream xs = SerializeXmlUtil.createXstream();
			xs.processAnnotations(InputMessage.class);
			xs.processAnnotations(OutputMessage.class);
			// 将指定节点下的xml节点数据映射为对象
			xs.alias("xml", InputMessage.class);
			// 将xml内容转换为InputMessage对象
			InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());
			log.debug("inputMsg="+inputMsg);
			String servername = inputMsg.getToUserName();// 服务端
			String custermname = inputMsg.getFromUserName();// 客户端
			long createTime = inputMsg.getCreateTime();// 接收时间
			Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间
			
			// 取得消息类型
			String msgType = inputMsg.getMsgType();
			log.debug("msgType="+msgType);
			// 根据消息类型获取对应的消息内容
			if (msgType.equals(MsgType.Text.toString())) {
				// 文本消息
				log.debug("开发者微信号：" + inputMsg.getToUserName());
				log.debug("发送方帐号：" + inputMsg.getFromUserName());
				log.debug("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));
				log.debug("消息内容：" + inputMsg.getContent());
				log.debug("消息Id：" + inputMsg.getMsgId());
				
				StringBuffer str = new StringBuffer();
				str.append("<xml>");
				str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
				str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");
				str.append("<CreateTime>" + returnTime + "</CreateTime>");
				str.append("<MsgType><![CDATA[" + msgType + "]]></MsgType>");
				str.append("<Content><![CDATA["+"感谢您的关注，更多详情欢迎来电咨询，联系电话：0755-82691700，"
						+ "手机专线：138 1881 888，电子邮箱：baidingsuo@163.com， "
						+ "公司地址：广东省东莞市沙田镇福禄沙管理区。"+"]]></Content>");
				str.append("</xml>");
				log.debug(str.toString());
				return str.toString();
			}
			// 获取并返回多图片消息
			if (msgType.equals(MsgType.Image.toString())) {
				log.debug("获取多媒体信息");
				log.debug("多媒体文件id：" + inputMsg.getMediaId());
				log.debug("图片链接：" + inputMsg.getPicUrl());
				log.debug("消息id，64位整型：" + inputMsg.getMsgId());
				
				OutputMessage outputMsg = new OutputMessage();
				outputMsg.setFromUserName(servername);
				outputMsg.setToUserName(custermname);
				outputMsg.setCreateTime(returnTime);
				outputMsg.setMsgType(msgType);
				ImageMessage images = new ImageMessage();
				images.setMediaId(inputMsg.getMediaId());
				outputMsg.setImage(images);
				log.debug("xml转换：/n" + xs.toXML(outputMsg));
				return xs.toXML(outputMsg);
			
			}
			if (msgType.equals(MsgType.Event.toString())) {
				String event = inputMsg.getEvent();
				// 点击菜单拉取消息时的事件推送
				log.debug("开发者微信号：" + inputMsg.getToUserName());
				log.debug("发送方帐号：" + inputMsg.getFromUserName());
				log.debug("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));
				log.debug("消息内容：" + inputMsg.getContent());
				log.debug("消息Id：" + inputMsg.getMsgId());
				
				StringBuffer str = new StringBuffer();
				str.append("<xml>");
				str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
				str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");
				str.append("<CreateTime>" + returnTime + "</CreateTime>");
				str.append("<MsgType><![CDATA[" + "text" + "]]></MsgType>");
				if("subscribe".equals(event)){
					str.append("<Content><![CDATA["+"感谢您的关注，更多详情欢迎来电咨询，联系电话：0755-82691700，"
							+ "手机专线：138 1881 888，电子邮箱：baidingsuo@163.com， "
							+ "公司地址：广东省东莞市沙田镇福禄沙管理区。"+"]]></Content>");
				}else{
					str.append("<Content><![CDATA["+"感谢您的关注，更多详情欢迎来电咨询，联系电话：0755-82691700，"
							+ "手机专线：138 1881 888，电子邮箱：baidingsuo@163.com， "
							+ "公司地址：广东省东莞市沙田镇福禄沙管理区。"+"]]></Content>");
				}
				str.append("</xml>");
				log.debug(str.toString());
				return str.toString();
			}
			return null;
		}
	 public static void main(String[] args) {
		System.out.println(createMenu());
	}
	
}
