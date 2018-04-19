package com.dhxx.web.money;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.entity.money.Money;
import com.dhxx.facade.entity.money.MoneyDetilBill;
import com.dhxx.facade.service.money.MoneyDetilBillFacade;
import com.dhxx.facade.service.money.MoneyFacade;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiewq
 * @date 2018/3/16
 * @description 余额额度管理
 */
@Controller
@RequestMapping("money")
public class MoneyController {

    @Reference(protocol = "dubbo")
    private MoneyDetilBillFacade moneyDetilBillFacade;


    @Reference(protocol = "dubbo")
    private MoneyFacade moneyFacade;


    //查看公司余额详情
    @RequestMapping(value = "MoneyDetail")
    public String MoneyDetail(Money  money, Model model) {

        if (money.getActionTypa() != null && money.getActionTypa().equals("2")) {
            ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

            money.setCompanyCode(shiroUser.companyCode);
        }

        PageInfo<Money> moneys = (PageInfo<Money>) moneyFacade.companyCreditList(money);
        model.addAttribute("moneys", moneys);
        if (money.getTabNum() == null || money.getTabNum() == "") {
            money.setTabNum("1");
        }

        if (money.getTabNum().equals("1")) {
            moneys = (PageInfo<Money>) moneyFacade.companyCreditList(money);
            model.addAttribute("moneys", moneys);
        } else if (money.getTabNum().equals("2") || money.getTabNum().equals("3")) {     //结算情况

            MoneyDetilBill moneyDetilBill = new MoneyDetilBill();


            moneyDetilBill.setCompanyCode(money.getCompanyCode());
            moneyDetilBill.setPageNum(money.getPageNum());
            moneyDetilBill.setPageSize(money.getPageSize());

            if (money.getTabNum().equals("2")) {
                moneyDetilBill.setType("2");
            } else {
                moneyDetilBill.setType("3");
            }

            PageInfo<MoneyDetilBill> moneyDetilBillPageInfo = (PageInfo<MoneyDetilBill>) moneyDetilBillFacade.list(moneyDetilBill);
            model.addAttribute("moneyDetilBillPageInfo", moneyDetilBillPageInfo);

        }

        model.addAttribute("money1", money);
        model.addAttribute("TabNum", money.getTabNum());

        return "charter/money/moneyDetailList";
    }



}
