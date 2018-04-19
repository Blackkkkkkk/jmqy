package com.sync.common.constant;

import java.sql.Timestamp;

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

    /**成功*/
    public static final int CODE = 200;
    /**成功*/
    public static final String SUCCESS = "SUCCESS";
    /**失败*/
    public static final int FAIL = 10000;
    /**请求头参数无效*/
    public static final int HEAD_FAIL = 40002;
    /**初始化当前时间**/
    public static final Timestamp InitData = new Timestamp(System.currentTimeMillis());

}
