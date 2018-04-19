package com.dhxx.facade.service.match;

import com.dhxx.facade.entity.order.Order;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年10月25日
 * @version 1.01
 * 包车方搜索匹配管理interface
 */

public interface MatchFacade {

	Object selectByCarTypes(Order order);

	Object selectLines(Order order);

	Object selectCars(Order order);

	Object selectDrivers(Order order);

}
