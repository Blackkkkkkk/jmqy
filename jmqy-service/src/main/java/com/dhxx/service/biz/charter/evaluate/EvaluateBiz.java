package com.dhxx.service.biz.charter.evaluate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhxx.facade.entity.charter.evaluate.Evaluate;
import com.dhxx.service.mapper.charter.evaluate.EvaluateMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * <p> 评价表 </p>
 * @author dingbin
 * Date: 2017年09月11日
 * @version 1.01
 *
 */
@Service
@Transactional
public class EvaluateBiz {
	private static Logger log = LoggerFactory.getLogger(EvaluateBiz.class);
	
	@Autowired
	private EvaluateMapper evaluateMapper;
	
	//保存
	public void save(Evaluate evaluate){
		log.info("EvaluateBiz.save()");
		evaluateMapper.save(evaluate);
	}
	
	//评价列表
	public PageInfo<Evaluate> list(Evaluate evaluate){
		log.debug("OrderBiz.list()");
		//设置起始页
		PageHelper.startPage(evaluate.getPageNum(), evaluate.getPageSize());
		List<Evaluate> list= evaluateMapper.list(evaluate);
		PageInfo<Evaluate> pageInfo = new PageInfo<Evaluate>(list);
		return pageInfo;
	}
	
	//查找评价
	public List<Evaluate> find(Evaluate evaluate){
		log.debug("OrderBiz.find()");
		return evaluateMapper.list(evaluate);
	}
}
