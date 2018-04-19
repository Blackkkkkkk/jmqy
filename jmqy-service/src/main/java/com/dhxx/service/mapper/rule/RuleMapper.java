package com.dhxx.service.mapper.rule;

import com.dhxx.facade.entity.rule.Rule;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/25
 * @description
 */
public interface RuleMapper {

    List<Rule> list(Rule rule);

    void update(Rule rule);

	void save(Rule rule);

	void delete(Rule rule);

	List<Rule> listCompany(Rule rule);
}
