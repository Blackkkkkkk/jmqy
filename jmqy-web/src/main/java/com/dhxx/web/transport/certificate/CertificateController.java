package com.dhxx.web.transport.certificate;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.transport.certificate.Certificate;
import com.dhxx.facade.service.transport.certificate.CertificateFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月13日
 * @version 1.01
 * 运输方证件管理
 */
@Controller
@RequestMapping("transport/certificate")
@SuppressWarnings("unchecked")
public class CertificateController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(CertificateController.class);
    
    @Reference(protocol="dubbo")
    private CertificateFacade certificateFacade;
    
	@RequestMapping(value = "list")
    public String list(Certificate certificate, Model model, HttpServletRequest requst) {
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		certificate.setTransporterCode(shiroUser.companyCode);
    	PageInfo<Certificate> certificates = (PageInfo<Certificate>) certificateFacade.list(certificate);
        model.addAttribute("certificate", certificate);
        model.addAttribute("certificates", certificates);
        return "transport/certificate/list";
    }
	
   //统计要提醒的证件数量
   @ResponseBody
   @RequestMapping(value = "statisticsDue", method = RequestMethod.POST)
   public Integer statisticsDue(Certificate certificate, Model model) {
	   try{
		   ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		   certificate.setTransporterCode(shiroUser.companyCode);
		   return (Integer) certificateFacade.statisticsDue(certificate);
       } catch (Exception e) {
    	   log.error(e.getMessage());
    	   return -1;
       }
   }
}
