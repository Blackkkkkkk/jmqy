package com.dhxx.common.constant;

/**
 * 
 * @author jhy
 * 系统级常量
 *
 */
public class Constant {
	
	//SHIRO加密
	public static final String HASH_ALGORITHM = "SHA-1";//加密算法  
	public static final int HASH_INTERATIONS = 1024;//迭代次数  
	
	/**
     * shiro静态配置文件
     */
    public static final String SHIRO_CONFIG = "classpath:shiro-config.ini";
    /**
     * web工程名称
     */
    public static final String PROJECT = "/swjt-web";
    
    /**
     * 换行
     */
    public static final String CRLF = "\r\n";
    /**
     * 订单编码开头
     */
 	public static final String OR_CODE = "DH-";
 	 /**
     * 回程单编码开头
     */
 	public static final String ROR_CODE = "DHR-";
    /**
    * 车辆编码开头
    */
	public static final String CA_CODE = "CA-";
	/**
    * 司机编码开头
    */
	public static final String DR_CODE = "DR-";
    /**
     * 用户编码开头
     */
    public static final String PN_CODE = "PN-";
    /**
     * 公司编码开头
     */
    public static final String CP_CODE = "CP-";
	
}
