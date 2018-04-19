package com.dhxx.web.charter.center;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhxx.facade.entity.balance.BalanceDetilBill;
import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.entity.money.Money;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.service.balance.BalanceDetilBillFacade;
import com.dhxx.facade.service.credit.CreditDetilBillFacade;
import com.dhxx.facade.service.money.MoneyFacade;
import com.dhxx.facade.service.order.OrderFacade;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.company.Company;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.score.Score;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.company.CompanyFacade;
import com.dhxx.facade.service.credit.CreditFacade;
import com.dhxx.facade.service.score.ScoreFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.transport.car.CarFile;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;

/**
 * 个人中心
 * @author dingbin
 * @description  
 * 
 */
@Controller
@RequestMapping("charter/center")
public class CenterController {

	private static Logger log = LoggerFactory.getLogger(CenterController.class);
	
    @Reference(protocol="dubbo")
    private CreditFacade creditFacade;


    @Reference(protocol="dubbo")
    private CompanyFacade companyFacade;
    
    @Reference(protocol="dubbo")
    private UserFacade userFacade;
    
    @Reference(protocol="dubbo")
    private ScoreFacade scoreFacade;

	@Reference(protocol = "dubbo")
	private CreditDetilBillFacade creditDetilBillFacade;

	@Reference(protocol = "dubbo")
	private BalanceDetilBillFacade balanceDetilBillFacade;

	@Reference(protocol="dubbo")
	private OrderFacade orderFacade;

	@Reference(protocol="dubbo")
	private MoneyFacade moneyFacade;

	//信用额度查询
    @RequestMapping(value = "creditQuery")
    public String creditQuery(Credit credit, Model model, HttpServletRequest request) {
    	ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    	//credit.setUserId(shiroUser.id);
		credit.setCompanyCode(shiroUser.companyCode);
        credit = (Credit) creditFacade.findOne(credit);

    	model.addAttribute("credit", credit);

    	return "charter/credit/credit";
    }
    
    //积分查询
    @RequestMapping(value = "companyQuery")
    public String companyQuery(Score score, Model model, HttpServletRequest request) {
    	ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    	//score.setUserId(shiroUser.id);
		score.setCompanyCode(shiroUser.companyCode);
    	PageInfo<Score> page = (PageInfo<Score>)scoreFacade.list(score);
    	if(null != page && page.getSize() >0){
    		score = page.getList().get(0);//企业包车
    	}else{
    		score = new Score();//个人包车
    	}
    	model.addAttribute("score", score);
    	return "charter/center/integral";
    }
    
    //用户查询
    @RequestMapping(value = "personal")
    public String personal(User user, Company company, Model model, HttpServletRequest requst) {
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    	user.setId(shiroUser.id);
		List<User> users= (List<User>) userFacade.find(user);
    	if(!CollectionUtils.isEmpty(users)){
    		user = users.get(0);
    	}
    	model.addAttribute("user", user);
    	company.setCompanyCode(shiroUser.companyCode);
    	List<Company> companys= (List<Company>) companyFacade.find(company);
    	if(!CollectionUtils.isEmpty(companys)){
    		company = companys.get(0);
    	}
    	model.addAttribute("company", company);
        return "charter/center/personal";
    }
	
	//修改用户信息
    @RequestMapping(value ="updateUser", method = RequestMethod.POST)
    public String updateUser(User user, RedirectAttributes redirectAttributes){
    	String message = "更新成功@1";
    	try{
    		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        	user.setId(shiroUser.id);
    		userFacade.update(user);
    	} catch (Exception e) {
	 	    message = message.replace("成功@1", "失败@2");
	 	    log.error(e.getMessage());
    	}
	   redirectAttributes.addFlashAttribute("message", message); 
	   return "redirect:/charter/center/personal";
    }
    
    //包车企业认证
    @RequestMapping(value = "beCompany", method = RequestMethod.GET)
    public String beCompany(Company company, Model model, HttpServletRequest requst) {
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		company.setId(shiroUser.companyId);
		company.setCompanyCode(shiroUser.companyCode);
    	model.addAttribute("company", company);
        return "charter/center/beCompany";
    }
    
    //提交包车企业认证
    @RequestMapping(value = "beCompany", method = RequestMethod.POST)
    public String beCompany(CompanyFile files, Company company, HttpServletRequest request, RedirectAttributes redirectAttributes){
 	   String message = "提交成功@1";
 	   ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
 	   try{
 		   String filePath = "/upload/company/"+shiroUser.companyCode+"/";
 		   String realPath = request.getSession().getServletContext().getRealPath(filePath);
 		   File file = new File(realPath);
 		   if(! file.exists()){
 			   file.mkdir();
 		   }
 		   MultipartFile multipartFile = files.getBusinessPicFile();
 		   if(! multipartFile.isEmpty()){
 			   String fileName = shiroUser.companyCode + "_businessPic."+multipartFile.getOriginalFilename().split("\\.")[1];
 			   multipartFile.transferTo(new File(realPath, fileName));
 			   company.setBusinessPic(filePath + fileName);
 		   }
 		  company.setStatus(-1l);


 		  companyFacade.update(company);

	   } catch (Exception e) {
     	   message = message.replace("成功@1", "失败@2");
     	   log.error(e.getMessage());
        }
 	   redirectAttributes.addFlashAttribute("message", message); 
       return "redirect:/charter/center/beCompany";
    }
    
    //用户修改自己密码
    @ResponseBody
    @RequestMapping(value ="updatePw", method = RequestMethod.POST)
    public int updatePw(User user){
    	try{
    		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
    		User u = new User();
    		u.setId(shiroUser.id);
    		List<User> users= (List<User>) userFacade.find(u);
        	if(!CollectionUtils.isEmpty(users)){
        		u = users.get(0);
        	}
    		 String password = u.getUserPassword();
    		 u.setUserPassword(user.getUserPassword());
             ShiroUserUtils.checkPassword(u);
             if(!password.equals(u.getUserPassword())){
            	 return -1;
             }
             user.setUserPassword(user.getNewPassword());
             user.setUserAccount(shiroUser.userAccount);
             user.setId(shiroUser.id);
             ShiroUserUtils.encryptPassword(user);
             userFacade.update(user);
    	} catch (Exception e) {
    		log.error(e.getMessage());
    		return -2;
    	}
    	//密码修改成功就登出
    	 Subject currentUser = SecurityUtils.getSubject();
         if (currentUser.getSession() != null){
             ShiroUser shiroUser = (ShiroUser)currentUser.getPrincipal();
             if(shiroUser != null){
                 currentUser.logout();
                 log.debug(shiroUser.getName()+">>>>>>>>>>>>修改密码成功后，自动退出登录");
             }
         }
	   return 0;
    }


	//订单结算查询
	@RequestMapping(value = "settlement")
	public String settlement(Order order,Model model){

		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();


		if(shiroUser.roleName.indexOf("sys")>-1){
		}else {
			order.setCharterCode(shiroUser.companyCode);
		}

		if(order.getPaymentStatus() == null || order.getPaymentStatus() == 3 ){
			order.setPaymentStatus(3l);
			order.setPaymentStatusList("1,2");
		}
		if(order.getPaymentStatus() ==1 || order.getPaymentStatus()==2){
			order.setPaymentStatusList(null);
		}

		order.setStatus(3l);

		PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);


		model.addAttribute("order",order);
		model.addAttribute("orders", orders);

		if(shiroUser.roleName.indexOf("sys")>-1){
			System.out.println("1");
			return "sys/charterSettlementList";
		}else {
			System.out.println("2");
			return "charter/center/charterSettlementList";
		}

	}



	//余额查询
	@RequestMapping(value = "moneyQuery")
	public String moneyQuery(Money money, Model model, HttpServletRequest request) {
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		//credit.setUserId(shiroUser.id);
		money.setCompanyCode(shiroUser.companyCode);
		money = (Money) moneyFacade.findOne(money);

		model.addAttribute("money", money);

		return "charter/money/money";
	}


}
