package com.dhxx.web.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dhxx.common.sms.Mo;
import com.dhxx.facade.entity.additional.AdditionalNum;
import com.dhxx.facade.entity.company.Company;
import com.dhxx.facade.entity.gprs.GprsCar;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.order.OrderTimeSet;
import com.dhxx.facade.entity.rule.BillingRate;
import com.dhxx.facade.entity.rule.RouteDeviate;
import com.dhxx.facade.service.additional.AdditionalNumFacade;
import com.dhxx.facade.service.company.CompanyFacade;
import com.dhxx.facade.service.gprs.GprsFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.order.OrderTimeSetFacade;
import com.dhxx.facade.service.rule.BillingRateFacade;
import com.dhxx.facade.service.rule.RouteDeviateFacade;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.rule.Rule;
import com.dhxx.facade.service.rule.RuleFacade;
import com.dhxx.web.BaseController;
import com.github.pagehelper.PageInfo;

/**
 * @author jhy
 * @date 2017/9/25
 * @description
 */
@Controller
@RequestMapping("rule")
@SuppressWarnings("unchecked")
public class RuleController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(RuleController.class);

    @Reference(protocol = "dubbo")
    private RuleFacade ruleFacade;

    @Reference(protocol = "dubbo")
    private GprsFacade gprsFacade;

    @Reference(protocol = "dubbo")
    private OrderTimeSetFacade orderTimeSetFacade;

    @Reference(protocol = "dubbo")
    private AdditionalNumFacade additionalNumFacade;

    @Reference(protocol = "dubbo")
    private CompanyFacade companyFacade;


    @Reference(protocol = "dubbo")
    private RouteDeviateFacade routeDeviateFacade;

    @Reference(protocol = "dubbo")
    private OrderFacade orderFacade;

    @Reference(protocol = "dubbo")
    private BillingRateFacade billingRateFacade;

    @RequestMapping(value = "ruleList")
    public String ruleList(Rule rule, Model model, HttpServletRequest requst) {
        //rule.setRuleType(1l,10l);//地区系数
        rule.setRuleTypeList("1,10");//地区系数
        rule.setParentId(0l);//一级
        PageInfo<Rule> rules = (PageInfo<Rule>) ruleFacade.list(rule);
        model.addAttribute("rule", rule);
        model.addAttribute("rules", rules);
        return "rule/ruleList";
    }

    //保存时间设置
    @RequestMapping(value = "saverule")
    public String saverule(OrderTimeSet orderTimeSet, Model model, HttpServletRequest requst) {

        orderTimeSetFacade.update(orderTimeSet);

        Map<String, Object> map = new HashMap<String, Object>();

        map = orderTimeSetFacade.find(orderTimeSet);
        model.addAttribute("map", map);
        model.addAttribute("state", true);

        return "rule/TimeSet";
    }

    //跳转时间设置页面
    @RequestMapping(value = "Timerule")
    public String Timerule(OrderTimeSet orderTimeSet, Model model, HttpServletRequest requst) {

        Map<String, Object> map = new HashMap<String, Object>();
        map = orderTimeSetFacade.find(orderTimeSet);
        model.addAttribute("map", map);

        return "rule/TimeSet";
    }

    //跳转附加选项设置页面
    @RequestMapping(value = "Additionalrule")
    public String Additionalrule(AdditionalNum additionalNum, Model model, HttpServletRequest requst) {

        Map<String, Object> map = new HashMap<String, Object>();
        map = additionalNumFacade.find(additionalNum);
        model.addAttribute("map", map);

        return "rule/AdditionalSet";
    }

    //保存附加选项设置页面
    @RequestMapping(value = "saveadditionalrule")
    public String saveadditionalrule(AdditionalNum additionalNum, Model model, HttpServletRequest requst) {

        additionalNumFacade.update(additionalNum);

        Map<String, Object> map = new HashMap<String, Object>();

        map = additionalNumFacade.find(additionalNum);
        model.addAttribute("map", map);
        model.addAttribute("state", true);

        return "rule/AdditionalSet";
    }

    @RequestMapping(value = "form")
    public String form(Rule rule, Model model, HttpServletRequest requst) {
        if (rule.getId() != null) {
            List<Rule> rules = (List<Rule>) ruleFacade.find(rule);
            rule = rules.get(0);
        }
        model.addAttribute("rule", rule);
        return "rule/form";
    }

    @RequestMapping(value = "detail/{id}")
    public String detail(Rule rule, Model model, HttpServletRequest requst) {
        List<Rule> rules = (List<Rule>) ruleFacade.find(rule);
        rule = rules.get(0);
        model.addAttribute("rule", rule);

        Rule r = new Rule();
        r.setParentId(rule.getId());
        rules = (List<Rule>) ruleFacade.find(r);

        if (CollectionUtils.isEmpty(rules)) {

            r.setParentId(-1l);
            rules = (List<Rule>) ruleFacade.find(r);

            for (int i = 0; i < rules.size(); i++) {
                Rule rr = rules.get(i);
                rr.setId(null);
                rr.setParentId(rule.getId());
                // 16 是空接单里程单价，由运输方设置，平台方不需要初始化
                if(rr.getRuleType().equals("16")){
                }else{
                    ruleFacade.save(rr);
                }

            }
            r.setParentId(rule.getId());
            rules = (List<Rule>) ruleFacade.find(r);
        }

        model.addAttribute("rules", rules);

        return "rule/detail";
    }

    //新增
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Map<String, Object> saveOrUpdate(Rule rule) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        //16为空接单，运输方设置需要保存公司编码
        if(rule.getRuleType()==16l){
            rule.setCompanyCode(shiroUser.companyCode);
            rule.setParentId(0l);
        }
        try {
            if (rule.getId() != null) {
                ruleFacade.update(rule);
            } else {
                rule = (Rule) ruleFacade.save(rule);
            }
        } catch (Exception e) {
            state = false;
            log.error(e.getMessage());
        }
        map.put("state", state);
        map.put("rule", rule);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "doSetRule")
    public Map<String, Object> doSetRule(Rule rule) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            ruleFacade.update(rule);
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public Map<String, Object> del(Rule rule) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            ruleFacade.delete(rule);
        } catch (Exception e) {
            log.debug(e.getMessage());
            state = false;
        }
        map.put("state", state);
        return map;
    }

    //设置优惠率页面
    @RequestMapping(value = "setDiscount", method = RequestMethod.GET)
    public String setDiscount(Rule rule, Model model, HttpServletRequest requst) {
        rule.setRuleType(9l);
        List<Rule> rules = (List<Rule>) ruleFacade.find(rule);
        if (!CollectionUtils.isEmpty(rules)) {
            rule = rules.get(0);
        } else {
            rule.setRuleValue("优惠率");
            rule.setCoefficient(1d);
            rule.setParentId(0l);
            rule = (Rule) ruleFacade.save(rule);
        }
        model.addAttribute("rule", rule);
        return "rule/setDiscount";
    }

    //设置优惠率
    @ResponseBody
    @RequestMapping(value = "doSetDiscount")
    public boolean doSetDiscount(Rule rule) {
        try {
            ruleFacade.update(rule);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }


    //设置线路偏差值页面
    @RequestMapping(value = "routeDeviate")
    public String routeDeviate(HttpServletRequest requst, RouteDeviate routeDeviate, Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        map = routeDeviateFacade.find(routeDeviate);


      /*  GprsCar gprsCar = new GprsCar();
        gprsCar.setOrderCode("DH-201801311737000_01");

        List<Map<String, Object>> listMap = gprsFacade.find(gprsCar);
        System.out.println(listMap.get(0));
*/
        model.addAttribute("map", map);

        return "rule/routeDeviate";
    }


    //设置线路偏差值

    @RequestMapping(value = "setRouteDeviate")
    public String setRouteDeviate(HttpServletRequest requst, RouteDeviate routeDeviate, Model model) {
        try {
            routeDeviateFacade.update(routeDeviate);
            model.addAttribute("state", true);

            Map<String, Object> map = new HashMap<String, Object>();
            map = routeDeviateFacade.find(routeDeviate);

            model.addAttribute("map", map);


        } catch (Exception e) {

            model.addAttribute("state", false);
        }
        return "rule/routeDeviate";
    }

    //设置退款界面
    @RequestMapping(value = "refund")
    public String refund(Rule rule, Model model) {


        rule.setRuleType(11l);//一级
        rule.setParentId(0l);
        PageInfo<Rule> rules = (PageInfo<Rule>) ruleFacade.listCompany(rule);
        model.addAttribute("rule", rule);
        model.addAttribute("rules", rules);

        return "rule/refundList";
    }

    //新增退款企业
    @RequestMapping(value = "refundSavePage")
    public String refundSavePage(Company company, Model model) {

        company.setActionType("1");
        List<Company> companyList = (List<Company>) companyFacade.find(company);
        model.addAttribute("companyList", companyList);

        return "rule/refundForm";
    }

    //保存退款企业
    @ResponseBody
    @RequestMapping(value = "refundSave")
    public Map refundSave(Model model, Rule rule) {

        String state = "0";

        //11为退款
        String RuleValue = rule.getRuleValue();
        rule.setRuleValue(null);
        rule.setRuleType(11L);

        try {
            List<Rule> list = ruleFacade.findRule(rule);

            if (list.size() > 0) {
                state = "2";  //存在记录，返回状态码2
            } else {

                rule.setRuleValue(RuleValue);
                ruleFacade.save(rule);
                state = "1";
            }
        } catch (Exception e) {
            state = "0";
        }

        //新增的时候对应增加最大值和最小值
        Rule ru = new Rule();
        ru.setParentId(rule.getId());
        ru.setRuleType(18l);
        ru.setDefaultRange(100+"");
        ru.setToRange(200+"");
        ru.setRuleValue("退款手续费大小限额");
        ruleFacade.save(ru);

        Map map = new HashMap();
        map.put("state", state);

        return map;
    }

    @RequestMapping(value = "refundDetail")
    public String refundDetail(Rule rule, Model model) {

        try {
            List<Rule> rules = (List<Rule>) ruleFacade.find(rule);
            rule = rules.get(0);
            Company company = new Company();
            company.setCompanyCode(rule.getCompanyCode());
            ArrayList arrayList = (ArrayList) companyFacade.find(company);
            if (arrayList.size() > 0) {
                company = (Company) arrayList.get(0);
            }
            rule.setCompanyName(company.getCompanyName());


            model.addAttribute("rule", rule);


            Rule r = new Rule();
            r.setParentId(rule.getId());
            rules = (List<Rule>) ruleFacade.find(r);

            if (CollectionUtils.isEmpty(rules)) {
                r.setParentId(-1l);
                rules = (List<Rule>) ruleFacade.find(r);

                for (int i = 0; i < rules.size(); i++) {
                    Rule rr = rules.get(i);
                    rr.setId(null);
                    rr.setParentId(rule.getId());
                    ruleFacade.save(rr);
                }
                r.setParentId(rule.getId());
                rules = (List<Rule>) ruleFacade.find(r);
            }

            model.addAttribute("rules", rules);

            Rule rd = new Rule();
            rd.setParentId(rule.getId());
            rd.setRuleType(17L);
            List<Rule> rds = (List<Rule>) ruleFacade.find(rd);

            if (rds.size() > 0) {
                Rule rr = (Rule) rds.get(0);
                model.addAttribute("refundId", rr.getId());
                model.addAttribute("refundMin",rr.getDefaultRange());
                model.addAttribute("refundMax",rr.getToRange());
            }

        } catch (Exception e) {
            log.debug(e.toString());
        }


        return "rule/refundDetail";
    }

    //退款列表
    @RequestMapping(value = "order")
    public String charter(Order order, Model model, HttpServletRequest requst) {

        order.setIds("1,0");
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.refund(order);
        model.addAttribute("order", order);
        model.addAttribute("orders", orders);
        return "refund/sysOrder";
    }

    //设置途经里程计费比率
    @RequestMapping(value = "billingRate")
    public String billingRate(HttpServletRequest requst, BillingRate billingRate, Model model) {
        try {
            billingRate.setRuleType("12");
            billingRate = billingRateFacade.find(billingRate);
            model.addAttribute("billingRate", billingRate);
        } catch (Exception e) {
        }
        return "rule/billingRate";
    }

    //保存途经里程计费比率

    @RequestMapping(value = "setBillingRate")
    public String setBillingRate(HttpServletRequest requst, BillingRate billingRate, Model model) {
        Boolean state = true;
        try {

            billingRate.setRuleType("12");
            billingRateFacade.update(billingRate);

        } catch (Exception e) {
            state = false;
        }
        billingRate = billingRateFacade.find(billingRate);
        model.addAttribute("billingRate", billingRate);
        model.addAttribute("state", state);

        return "rule/billingRate";
    }


    //设置包车天数系数
    @RequestMapping(value = "charteredBusCoefficient")
    public String charteredBusCoefficient(HttpServletRequest requst, BillingRate billingRate, Model model) {
        try {
            billingRate.setRuleType("13");
            billingRate = billingRateFacade.find(billingRate);
            model.addAttribute("billingRate", billingRate);
        } catch (Exception e) {
        }
        return "rule/charteredBusCoefficient";
    }


    //保存包车天数系数
    @RequestMapping(value = "setCharteredBusCoefficient")
    public String setcharteredBusCoefficient(HttpServletRequest requst, BillingRate billingRate, Model model) {
        Boolean state = true;
        try {
            billingRate.setRuleType("13");
            billingRateFacade.update(billingRate);

        } catch (Exception e) {
            state = false;
        }
        billingRate = billingRateFacade.find(billingRate);
        model.addAttribute("billingRate", billingRate);
        model.addAttribute("state", state);

        return "rule/charteredBusCoefficient";
    }



}
