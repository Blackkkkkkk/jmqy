package com.dhxx.web.transport.driver;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.company.Company;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.transport.driver.Driver;
import com.dhxx.facade.service.company.CompanyFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.transport.driver.DriverFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月13日
 * @version 1.01
 * 运输方司机管理
 */
@Controller
@RequestMapping("transport/driver")
@SuppressWarnings("unchecked")
public class DriverController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(DriverController.class);
    
    @Reference(protocol="dubbo")
    private DriverFacade driverFacade;
    
    @Reference(protocol="dubbo")
    private CompanyFacade companyFacade;
    
    @Reference(protocol="dubbo")
    private OrderFacade orderFacade;
    
	@RequestMapping(value = "list")
    public String list(Driver driver, Model model, HttpServletRequest requst) {
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		driver.setCompanyCode(shiroUser.companyCode);
    	PageInfo<Driver> drivers = (PageInfo<Driver>) driverFacade.list(driver);
        model.addAttribute("driver", driver);
        model.addAttribute("drivers", drivers);
        return "transport/driver/list";
    }

	//司机任务表
	@RequestMapping(value = "task")
    public String task(Order order, Model model, HttpServletRequest request) {
    	ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		order.setTransporterCode(shiroUser.companyCode);
		order.setIds("0,1,2");//-4已取消，-3被拒绝，-2待匹配，-1匹配中，0待接受，1等待上车，2在途,3完成
		/*if(StringUtils.isEmpty(order.getTaskTime())){
			order.setTaskTime(new Date());
		}*/
    	PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
    	model.addAttribute("order",order);
        model.addAttribute("orders", orders);
        return "transport/driver/task";
    }
	
	@RequestMapping(value = "form")
    public String form(Driver driver,Model model, HttpServletRequest requst
	                    ,@RequestParam(value = "companyId" ,required = false) String companyId) {
		String changeType = driver.getChangeType();
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		model.addAttribute("message", driver.getMessage());
		if(driver.getId() != null){
			List<Driver> drivers = (List<Driver>) driverFacade.find(driver);
			if(!CollectionUtils.isEmpty(drivers)){
				driver = drivers.get(0);
			}
		}
		Company company = new Company();

		if(shiroUser.roleName.indexOf("sys")>-1){
			company.setId(Long.parseLong(companyId));
		}else {
			company.setId(shiroUser.companyId);
		}
		List<Company> companys = (List<Company>) companyFacade.find(company);
		company = companys.get(0);
		if(changeType!=null &&changeType!=""){
			company.setChangeType(changeType);
		}
		model.addAttribute("company", company);
        model.addAttribute("driver", driver);
        model.addAttribute("changeType",changeType);
        model.addAttribute("companyId",companyId);
        return "transport/driver/form";
    }
	
   //校验司机企业工号或者身份证是否唯一
   @ResponseBody
   @RequestMapping(value = "checkUnique", method = RequestMethod.POST)
   public Boolean checkUnique(Driver driver) {
	   String code = driver.getCode();
	   driver.setCode(null);
	   List<Driver> drivers = (List<Driver>) driverFacade.find(driver);
		if(!CollectionUtils.isEmpty(drivers)){
			if (!StringUtils.isEmpty(code) && code.equals(drivers.get(0).getCode())) {
				return true;
			} else {
				return false;
			}
		}else{
			return true;
		}
   }
	
	/*
	* status:
	* 0在职，1休假，2离职
	* recordStatus:
	* 0正常，1删除，
	*/
   @ResponseBody
   @RequestMapping(value = "updateById", method = RequestMethod.POST)
   public Long updateById(Driver driver, Model model) {
	   try{
		   return (Long) driverFacade.updateById(driver);
       } catch (Exception e) {
    	   log.error(e.getMessage());
    	   return -1l;
       }
   }
   
   //更新或者新增
   @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
   public String saveOrUpdate(DriverFile files, Driver driver, Model model, HttpServletRequest request,
							  @RequestParam(value = "companyId",required = false) String companyId) throws UnsupportedEncodingException {
	   String message = (driver.getId() == null?"新建":"更新")+"成功@1";
	   try{
		   String filePath = "/upload/driver/"+driver.getJobNumPre()+driver.getJobNum()+"/";
		   String realPath = request.getSession().getServletContext().getRealPath(filePath);
		   File file = new File(realPath);
		   if(! file.exists()){
			   file.mkdir();
		   }
		   MultipartFile multipartFile = files.getIdCardProsFile();
		   if(! multipartFile.isEmpty()){
			   String fileName = driver.getJobNum() + "_idCardPros."+multipartFile.getOriginalFilename().split("\\.")[1];
			   multipartFile.transferTo(new File(realPath, fileName));
			   driver.setIdCardPros(filePath + fileName);
		   }
		   multipartFile = files.getIdCardConsFile();
		   if(! multipartFile.isEmpty()){
			   String fileName = driver.getJobNum() + "_idCardCons."+multipartFile.getOriginalFilename().split("\\.")[1];
			   multipartFile.transferTo(new File(realPath, fileName));
			   driver.setIdCardCons(filePath + fileName);
		   }
		   multipartFile = files.getDrivingLicenseFile();
		   if(! multipartFile.isEmpty()){
			   String fileName = driver.getJobNum() + "_drivingLicense."+multipartFile.getOriginalFilename().split("\\.")[1];
			   multipartFile.transferTo(new File(realPath, fileName));
			   driver.setDrivingLicense(filePath + fileName);
		   }
		   multipartFile = files.getWorkLicenseFile();
		   if(! multipartFile.isEmpty()){
			   String fileName = driver.getJobNum() + "_workLicense."+multipartFile.getOriginalFilename().split("\\.")[1];
			   multipartFile.transferTo(new File(realPath, fileName));
			   driver.setWorkLicense(filePath + fileName);
		   }
		  /* multipartFile = files.getHeadshotFile();
		   if(! multipartFile.isEmpty()){
			   String fileName = driver.getJobNum() + "_headshot."+multipartFile.getOriginalFilename().split("\\.")[1];
			   multipartFile.transferTo(new File(realPath, fileName));
			   driver.setHeadshot(filePath + fileName);
		   }*/
           ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
           driver.setCreateUser(shiroUser.userAccount);
           driver.setUpdateUser(shiroUser.userAccount);
           driver.setCreateCompany(shiroUser.companyCode);
           driver.setCompanyCode(shiroUser.companyCode);
           driver.setJobNum(driver.getJobNumPre()+driver.getJobNum());
           driver = (Driver) driverFacade.saveOrUpdate(driver);
       } catch (Exception e) {
    	   message = message.replace("成功@1", "失败@2");
    	   log.error(e.getMessage());
       }
       return "redirect:/transport/driver/form?message="+URLEncoder.encode(message, "UTF-8")+"&id="+(driver.getId()==null?driver.getReId():driver.getId())+"&companyId="+companyId;
   }
}
