package com.dhxx.web.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置文件application.properties
 * @author Administrator
 *
 */
public class PropertiesUtil {

	private final static Properties prop=new Properties();
	static {
		InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取键jdbc.url的值
	 * @return
	 */
	public static String getURL(){
		return prop.getProperty("jdbc.url");
	}
	
	/**
	 * 获取司机前10位终端id
	 * @return
	 */
	public static String getMsidSJTen(){
		return prop.getProperty("msid.sj.ten");
	}
	
	/**
	 * 获取乘客前10位终端id
	 * @return
	 */
	public static String getMsidCKTen(){
		return prop.getProperty("msid.ck.ten");
	}
	
	/**
	 * 获取tscweb企业id
	 * @return
	 */
	public static String getTSCEpId(){
		return prop.getProperty("tscweb.epid");
	}
	
	/**
	 * 获取乘客在tscweb的群组id
	 * @return
	 */
	public static String getTSCPassengerGrpId(){
		return prop.getProperty("tscweb.pgrpid");
	}
	
	/**
	 * 获取app支付宝需要的账号信息
	 * @return
	 */
	public static Map<String, String> getAliInfo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("PARTNER", prop.getProperty("ali.partner"));
		map.put("SELLER", prop.getProperty("ali.seller"));
		map.put("RSA_PRIVATE", prop.getProperty("ali.rsaprivate"));
		map.put("NOTIFY_URL", prop.getProperty("ali.notifyurl"));
		return map;
	}
	
	/**
	 * 获取后台服务器支付宝所需的商户信息
	 * @return
	 */
	public static Map<String, String> getEpAliInfo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("partner", prop.getProperty("ali.partner"));
		map.put("private_key", prop.getProperty("ali.ep.privatekey"));
		map.put("ali_public_key", prop.getProperty("ali.publickey"));
		return map;
	}
	
	/**
	 * 获取后台服务器微信支付商户信息
	 * @return
	 */
	public static Map<String, String> getWeChatInfo(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", prop.getProperty("wechat.appid"));
		map.put("appsecret", prop.getProperty("wechat.appsecret"));
		map.put("appkey", prop.getProperty("wechat.appkey"));
		map.put("partner", prop.getProperty("wechat.partner"));
		map.put("partnerkey", prop.getProperty("wechat.partnerkey"));
		map.put("openId", prop.getProperty("wechat.openId"));
		map.put("notifyurl", prop.getProperty("wechat.notifyurl"));
		return map;
	}
	
	/**
	 * 获取TSC  IP
	 * @return
	 */
	public static String getTSC_Ip(){
		return prop.getProperty("tsc.ip");
	}
	
	/**
	 * 获取headPath
	 * @return
	 */
	public static String getHeadPath(){
		return prop.getProperty("headPath");
	}
	
	/**
	 * 获取TSC  PORT
	 * @return
	 */
	public static Integer getTSC_Port(){
		return Integer.valueOf(prop.getProperty("tsc.port"));
	}
	
	/**
	 * 获取域名
	 * @return
	 */
	public static String getDomain(){
		return prop.getProperty("domain");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getTSCEpId());
	}
}
