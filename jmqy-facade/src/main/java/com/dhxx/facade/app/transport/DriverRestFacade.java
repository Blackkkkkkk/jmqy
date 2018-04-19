package com.dhxx.facade.app.transport;


import com.dhxx.facade.entity.charter.evaluate.Evaluate;
import com.dhxx.facade.entity.order.Order;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> 类说明 </p>
 * @author xiewq
 * Date: 2018年03月09日
 * @version 1.00
 *
 */

public interface DriverRestFacade {

    Object list( HttpServletRequest requst);

    Object pushMessage(Order order, HttpServletRequest requst);
}
