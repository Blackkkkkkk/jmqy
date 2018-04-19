package com.dhxx.web.charter.pay;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.balance.BalanceDetilBill;
import com.dhxx.facade.entity.bill.Bill;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.entity.money.Money;
import com.dhxx.facade.entity.money.MoneyDetilBill;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.bill.BillFacade;
import com.dhxx.facade.service.credit.CreditDetilBillFacade;
import com.dhxx.facade.service.credit.CreditFacade;
import com.dhxx.facade.service.money.MoneyDetilBillFacade;
import com.dhxx.facade.service.money.MoneyFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 余额支付接口
 */
@Controller
@RequestMapping("charter/balancePay")
public class balancePay {

    @Reference(protocol = "dubbo")
    private MoneyFacade moneyFacade;

    @Reference(protocol = "dubbo")
    private MoneyDetilBillFacade moneyDetilBillFacade;


    @Reference(protocol = "dubbo")
    private BillFacade billFacade;

    @Reference(protocol = "dubbo")
    private CreditFacade creditFacade;

    @Reference(protocol = "dubbo")
    private CreditDetilBillFacade creditDetilBillFacade;

    @Reference(protocol = "dubbo")
    private OrderFacade orderFacade;

    @Reference(protocol = "dubbo")
    private UserFacade userFacade;

    private static Logger log = LoggerFactory.getLogger(balancePay.class);

    @ResponseBody
    @RequestMapping(value = "balancePay")
    public Map<String, Object> creDitPay(Order order, Model model, HttpServletRequest requst) {
        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

        Map<String, Object> map = new HashMap<String, Object>();

        Money money = new Money();

        money.setCompanyCode(shiroUser.companyCode);


        money = (Money) moneyFacade.findOne(money);


        if (money.getStockMoney() < Double.parseDouble(order.getPrices())) {  //余额不足无法支付
            map.put("state", 2);
        } else {

            try {

                order.setPaymentStatus(2L);//已支付
                orderFacade.update(order);

                DecimalFormat df = new DecimalFormat("######0.00");


                //保存余额额度流水情况表
                MoneyDetilBill moneyDetilBill = new MoneyDetilBill();
                moneyDetilBill.setUserId(shiroUser.id);
                moneyDetilBill.setCompanyCode(shiroUser.companyCode);
                moneyDetilBill.setMoney("-" + order.getPrices());
                moneyDetilBill.setMoneyAfter(df.format(money.getStockMoney() - Double.parseDouble(order.getPrices())));
                moneyDetilBill.setMoneyBefore(money.getStockMoney() + "");
                moneyDetilBill.setCreateDate(new Date());
                moneyDetilBill.setType("2");
                moneyDetilBill.setPayMode("6");
                moneyDetilBill.setBalance(df.format(money.getStockMoney() - Double.parseDouble(order.getPrices())));
                moneyDetilBill.setBigOrderCode(order.getBigOrderCode());

                moneyDetilBillFacade.save(moneyDetilBill);


                User user = new User();
                user.setCompanyCode(shiroUser.companyCode);
                user.setType(1);
                Money mon = new Money();
                mon.setCompanyCode(shiroUser.companyCode);
                mon = (Money) moneyFacade.findOne(mon);

                //变更公司金额
                money.setConsumeMoney(Double.parseDouble(df.format(money.getConsumeMoney() + Double.parseDouble(order.getPrices())))); //已消费总额
                money.setStockMoney(Double.parseDouble(df.format(money.getStockMoney() - Double.parseDouble(order.getPrices()))));   // 剩余额度
                money.setUserId(mon.getUserId());
                moneyFacade.update(money);


                //添加账单流水
                Bill bill = new Bill();
                bill.setBillType("0");//包车方订单支付
                bill.setMoney("-" + order.getPrices() + "");//金额
                bill.setOrderCode(order.getBigOrderCode());//大订单编号
                bill.setUserId(shiroUser.id);//使用ID
                bill.setCompanyCode(shiroUser.companyCode);
                bill.setRecordStatus(0);
                bill.setTradeMode("0");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联
                billFacade.save(bill);

                Credit credit = new Credit();

                user = new User();
                user.setCompanyCode(shiroUser.companyCode);
                user.setType(1);

                List<User> users = (List<User>) userFacade.find(user);
                UserId:
                for (int i = 0; i < users.size(); i++) {
                    user = users.get(i);
                    credit.setUserId(user.getId());
                    credit = (Credit) creditFacade.findOne(credit);
                    if (credit != null) {
                        break UserId;
                    }
                }
                //保存信用额度流水情况表
                CreditDetilBill creditDetilBill = new CreditDetilBill();

                creditDetilBill.setUserId(shiroUser.id);
                creditDetilBill.setCompanyCode(shiroUser.companyCode);
                creditDetilBill.setCredit(order.getPrices() + "");
                creditDetilBill.setCreditAfter(df.format(credit.getStockCredit() + Double.parseDouble(order.getPrices())));
                creditDetilBill.setCreditBefore(credit.getStockCredit() + "");
                creditDetilBill.setCreateDate(new Date());
                creditDetilBill.setType("2");
                creditDetilBill.setPayMode("0");
                creditDetilBill.setBigOrderCode(order.getBigOrderCode());


                //更新对应信用额度
                credit.setStockCredit(credit.getStockCredit() + Double.parseDouble(order.getPrices()));
                credit.setConsumeCredit(credit.getConsumeCredit() - Double.parseDouble(order.getPrices()));
                creditFacade.update(credit);


                //信用分流水账单
                creditDetilBillFacade.save(creditDetilBill);

                map.put("state", 1);

            } catch (Exception e) {
                map.put("state", 3);
                log.debug(e.toString());
            }
        }

        return map;
    }
}
