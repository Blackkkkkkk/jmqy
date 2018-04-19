package com.dhxx.web.shiro;

/**
 * <p> 类说明 </p>
 * @author jhy
 * Date: 2017年4月5日
 * @version 1.01
 *
 */
public interface AuthService {

    /**
     * 加载过滤配置信息
     * @return
     */
    public String loadFilterChainDefinitions();
    
    /**
     * 重新构建权限过滤器
     * 一般在修改了用户角色、用户等信息时，需要再次调用该方法
     */
    public void reCreateFilterChains();
    
}
