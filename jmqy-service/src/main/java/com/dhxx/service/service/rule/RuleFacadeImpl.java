package com.dhxx.service.service.rule;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.rule.Rule;
import com.dhxx.facade.service.rule.RuleFacade;
import com.dhxx.service.biz.rule.RuleBiz;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/25
 * @description
 */
@Service(protocol = {"dubbo"})
public class RuleFacadeImpl implements RuleFacade {

    @Autowired
    private RuleBiz ruleBiz;

    @Override
    public Object list(Rule rule) {
        return ruleBiz.list(rule);
    }
    
    @Override
    public Object find(Rule rule) {
        return ruleBiz.find(rule);
    }

    @Override
    public void update(Rule rule) {
        ruleBiz.update(rule);
    }

	@Override
	public Rule save(Rule rule) {
		return ruleBiz.save(rule);
	}

	@Override
	public void delete(Rule rule) {
		ruleBiz.delete(rule);
	}

	@Override
    public List<Rule> findRule(Rule rule){return ruleBiz.find(rule);}

    @Override
    public Object listCompany(Rule rule) {
        return ruleBiz.listCompany(rule);
    }


}
