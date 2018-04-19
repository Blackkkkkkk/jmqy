package com.dhxx.web.charter.coupon;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dhxx.facade.entity.charter.coupon.Coupon;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;

/**
 * 优惠卷
 * @author dingbin
 * @description  
 * 
 */
@Controller
@RequestMapping("charter/coupon")
public class CouponController {

	//优惠价列表
    @RequestMapping(value = "couponList")
    public String couponList(Coupon coupon, Model model, HttpServletRequest request) {
    	ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    	
    	model.addAttribute("coupon",coupon);
        return "charter/coupon/coupon";
    }
	
}