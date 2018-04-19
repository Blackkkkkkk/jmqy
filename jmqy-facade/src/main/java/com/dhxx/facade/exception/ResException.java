package com.dhxx.facade.exception;

import com.dhxx.common.exceptions.BusinessException;

/**
 * @author jhy
 */
public class ResException extends BusinessException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResException(){}
	
	public ResException(String msg) {
		super(-1, msg);
	}
	/**
     * 系统错误
     */
    public static final ResException SYSTEM_ERROR = new ResException("系统错误!");
	/**
     * 验证码发送失败
     */
    public static final ResException SEND_SMS_ERROR = new ResException("验证码发送失败");
    
    /**
     * 验证码错误
     */
    public static final ResException SMS_CODE_ERROR = new ResException("验证码错误");
    
    /**
     * 验证码超时
     */
    public static final ResException SMS_CODE_TIME_OUT = new ResException("验证码超时");
	
	/**
     * 用户未登录
     */
    public static final ResException USER_AUTHOR_ERROR = new ResException("用户未登录");
    
	/**
     * 用户不存在
     */
    public static final ResException USER_NOT_EXISTS = new ResException("用户不存在");
    
    /**
     * 用户已存在
     */
    public static final ResException USER_EXISTS = new ResException("用户已存在");
    
    /**
     * 密码错误
     */
    public static final ResException USER_LOGINED_ERROR = new ResException("用户已登录, 不允许重复登录");
    
    /**
     * 用户已登录
     */
    public static final ResException USER_PASS_ERROR = new ResException("密码错误");
    
    /**
     * 用户正在审核中
     */
    public static final ResException USER_STATUS_ERROR = new ResException("该账号正在审核中,不允许登陆");
    
    /**
     * 订单不存在
     */
    public static final ResException ORDER_NOT_EXISTS = new ResException("订单不存在");


    /**
     * 延时提交失败
     */
    public static final ResException SEND_SENDMESSAGE_ERROR = new ResException("延时提交失败");

    /**
     * 存储gprs失败
     */
    public static final ResException SEND_SAVEGPRS_ERROR = new ResException("存储gprs失败");

    /**
     * 获取历史总公里数
     */
    public static final ResException SEND_HISTORYMILEAGE_ERROR = new ResException("获取历史总公里数失败");
    
}
    
