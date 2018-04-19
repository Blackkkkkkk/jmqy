package com.dhxx.web.transport.evaluate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.charter.evaluate.Evaluate;
import com.dhxx.facade.service.charter.evaluate.EvaluateFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月17日
 * @version 1.01
 * 运输方评价管理
 */
@Controller
@RequestMapping("transport/evaluate")
@SuppressWarnings("unchecked")
public class EvaluateTsController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(EvaluateTsController.class);
	
	@Reference(protocol="dubbo")
    private EvaluateFacade evaluateFacade;
    
	//运输方收到的评价信息
	@RequestMapping(value="list")
    public String list(Evaluate evaluate,Model model,HttpServletRequest request){
    	ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    	evaluate.setTransporterCode(shiroUser.companyCode);
    	PageInfo<Evaluate> evaluates = (PageInfo<Evaluate>) evaluateFacade.list(evaluate);
        model.addAttribute("evaluate", evaluate);
        model.addAttribute("evaluates", evaluates);
        return "transport/evaluate/list";
    }
	
	//评价信息详情
	@RequestMapping(value="detail")
    public String detail(Evaluate evaluate,Model model,HttpServletRequest request){
    	List<Evaluate> evaluates = (List<Evaluate>) evaluateFacade.find(evaluate);
    	if(!CollectionUtils.isEmpty(evaluates)){
    		evaluate = evaluates.get(0);
    	}
        model.addAttribute("evaluate", evaluate);
        return "transport/evaluate/detail";
    }
}