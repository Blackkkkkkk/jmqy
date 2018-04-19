package com.dhxx.facade.app.gprs;


import com.dhxx.facade.entity.gprs.GprsCar;
import com.dhxx.facade.entity.user.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> 类说明 </p>
 * @author xiewq
 * Date: 2018年03月09日
 * @version 1.01
 *
 */
public interface GprsRestFacade {

    Object save(GprsCar gprsCar, HttpServletRequest requst);

    Object getGprs(GprsCar gprsCar, HttpServletRequest requst);
}
