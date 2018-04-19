package com.dhxx.facade.constant;

/**
 * 
 * @author hanrs
 *
 */
public class Constant {
	
   /**
    * token有效期（小时）
    */
   public static final int TOKEN_EXPIRES_HOUR = 72;
   
   /**
    * token有效期（分钟,测试）
    */
   public static final int TOKEN_EXPIRES_MINUTE = 5;
   
   /**
    * 无权限
    */
   public static final String UNAUTHORIZED = "您无权限访问, 请联系管理员!";
   
   /**
    * 允许跨域IP
    */
   public static final String IP = "*";
   
   /**
    * 静态配置文件
    */
   public static final String PROFILEPATH = "public_system.properties";
   
   /**
    * shiro静态配置文件
    */
   public static final String SHIRO_CONFIG = "classpath:shiro-config.ini";
   
   /**
    * 换行
    */
   public static final String CRLF = "\r\n";
   
}
