package com.dhxx.facade.app.order;

import javax.servlet.http.HttpServletRequest;

import com.dhxx.facade.entity.order.Order;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2018年02月01日
 * @version 1.01
 *
 */
public interface OrderRestFacade {

    Object list(Order order, HttpServletRequest requst);
    
    Object detail(Order order, HttpServletRequest requst);
    
    Object reportStatus(Order order, HttpServletRequest requst);

    Object doDelay(Order order, HttpServletRequest requst);

    Object historyMileage(Order order, HttpServletRequest requst);
}
