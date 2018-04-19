package com.dhxx.web.credit;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.common.util.DataUtils;
import com.dhxx.facade.entity.balance.BalanceDetilBill;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.entity.user.Userper;
import com.dhxx.facade.service.balance.BalanceDetilBillFacade;
import com.dhxx.facade.service.credit.CreditDetilBillFacade;
import com.dhxx.facade.service.credit.CreditFacade;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jhy
 * @date 2017/9/16
 * @description 信用额度管理
 */
@Controller
@RequestMapping("credit")
public class CreditController {

    @Reference(protocol = "dubbo")
    private CreditFacade creditFacade;

    @Reference(protocol = "dubbo")
    private CreditDetilBillFacade creditDetilBillFacade;

    @Reference(protocol = "dubbo")
    private BalanceDetilBillFacade balanceDetilBillFacade;

    //信用额度列表
    @RequestMapping(value = "userList")
    public String userList(Credit credit, Model model, HttpServletRequest request) {
        credit.setCompanyType(2l);
        PageInfo<Credit> credits = (PageInfo<Credit>) creditFacade.list(credit);
        model.addAttribute("credit", credit);
        model.addAttribute("credits", credits);
        return "credit/userList";
    }

    //设置信用额度
    @RequestMapping(value = "setCredit")
    public String setCredit(Credit credit, Model model, HttpServletRequest requst) {
        String companyCode = credit.getCompanyCode();
        credit = (Credit) creditFacade.findOne(credit);
        credit.setCompanyCode(companyCode);
        model.addAttribute("credit", credit);
        return "credit/setCredit";
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "doSetCredit")
    public Map<String, Object> doSetCredit(Credit credit) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

            Credit cre = new Credit();
            cre = (Credit) creditFacade.findOne(credit);

            creditFacade.update(credit);
            CreditDetilBill creditDetilBill = new CreditDetilBill();
            creditDetilBill.setUserId(credit.getUserId());


            creditDetilBill.setCreateDate(new Date());
            creditDetilBill.setCredit((credit.getTotalCredit() - cre.getTotalCredit()) + "");
            creditDetilBill.setCreditAfter(credit.getTotalCredit() + "");
            creditDetilBill.setCreditBefore(cre.getTotalCredit() + "");
            creditDetilBill.setCompanyCode(credit.getCompanyCode());
            creditDetilBill.setUserId(shiroUser.id);
            creditDetilBill.setType("3");
            creditDetilBill.setPayMode("7");
            //管理员设置
            creditDetilBill.setBigOrderCode("PS-"+ DataUtils.getCurrentTimeStr());
            creditDetilBillFacade.save(creditDetilBill);

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            state = false;
        }
        map.put("state", state);
        return map;
    }


    //查看公司消费详情
    @RequestMapping(value = "CreditDetail")
    public String CreditDetail(Credit credit, Model model) {

        if (credit.getActionTypa() != null && credit.getActionTypa().equals("2")) {
            ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
          //  credit.setUserId(shiroUser.id);
            credit.setCompanyCode(shiroUser.companyCode);
        }

        PageInfo<Credit> credits = (PageInfo<Credit>) creditFacade.companyCreditList(credit);
        model.addAttribute("credits", credits);
        if (credit.getTabNum() == null || credit.getTabNum() == "") {
            credit.setTabNum("1");
        }

        if (credit.getTabNum().equals("1")) {
            credits = (PageInfo<Credit>) creditFacade.companyCreditList(credit);
            model.addAttribute("credits", credits);
        } else if (credit.getTabNum().equals("2") || credit.getTabNum().equals("3")) {     //结算情况

            CreditDetilBill creditDetilBill = new CreditDetilBill();
            creditDetilBill.setCompanyCode(credit.getCompanyCode());

            creditDetilBill.setPageNum(credit.getPageNum());
            creditDetilBill.setPageSize(credit.getPageSize());

            if (credit.getTabNum().equals("2")) {
                creditDetilBill.setType("2");
            } else {
                creditDetilBill.setType("3");
            }

            PageInfo<CreditDetilBill> creditDetilBillPageInfo = (PageInfo<CreditDetilBill>) creditDetilBillFacade.list(creditDetilBill);
            model.addAttribute("creditDetilBillPageInfo", creditDetilBillPageInfo);

        }

        model.addAttribute("credit1", credit);
        model.addAttribute("TabNum", credit.getTabNum());

        return "credit/userDetailList";
    }

}
