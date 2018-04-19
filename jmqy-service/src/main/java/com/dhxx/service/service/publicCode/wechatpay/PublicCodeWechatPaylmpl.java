package com.dhxx.service.service.publicCode.wechatpay;


import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.common.sms.SingletonSMSClient;
import com.dhxx.common.util.DateEditorUtils;
import com.dhxx.common.wechat.WechatUtils;
import com.dhxx.facade.entity.bill.Bill;
import com.dhxx.facade.entity.company.Company;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.permission.Permission;
import com.dhxx.facade.entity.remind.Remind;
import com.dhxx.facade.entity.transport.order.BackOrder;
import com.dhxx.facade.entity.user.User;

import com.dhxx.service.biz.bill.BillBiz;
import com.dhxx.service.biz.company.CompanyBiz;
import com.dhxx.service.biz.credit.CreditBiz;
import com.dhxx.service.biz.credit.CreditDetilBillBiz;
import com.dhxx.service.biz.order.OrderBiz;
import com.dhxx.service.biz.permission.PermissionBiz;
import com.dhxx.service.biz.remind.RemindBiz;
import com.dhxx.service.biz.transport.order.BackOrderBiz;
import com.dhxx.service.biz.user.UserBiz;
import com.dhxx.service.service.order.OrderFacadeImpl;
import com.github.pagehelper.PageInfo;
import org.apache.zookeeper.Op;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.dhxx.facade.service.publicCode.wechatpay.PublicCodeWechatPay;

@Service(protocol = {"dubbo"})
public class PublicCodeWechatPaylmpl implements PublicCodeWechatPay {

    private static Logger log = LoggerFactory.getLogger(PublicCodeWechatPaylmpl.class);


    @Autowired
    private OrderFacadeImpl orderFacadeImpl;

    @Autowired
    private OrderBiz orderBiz;

    @Autowired
    private BillBiz billBiz;

    @Autowired
    private RemindBiz remindBiz;

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private BackOrderBiz backOrderBiz;

    @Autowired
    private CompanyBiz companyBiz;

    @Autowired
    private PermissionBiz permissionBiz;


    @Autowired
    private CreditDetilBillBiz creditDetilBillBiz;



    @Autowired
    private CreditBiz creditBiz;
    //支付状态
    private String re = null;
    //返回支付二维码的地址；
    private String Src = null;


    @Override
    public String NewPayResult(Order order, Long userid, Double paymoney, String phone) {


        String state = null;

        //请求端口查询是否支付成功
        re = ReturnResult(order.getBigOrderCode());

        if (!re.equals("F")) {

            //TODO:未做订单金额效验
            //  ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            //  if ((Long.parseLong(re)) == Money) {}  金额校验
            String orcompanyCode = "";
            Order orUserid = new Order();
            if (phone != null && phone != "") {

                //根据手机号码查id，格式为wx+手机号码
                User us = new User();
                us.setUserAccount("wx" + phone);
                //  List list = (List) userFacade.find(us);
                List list = userBiz.find(us);
                us = (User) list.get(0);
                userid = us.getId();
                orcompanyCode = us.getCompanyCode();

            } else {
                //根据订单号查询下单账户和公司编码
                List<Order> list = (List<Order>) orderBiz.find(order);

                if (!CollectionUtils.isEmpty(list)) {
                    orUserid = list.get(0);
                }
                userid = Long.parseLong(orUserid.getOrderPlacer());
                orcompanyCode = orUserid.getCharterCode();
            }

            order.setPaymentStatus(2L);//已支付
            order.setPaymentType(1L);//现付
            order.setStatus(0L);//待接受
            order.setPlaceTime(new Date());//下单时间
            orderBiz.update(order);


            Bill bill = new Bill();
            bill.setBillType("0");//包车方订单支付


            Double amount = paymoney;
            BigDecimal bigDecimal = new BigDecimal(amount);
            double money = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            bill.setMoney(money + "");//金额
            bill.setTradeMode("2");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联
            bill.setOrderCode(order.getBigOrderCode());//大订单编号
            bill.setThreeOrderNo(order.getBigOrderCode());//第三方订单编号,订单号就是支付接口的订单号
            bill.setUserId(Long.parseLong(userid + ""));//使用ID
            bill.setCompanyCode(orcompanyCode);
            bill.setRecordStatus(0);
            billBiz.save(bill);


            //微信支付结算额度
            if (order.getBigOrderCode().indexOf("CL") > -1) {

                Credit credit = new Credit();

                List<Credit> listCredit = (List<Credit>) creditBiz.findOne(credit);

                if (!CollectionUtils.isEmpty(listCredit)) {

                    credit = listCredit.get(0);

                    CreditDetilBill creditDetilBill = new CreditDetilBill();

                    creditDetilBill.setUserId(credit.getUserId());
                    creditDetilBill.setCreateDate(new Date());
                    creditDetilBill.setCredit(money + "");
                    creditDetilBill.setCreditAfter(credit.getTotalCredit() + "");
                    creditDetilBill.setCreditBefore((credit.getTotalCredit() + money) + "");
                    creditDetilBill.setCompanyCode(credit.getCompanyCode());
                    creditDetilBill.setUserId(userid);
                    creditDetilBill.setType("2");
                    creditDetilBill.setPayMode("2");
                    creditDetilBill.setBigOrderCode(order.getBigOrderCode());
                    creditDetilBillBiz.save(creditDetilBill);
                }
            }
            sendRemind(userid, order);

            String str = new String("【马上走】您的从【" + order.getStartPoint() + "】出发, "
                    + "上车时间为【" + DateEditorUtils.dateToStr(DateEditorUtils.TIMEFORMAT, order.getBoardingTime()) + "】的订单已经下单成功,待运输方安排车辆,请稍后注意查看相关信息，谢谢您的租乘!。");

            try {
                SingletonSMSClient.getClient().sendSMS(new String[]{order.getLinkPhone()}, (str), "", 5);
            } catch (RemoteException e) {
                log.error(e.getMessage());
            }
            state = "success";
        } else {
            state = "fail";

        }

        return state;
    }


    //请求接口并返回支付状态
    //TODO 未做金额支付判断
    public String ReturnResult(String BigOrderCode) {
        //请求端口查询是否支付成功
        WechatUtils wechatUtils = new WechatUtils();
        String url = "http://mobile.jmqyjt.com/Interface/PayStateall.aspx?order=" + BigOrderCode;
        String re = wechatUtils.httpRequest(url, "GET", null);

        re.trim();

        return re;
    }


    public void sendRemind(Long userid, Order order) {

        Order or = new Order();
        Remind remind = new Remind();
        User user = new User();
        BackOrder backOrder = new BackOrder();


        //生成提醒记录，9 表示订单刚支付完
        remind.setRemindTime(new Date());
        remind.setType(9L);
        remind.setStatus(0);


        List<Order> orders = orderBiz.find(order);
        if (!CollectionUtils.isEmpty(orders)) {
            for (int i = 0; i < orders.size(); i++) {
                or = orders.get(i);
                remind.setOrderId(or.getId());
                try {
                    Company company = new Company();
                    company.setCompanyCode(or.getTransporterCode());

                    List<Company> coList = companyBiz.find(company);

                    if (!CollectionUtils.isEmpty(coList)) {
                        Company co = coList.get(0);


                        //查找出角色ID
                        Permission permission = new Permission();
                        permission.setCompanyId(co.getId() + "");
                        permission.setMenuId(20l);

                        permission = permissionBiz.find(permission);
                        //获取对应角色的手机号码
                        User us = new User();
                        us.setRole(permission.getRoleId() + "");
                        us.setCompanyCode(co.getCompanyCode());

                        List<User> usList = userBiz.find(us);

                        if (usList.size() > 0) {
                            for (int j = 0; j < usList.size(); j++) {
                                us = usList.get(j);
                                SingletonSMSClient.getClient().sendSMS(new String[]{us.getPhone()}, "【马上走】有新用户已下单，请注意查看！", "", 5);
                                //  remind.setUserId(us.getId());
                                // remindBiz.save(remind);
                            }
                        }
                    }
                } catch (RemoteException e) {
                    log.error(e.getMessage());
                }
                //通过返程单查找发布订单号人
                if (or.getBackOrderCode() != null && or.getBackOrderCode() != "") {
                    backOrder.setOrderCode(or.getBackOrderCode());

                    PageInfo<BackOrder> backOrder1 = backOrderBiz.list(backOrder);

                    List<BackOrder> list = backOrder1.getList();
                    if (!CollectionUtils.isEmpty(list)) {
                        backOrder = list.get(0);
                        user.setUserAccount(backOrder.getCreateUser());
                    } else {
                        //如果没有则通过公司编码遍历发送给运输方所有人
                        user.setCompanyCode(or.getTransporterCode());
                    }
                    //查询遍历发送
                    remind.setOrderId(or.getId());
                    List<User> users = userBiz.find(user);
                    if (!CollectionUtils.isEmpty(users)) {
                        for (User value : users) {
                            if (value.getId() != null) {
                                remind.setUserId(value.getId());
                                remindBiz.saveOne(remind);
                            }
                        }
                    }
                } else {
                    user = new User();
                    user.setCompanyCode(or.getTransporterCode());
                    user.setType(1);
                    List<User> users = userBiz.find(user);
                    if (!CollectionUtils.isEmpty(users)) {
                        for (User value : users) {
                            if (value.getId() != null) {
                                remind.setUserId(value.getId());
                                remindBiz.saveOne(remind);
                            }
                        }
                    } else {
                        user.setType(null);
                        users = userBiz.find(user);
                        if (!CollectionUtils.isEmpty(users)) {
                            for (User value : users) {
                                if (value.getId() != null) {
                                    remind.setUserId(value.getId());
                                    remindBiz.saveOne(remind);
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}


