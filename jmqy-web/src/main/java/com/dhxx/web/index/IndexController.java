package com.dhxx.web.index;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.service.menu.MenuFacade;

@Controller
@RequestMapping("index")
public class IndexController {

    @Reference(protocol="dubbo")
    private MenuFacade menuFacade;
	
	@RequestMapping(value = "")
    public String main(Model model, HttpServletRequest request) {
        return "index/index";
    }

}
