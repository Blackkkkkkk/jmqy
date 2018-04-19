package com.dhxx.web.transport.statistic;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dhxx.web.BaseController;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月18日
 * @version 1.01
 * 运输方统计分析管理
 */
@Controller
@RequestMapping("transport/statistic")
public class StatisticController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(StatisticController.class);
    
   @RequestMapping(value = "analysis")
   public String analysis(Model model, HttpServletRequest requst) {
       return "transport/statistic/analysis";
   }
		
}
