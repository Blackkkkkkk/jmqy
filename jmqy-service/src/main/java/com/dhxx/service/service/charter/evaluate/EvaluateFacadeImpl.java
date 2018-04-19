package com.dhxx.service.service.charter.evaluate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.charter.evaluate.Evaluate;
import com.dhxx.facade.service.charter.evaluate.EvaluateFacade;
import com.dhxx.service.biz.charter.evaluate.EvaluateBiz;

/**
 * 订单评价
 * @author dingbin
 *
 */
@Service(protocol = {"dubbo"})
public class EvaluateFacadeImpl implements EvaluateFacade{
	private static Logger log = LoggerFactory.getLogger(EvaluateFacadeImpl.class);

	@Autowired
	private EvaluateBiz evaluateBiz;
	
	@Override
	public Object save(Evaluate evaluate) {
		log.info("EvaluateFacadeImpl.save()");
		evaluateBiz.save(evaluate);;
		return evaluate;
	}

	@Override
	public Object list(Evaluate evaluate) {
		log.info("EvaluateFacadeImpl.list()");
		return evaluateBiz.list(evaluate);
	}
	
	@Override
	public Object find(Evaluate evaluate) {
		log.info("EvaluateFacadeImpl.find()");
		return evaluateBiz.find(evaluate);
	}
}
