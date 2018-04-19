package com.dhxx.common.exceptions;


/**
 * @author jhy
 * 业务异常基类，所有业务异常都必须继承于此异常
 * 
 *  定义异常时，需要先确定异常所属模块
 */
public class BusinessException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * Token 验证不通过
	 */
	public static final BusinessException TOKEN_DECRYPT_ERROR = new BusinessException(10010001, "Token 解密错误");
	/**
	 * sign 错误
	 */
	public static final BusinessException SIGN_ERROR = new BusinessException(10010002, "签名错误");
	
	
	/**
	 * 解密 错误
	 */
	public static final BusinessException DECRYPT_ERROR = new BusinessException(10010003, "解密错误");
	
	
	/**
	 * 加密 错误
	 */
	public static final BusinessException ENCRYPTION_ERROR = new BusinessException(10010004, "加密错误");
	
	
	/**
	 * 参数异常错误
	 */
	public static final BusinessException PARAMETER_ERROR = new BusinessException(10010005, "参数错误");
	
	
	
	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected int code;

	public BusinessException(int code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}

	public BusinessException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public BusinessException newInstance(String msgFormat, Object... args) {
		return new BusinessException(this.code, msgFormat, args);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message) {
		super(message);
	}
}
