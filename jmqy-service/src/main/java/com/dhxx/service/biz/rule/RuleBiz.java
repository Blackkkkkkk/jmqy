package com.dhxx.service.biz.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhxx.facade.entity.rule.Rule;
import com.dhxx.service.mapper.rule.RuleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author jhy
 * @date 2017/9/25
 * @description
 */
@Service
@Transactional
public class RuleBiz {

    @Autowired
    private RuleMapper ruleMapper;

    public PageInfo<Rule> list(Rule rule) {
        PageHelper.startPage(rule.getPageNum(), rule.getPageSize());
        List<Rule> list = ruleMapper.list(rule);
        PageInfo<Rule> pageInfo = new PageInfo<Rule>(list);
        return pageInfo;
    }
    
    public List<Rule> find(Rule rule) {
        return ruleMapper.list(rule);
    }

    public void update(Rule rule) {
        ruleMapper.update(rule);
    }

	public Rule save(Rule rule) {
		ruleMapper.save(rule);
		return rule;
	}
	
	public void delete(Rule rule) {
		 ruleMapper.delete(rule);
	}

    public PageInfo<Rule> listCompany(Rule rule) {
        PageHelper.startPage(rule.getPageNum(), rule.getPageSize());
        List<Rule> list = ruleMapper.listCompany(rule);
        PageInfo<Rule> pageInfo = new PageInfo<Rule>(list);
        return pageInfo;
    }

}
