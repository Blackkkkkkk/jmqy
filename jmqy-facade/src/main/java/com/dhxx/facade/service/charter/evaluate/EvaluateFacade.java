package com.dhxx.facade.service.charter.evaluate;

import com.dhxx.facade.entity.charter.evaluate.Evaluate;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月07日
 * @version 1.01
 * 订单评价interface
 */
public interface EvaluateFacade {
	
	Object save(Evaluate evaluate);
	
	Object list(Evaluate evaluate);

	Object find(Evaluate evaluate);
}
