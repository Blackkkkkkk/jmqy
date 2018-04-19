package com.dhxx.web.transport.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.rule.Rule;
import com.dhxx.facade.service.rule.RuleFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年12月5日
 * @version 1.01
 * 运输方系统设置管理
 */
@Controller
@RequestMapping("transport/sys")
@SuppressWarnings("unchecked")
public class SysTsController extends BaseController{
		
	private static Logger log = LoggerFactory.getLogger(SysTsController.class);
    
	@Reference(protocol="dubbo")
    private RuleFacade ruleFacade;
    
	@RequestMapping(value = "list")
    public String personal(Rule rule, Model model, HttpServletRequest requst) {
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		rule.setCompanyCode(shiroUser.companyCode);
		List<Rule> rules = (List<Rule>) ruleFacade.find(rule);
		if (CollectionUtils.isEmpty(rules)) {
			rule.setParentId(-1l);
			rule.setRuleType(16l);
			rule.setCompanyCode(null);
			rules = (List<Rule>) ruleFacade.find(rule);
	    
			for (int i = 0; i < rules.size(); i++) {
		    	Rule rr = rules.get(i);
		    	rr.setId(null);
		    	rr.setParentId(0l);
		    	rr.setCompanyCode(shiroUser.companyCode);
		    	ruleFacade.save(rr);
			}
		}
    	model.addAttribute("rules", rules);
        return "transport/sys/list";
    }
	
}
