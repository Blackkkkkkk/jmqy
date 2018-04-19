package com.dhxx.web.transport.car;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.facade.entity.transport.car.CarTravel;
import com.dhxx.facade.entity.transport.driver.Driver;
import com.dhxx.facade.service.transport.car.CarFacade;
import com.dhxx.facade.service.transport.car.CarTravelFacade;
import com.dhxx.facade.service.transport.driver.DriverFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月16日
 * @version 1.01
 * 运输方车辆行程管理
 */
@Controller
@RequestMapping("transport/carTravel")
@SuppressWarnings("unchecked")
public class CarTravelController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(CarTravelController.class);
    
    @Reference(protocol="dubbo")
    private CarTravelFacade carTravelFacade;
    
    @Reference(protocol="dubbo")
    private DriverFacade driverFacade;
    
    @Reference(protocol="dubbo")
    private CarFacade carFacade;
    
	@RequestMapping(value = "list")
    public String list(CarTravel carTravel, Model model, HttpServletRequest requst) {
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		carTravel.setCreateCompany(shiroUser.companyCode);
    	PageInfo<CarTravel> carTravels = (PageInfo<CarTravel>) carTravelFacade.list(carTravel);
        model.addAttribute("carTravel", carTravel);
        model.addAttribute("carTravels", carTravels);
        return "transport/carTravel/list";
    }
	
	@RequestMapping(value = "form")
    public String form(CarTravel carTravel, Model model, HttpServletRequest requst) {
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		model.addAttribute("message", carTravel.getMessage());
		if(!StringUtils.isEmpty(carTravel.getId())){
			List<CarTravel> carTravels = (List<CarTravel>) carTravelFacade.find(carTravel);
			if(!CollectionUtils.isEmpty(carTravels)){
				carTravel = carTravels.get(0);
			}
		}
		/*Driver driver = new Driver();
		driver.setCompanyCode(shiroUser.companyCode);
		model.addAttribute("drivers", driverFacade.find(driver));*/
		Car car =new Car();
		car.setCompanyCode(shiroUser.companyCode);
		model.addAttribute("cars", carFacade.find(car));
        model.addAttribute("carTravel", carTravel);
        return "transport/carTravel/form";
    }
	
	/*
	* recordStatus:
	* 0正常，1删除，
	*/
   @ResponseBody
   @RequestMapping(value = "updateById", method = RequestMethod.POST)
   public Long updateById(CarTravel carTravel, Model model) {
	   try{
		   return (Long) carTravelFacade.updateById(carTravel);
       } catch (Exception e) {
    	   log.error(e.getMessage());
    	   return -1l;
       }
   }
   
   //更新或者新增
   @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
   public String saveOrUpdate(CarTravel carTravel, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
	   Long id = carTravel.getId();
	   String message = (id == null?"新建":"更新")+"成功@1";
	   try{
           ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
           carTravel.setCreateUser(shiroUser.userAccount);
           carTravel.setUpdateUser(shiroUser.userAccount);
           carTravel.setCreateCompany(shiroUser.companyCode);
           carTravel = (CarTravel) carTravelFacade.saveOrUpdate(carTravel);
       } catch (Exception e) {
    	   message = message.replace("成功@1", "失败@2");
    	   log.error(e.getMessage());
       }
       return "redirect:/transport/carTravel/form?message="+URLEncoder.encode(message, "UTF-8")+"&id="+(id==null?carTravel.getReId():id);
   }
		
}
