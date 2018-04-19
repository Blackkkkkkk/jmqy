package com.dhxx.facade.service.rule;

import com.dhxx.facade.entity.rule.Rule;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/25
 * @description
 */
public interface RuleFacade {

    Object list(Rule rule);

    void update(Rule rule);

	Object save(Rule rule);

	Object find(Rule rule);

	void delete(Rule rule);


	List<Rule> findRule(Rule rule);

	Object listCompany(Rule rule);

}
