package com.dhxx.web.charter.order;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.dhxx.facade.entity.balance.BalanceDetilBill;
import com.dhxx.facade.entity.company.Company;
import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.entity.money.Money;
import com.dhxx.facade.entity.money.MoneyDetilBill;
import com.dhxx.facade.entity.refund.Refund;
import com.dhxx.facade.entity.rule.Rule;
import com.dhxx.facade.service.balance.BalanceDetilBillFacade;
import com.dhxx.facade.service.company.CompanyFacade;
import com.dhxx.facade.service.credit.CreditDetilBillFacade;
import com.dhxx.facade.service.money.MoneyDetilBillFacade;
import com.dhxx.facade.service.money.MoneyFacade;
import com.dhxx.facade.service.publicCode.wechatpay.PublicCodeWechatPay;
import com.dhxx.facade.service.refund.RefundFacade;
import com.dhxx.facade.service.rule.RuleFacade;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.common.constant.Constant;
import com.dhxx.common.util.DataUtils;
import com.dhxx.facade.entity.additional.AdditionalNum;
import com.dhxx.facade.entity.bill.Bill;
import com.dhxx.facade.entity.charter.complaint.Complaint;
import com.dhxx.facade.entity.charter.evaluate.Evaluate;
import com.dhxx.facade.entity.charter.invoice.Address;
import com.dhxx.facade.entity.charter.invoice.Invoice;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.line.Line;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.order.OrderTimeSet;
import com.dhxx.facade.entity.remind.Remind;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.facade.entity.transport.order.BackOrder;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.additional.AdditionalNumFacade;
import com.dhxx.facade.service.bill.BillFacade;
import com.dhxx.facade.service.charter.complaint.ComplaintFacade;
import com.dhxx.facade.service.charter.evaluate.EvaluateFacade;
import com.dhxx.facade.service.charter.invoice.AddressFacade;
import com.dhxx.facade.service.charter.invoice.InvoiceFacade;
import com.dhxx.facade.service.credit.CreditFacade;
import com.dhxx.facade.service.line.LineFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.order.OrderTimeSetFacade;
import com.dhxx.facade.service.remind.RemindFacade;
import com.dhxx.facade.service.transport.car.CarFacade;
import com.dhxx.facade.service.transport.order.BackOrderFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.dhxx.web.utils.SMSNotifyUtil;
import com.github.pagehelper.PageInfo;

/**
 * @author dingbin
 * @description
 */
@Controller
@RequestMapping("charter/order")
@SuppressWarnings("unchecked")
public class OrderController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Reference(protocol = "dubbo")
    private OrderFacade orderFacade;

    @Reference(protocol = "dubbo")
    private CompanyFacade companyFacade;

    @Reference(protocol = "dubbo")
    private RemindFacade remindFacade;

    @Reference(protocol = "dubbo")
    private UserFacade userFacade;

    @Reference(protocol = "dubbo")
    private BillFacade billFacade;

    @Reference(protocol = "dubbo")
    private CreditFacade creditFacade;

    @Reference(protocol = "dubbo")
    private BackOrderFacade backOrderFacade;

    @Reference(protocol = "dubbo")
    private CarFacade carFacade;

    @Reference(protocol = "dubbo")
    private LineFacade lineFacade;

    @Reference(protocol = "dubbo")
    private EvaluateFacade evaluateFacade;

    @Reference(protocol = "dubbo")
    private ComplaintFacade complaintFacade;

    @Reference(protocol = "dubbo")
    private InvoiceFacade invoiceFacade;


    @Reference(protocol = "dubbo")
    private AddressFacade addressFacade;

    @Reference(protocol = "dubbo")
    private OrderTimeSetFacade orderTimeSetFacade;


    @Reference(protocol = "dubbo")
    private AdditionalNumFacade additionalNumFacade;


    @Reference(protocol = "dubbo")
    private RefundFacade refundFacade;

    @Reference(protocol = "dubbo")
    private RuleFacade ruleFacade;

    @Reference(protocol = "dubbo")
    private BalanceDetilBillFacade balanceDetilBillFacade;

    @Reference(protocol = "dubbo")
    private CreditDetilBillFacade creditDetilBillFacade;

    @Reference(protocol = "dubbo")
    private MoneyFacade moneyFacade;

    @Reference(protocol = "dubbo")
    private MoneyDetilBillFacade moneyDetilBillFacade;

    @Reference(protocol = "dubbo")
    private PublicCodeWechatPay publicCodeWechatPay;

    @Autowired
    private SMSNotifyUtil sMSNotifyUtil;


    //保存订单的ajax请求
    @ResponseBody
    @RequestMapping(value = "saveOrder", method = RequestMethod.POST)
    public Map<String, Object> saveOrder(Order order, AdditionalNum additionalNum, OrderTimeSet orderTimeSet, Model model, HttpServletRequest request) {


        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapTimeSet = new HashMap<String, Object>();

        int OrderCodeNum = 0;//当在途订单再来一单的时候，大订单号不变，小订单号累加数量
        mapTimeSet = orderTimeSetFacade.find(orderTimeSet);
        boolean state = true;
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String bigOrderCode = order.getBigOrderCode();
        try {
            if (!StringUtils.isEmpty(bigOrderCode)) {
                Order o = new Order();
                o.setBigOrderCode(bigOrderCode);
                o.setIds("-4,-3,-2,-1,4");
                o.setRecordStatus(1);

                orderFacade.update(o);
                //查找相同大订单号的条数
                OrderCodeNum = (orderFacade.selectCount(o)).intValue();

            } else {
                bigOrderCode = Constant.OR_CODE + DataUtils.getCurrentTimeStr();
                order.setBigOrderCode(bigOrderCode);
            }
            order.setLinkMan(shiroUser.userName);
            order.setLinkPhone(shiroUser.phone);
            order.setOrderPlacer(shiroUser.id + "");
            order.setCharterCode(shiroUser.companyCode);
            order.setRecordStatus(0);
            order.setPaymentStatus(1L);//待支付


            Double a = (1 + Integer.parseInt(mapTimeSet.get("middlesettime") + "") * 0.01) * 2;
            System.out.println("a=" + a);

            if (order.getFlag() == 1) {//预定

                //计算预留上车时间和预留下车时间>>>start
                double duration = (order.getDuration() != null ? order.getDuration() : 0) / 60.0;
                order.setReserveUpTime(DataUtils.getByHour(order.getBoardingTime(), -(Integer.parseInt((mapTimeSet.get("beforesettime")) + ""))));//单前预留时间2小时
                if (!StringUtils.isEmpty(order.getEndPoint())) {
                    //TripType == 1:单程从上车时间算,==2：往返的从返回的上车时间
                    if (order.getTripType() == 1) {
                        order.setReserveOffTime(DataUtils.getByMinute(order.getBoardingTime(), (int) Math.ceil(duration * ((1 + Integer.parseInt(mapTimeSet.get("middlesettime") + "") * 0.01) * 2)) + (Integer.parseInt((mapTimeSet.get("aftersettime")) + "")) * 60));
                    } else {
                        order.setReserveOffTime(DataUtils.getByMinute(order.getTrackBoardTime(), (int) Math.ceil(duration * (1 + Integer.parseInt(mapTimeSet.get("middlesettime") + "") * 0.01))));
                    }
                }
                if (order.getCharterDays() != null) {
                    order.setReserveOffTime(DataUtils.format(DataUtils.getByDay(order.getBoardingTime(), order.getCharterDays().intValue() - 1), "yyyy-MM-dd 23:59:59"));
                }
                //计算预留上车时间和预留下车时间>>>end
                String[] carCodes = order.getCarCode().split(",");
                String[] pricess = order.getPrices().split(",");
                String[] backOrderCodes = order.getBackOrderCode().split(",");


                //把车辆编码，车辆数目，车辆类型做分割
                List carCodesList = java.util.Arrays.asList(order.getCarCode().split(","));
                List carAmountsList = java.util.Arrays.asList(order.getCarAmounts().split(","));
                List carTypesList = java.util.Arrays.asList(order.getCarTypes().split(","));


                Map<String, Integer> map1 = new HashMap<String, Integer>();

                String recarAmounts = "";
                String recarTypes = "";

                for (int i = 0; i < backOrderCodes.length; i++) {
                    Order od = new Order();
                    BeanUtils.copyProperties(order, od);
                    od.setAmount(Double.parseDouble(pricess[i]));
                    od.setPrices(order.getAmount() + "");//在每个订单保存总价格
                    od.setOrderCode(bigOrderCode + "_" + String.format("%02d", OrderCodeNum + i + 1));
                    od.setStatus(-1L);//匹配中
                    od.setMatchTime(new Date());
                    if ("0".equals(backOrderCodes[i])) {
                        Car car = new Car();
                        car.setCarCode(carCodes[i]);
                        List<Car> carList = (List<Car>) carFacade.find(car);
                        car = carList.get(0);

                        od.setBackOrderCode(null);
                        od.setCarCode(car.getCarCode());
                        od.setCarNum(car.getCarNum());
                        od.setCarType(car.getCarType());
                        od.setDriverCode(car.getDriverCode());
                        od.setTransporterCode(car.getCompanyCode());
                    } else {
                        BackOrder backOrder = new BackOrder();
                        backOrder.setOrderCode(backOrderCodes[i]);
                        List<BackOrder> backOrderList = (List<BackOrder>) backOrderFacade.find(backOrder);
                        backOrder = backOrderList.get(0);
                        od.setBackOrderCode(backOrderCodes[i]);
                        od.setCarCode(backOrder.getCarCode());
                        od.setCarNum(backOrder.getCarNum());
                        od.setCarType(backOrder.getCarType());
                        od.setDriverCode(backOrder.getDriverCode());
                        od.setTransporterCode(backOrder.getCreateCompany());
                        //计算预留上车时间和预留下车时间>>>start
                        //	od.setReserveOffTime(DataUtils.getByMinute(backOrder.getLatestDepartureTime(), (int) Math.ceil(duration*1.25)+2*60));
                        //计算预留上车时间和预留下车时间>>>end
                        BackOrder bo = new BackOrder();
                        bo.setOrderCode(backOrder.getOrderCode());
                        bo.setStatus(1);
                        backOrderFacade.update(bo);

                    }

                    map1 = getMap(carCodesList);
                    recarTypes = "";
                    recarAmounts = "";
                    for (String key : map1.keySet()) {

                        recarTypes += key + ",";
                        recarAmounts += map1.get(key) + ",";
                    }
                    od.setCarAmounts(recarAmounts);
                    od.setCarTypes(recarTypes);

                    orderFacade.save(od);
                }


                //选择车辆的时候判断是否数量全部满足
                Order pushOr = new Order();
                BeanUtils.copyProperties(order, pushOr);

                //统计多种车型情况下的全部总数目
                int num = 0;
                for (Object k : carAmountsList) {
                    num += Integer.parseInt(k + "");
                }
                //检查选择车辆的数目和填写车辆的数目是否一致
                if (num > carCodesList.size()) {
                    if (carAmountsList.size() == 1) {//如果车型只有一种的情况

                        //当车辆选择少于填写车辆数目的时候
                        if (Integer.parseInt(carAmountsList.get(0) + "") > carCodesList.size()) {
                            pushOr.setCarAmounts((Integer.parseInt(carAmountsList.get(0) + "") - carCodesList.size()) + ",");//填写车辆数目减去所选车型
                            pushOr.setCarTypes(carTypesList.get(0) + "");
                        }
                    } else {


                        map1 = getMap(carCodesList);
                        recarAmounts = "";
                        recarTypes = "";
                        //判断每种型号的差数
                        for (int j = 0; j < carTypesList.size(); j++) {
                            //如果选择了N种类型，实际出现了少于N的时候不做判断会报空指针
                            if (map1.containsKey(carTypesList.get(j))) {
                                System.out.println("11");
                                if (Integer.parseInt(carAmountsList.get(j) + "") > map1.get(carTypesList.get(j)).intValue()) {
                                    recarAmounts += (Integer.parseInt(carAmountsList.get(j) + "") - map1.get(carTypesList.get(j)).intValue()) + ",";
                                    recarTypes += carTypesList.get(j) + ",";
                                }
                            } else {
                                recarAmounts += Integer.parseInt(carAmountsList.get(j) + "") + ",";
                                recarTypes += carTypesList.get(j) + ",";
                            }

                        }
                        pushOr.setCarAmounts(recarAmounts);
                        pushOr.setCarTypes(recarTypes);
                    }

                    pushOr.setCarCode("");
                    //区别上面的大订单号，当前时间加1秒
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, 0);
                    cal.add(Calendar.SECOND, 1);
                    String time = new SimpleDateFormat("yyyyMMddhhmmsss").format(cal.getTime());

                    String bigOrderCode1 = Constant.OR_CODE + time;
                    pushOr.setBigOrderCode(bigOrderCode1);
                    pushOr.setOrderCode(bigOrderCode1 + "_" + String.format("%02d", num));
                    pushOr.setPrices("");
                    pushOr.setCarCode("");
                    pushOr.setSeatNumber(null);
                    pushOr.setReserveOffTime(null);
                    pushOr.setReserveUpTime(null);
                    pushOr.setAmount(null);
                    pushOr.setBackOrderCode(null);
                    pushOr.setStatus(-2L);//待匹配

                    orderFacade.save(pushOr);

                }

            } else {//没有选择车辆直接发布
                order.setOrderCode(bigOrderCode + "_01");
                order.setStatus(-2L);//待匹配
                orderFacade.save(order);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            state = false;
            e.printStackTrace();
        }
        map.put("bigOrderCode", bigOrderCode);
        map.put("state", state);
        return map;
    }


    //根据车牌号从数据库查找车辆类型
    public Map<String, Integer> getMap(List carCodesList) {
        System.out.println("carCodesList=" + carCodesList);
        //根据carCode从数据库查找出对应的车辆的类型放进list中
        List<String> l = new ArrayList<String>();
        for (Object x : carCodesList) {
            l.add(carFacade.findcartype(x + ""));
        }
        //根据查询出来的车辆型号做统计
        Map<String, Integer> map1 = new HashMap<String, Integer>();
        for (String item : l) {
            if (map1.containsKey(item)) {
                map1.put(item, map1.get(item).intValue() + 1);
            } else {
                map1.put(item, new Integer(1));
            }

        }

        return map1;
    }


    //立即预约
    @RequestMapping(value = "detailRelease")
    public String detailRelease(Order order, Model model, HttpServletRequest request) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        if (!StringUtils.isEmpty(order.getBigOrderCode()) || !StringUtils.isEmpty(order.getOrderCode())) {
            List<Order> orders = (List<Order>) orderFacade.find(order);
            if (!CollectionUtils.isEmpty(orders)) {
                order = orders.get(0);
            }
        }
        User user = new User();
        user.setId(shiroUser.id);
        List<User> userList = (List<User>) userFacade.find(user);
        if (!CollectionUtils.isEmpty(userList)) {
            user = userList.get(0);
        }

        Company company = new Company();
        company.setCompanyCode(order.getCharterCode());
        List<Company> com = (List<Company>) companyFacade.find(company);
        if (!com.isEmpty()) {
            company = com.get(0);
        }

        model.addAttribute("company", company);
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        return "charter/order/release_detail";
    }

    //统计订单数量
    @ResponseBody
    @RequestMapping(value = "statistics", method = RequestMethod.POST)
    public Integer statistics(Order order, Model model) {
        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            order.setCharterCode(shiroUser.companyCode);
            //   order.setOrderPlacer(shiroUser.id+"");
            return (Integer) orderFacade.statistics(order);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    //进入发布订单页面或者修改重新发布或者再来一单的复制
    @RequestMapping(value = "pushOrder")
    public String pushOrder(Order order, OrderTimeSet orderTimeSet, Model model, HttpServletRequest request, Car car) {
        Map<String, Object> map = new HashMap<String, Object>();

        map = orderTimeSetFacade.find(orderTimeSet);

        List<Car> carsList = (List<Car>) carFacade.carList(car);
        //	carFacade.carList(car);
        if (!StringUtils.isEmpty(order.getOrderCode()) || !StringUtils.isEmpty(order.getBigOrderCode())) {
            List<Order> orders = (List<Order>) orderFacade.find(order);
            if (!CollectionUtils.isEmpty(orders)) {
                Integer flag = order.getFlag();
                order = orders.get(0);
                //flag == 4:表示再来一单，复制
                if (flag != null && flag == 4) {
                    order.setId(null);
                    order.setOrderCode(null);
                    order.setBigOrderCode(null);
                    order.setBoardingTime(null);
                    order.setDebusTime(null);
                    order.setTrackTime(null);
                }
                if (flag != null && flag == 5) {
                    order.setId(null);
                    order.setOrderCode(null);
                    order.setBoardingTime(null);
                    order.setDebusTime(null);
                    order.setTrackBoardTime(null);
                    order.setTrackTime(null);
                }
                //修改订单或者再来一单的时候，上车时间为过去的时间，就置为空，让用户自己选择
                //if(order.getBoardingTime() != null && order.getBoardingTime().getTime() < System.currentTimeMillis()){
                if (order.getBoardingTime() != null) {
                    order.setBoardingTime(null);
                    order.setTrackBoardTime(null);
                    order.setDebusTime(null);
                    order.setTrackTime(null);
                    System.out.println("2");
                }
            }
        } else {
            //设置默认
            order.setTripType(1L);
            order.setCharterType(1L);
        }
        model.addAttribute("order", order);
        model.addAttribute("carsTypeList", carsList);
        model.addAttribute("map", map);
        return "charter/order/release";
    }


    //订单列表
    @RequestMapping(value = "list/{ids}")
    public String list(Order order, Model model, HttpServletRequest request) {
        model.addAttribute("ids", order.getIds());
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        order.setCharterCode(shiroUser.companyCode);
        //	order.setOrderPlacer(shiroUser.id+"");
        if ("3".equals(order.getIds())) {
            order.setIds("-4,3,4,7,6");//取消和完成的订单
        } else if ("2".equals(order.getIds())) {
            order.setIds("1,2,5");//在途订单
        } else {
        }
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
        model.addAttribute("order", order);
        model.addAttribute("orders", orders);
        return "charter/order/list";
    }

    @ResponseBody
    @RequestMapping(value = "searchRelease")
    public Map<String, Object> searchRelease(Order order, Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        try {
            //推荐车辆
            if (!StringUtils.isEmpty(order.getStartPoint()) && null != order.getPopulation()) {
                order.setTop(4);
                List<Order> carList = (List<Order>) orderFacade.findCarByScore(order);
                map.put("carList", carList);
                //保存或者更新搜索路线
                if (shiroUser != null) {
                    Line line = new Line();
                    BeanUtils.copyProperties(order, line);
                    line.setId(null);
                    line.setType(2);//类型：1收藏，2搜索
                    line.setCreateCompany(shiroUser.companyId + "");
                    line.setCreateUser(shiroUser.id + "");
                    lineFacade.saveOrUpdate(line);
                }
            }

            //推荐返程路线
            if (order.getTripType() == null || order.getTripType() == 1) {
                BackOrder backOrder = new BackOrder();
                backOrder.setStartPoint(order.getStartPoint());
                backOrder.setStartArea(order.getStartArea());
                backOrder.setEndPoint(order.getEndPoint());
                backOrder.setEndArea(order.getEndArea());
                backOrder.setBoardingTime(order.getBoardingTime());//上车时间
                backOrder.setStatus(0);//待匹配
                backOrder.setSeatNumber(order.getPopulation() != null ? order.getPopulation().intValue() : null);
                backOrder.setTop(2);
                PageInfo<BackOrder> backOrderList = (PageInfo<BackOrder>) backOrderFacade.list(backOrder);
                map.put("backOrderList", backOrderList.getList());
            }
        } catch (Exception e) {
            state = false;
            log.info(e.getMessage());
            e.printStackTrace();
        }
        map.put("state", state);
        return map;
    }


    @ResponseBody
    @RequestMapping(value = "saveReleaseDetail", method = RequestMethod.POST)
    public Map<String, Object> saveReleaseDetail(Order order, Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer state = 1;
        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            if (order.getPayType() == 1) {//全额支付
                if (!StringUtils.isEmpty(order.getBigOrderCode())) {
                    orderFacade.update(order);
                }
                state = 2;//跳去微信扫描支付
            } else if (order.getPayType() == 2) {  //余额支付
                Money money = new Money();
                money.setCompanyCode(shiroUser.companyCode);
                money = (Money) moneyFacade.findOne(money);

                if (Double.parseDouble(order.getPrices()) > money.getStockMoney()) {
                    state = 4;//余额额度不够
                } else {

                    doSave(shiroUser, order, null, money);
                    //发送短信
                    publicCodeWechatPay.sendRemind(shiroUser.id,order);
                    sMSNotifyUtil.send(order, 5);

                }
            } else if (order.getPayType() == 3 || order.getPayType() == 4) { //记账支付
                Credit credit = new Credit();
                //	credit.setUserId(shiroUser.id);
                credit.setCompanyCode(shiroUser.companyCode);
                credit = (Credit) creditFacade.findOne(credit);
                if (Double.parseDouble(order.getPrices()) > credit.getStockCredit()) {
                    state = 3;//信用额度不够
                } else {

                    doSave(shiroUser, order, credit, null);

                    publicCodeWechatPay.sendRemind(shiroUser.id,order);
                    sMSNotifyUtil.send(order, 5);

                }
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            state = 99;
            e.printStackTrace();
        }
        map.put("state", state);
        return map;
    }


    private void doSave(ShiroUser shiroUser, Order order, Credit credit, Money money) {

        if (!StringUtils.isEmpty(order.getBigOrderCode())) {

            if(credit != null){
                order.setPaymentType(2L);//月结
            }else {
                order.setPaymentType(3L);//余额支付
                order.setPaymentStatus(2L); //已支付
            }

            order.setStatus(0L);//待接受
            order.setPlaceTime(new Date());//下单时间

            orderFacade.update(order);

            Order o = new Order();
            o.setBigOrderCode(order.getBigOrderCode());
         //   sMSNotifyUtil.send(o, 1);//包车方下单之后通知他的车辆和司机的联系方式;
        }


        //添加账单流水
        Bill bill = new Bill();
        bill.setBillType("0");//包车方订单支付
        bill.setMoney("-" + order.getPrices() + "");//金额
        bill.setOrderCode(order.getBigOrderCode());//大订单编号
        bill.setUserId(shiroUser.id);//使用ID
        bill.setCompanyCode(shiroUser.companyCode);
        bill.setRecordStatus(0);


        String balanceBefore = null;              //调整前
        String balance = order.getPrices() + "";  //调整额
        Double Finallymoney = 0.0;                 //调整后
        String balanceAfter = null;

        if (credit != null) {

            bill.setTradeMode("1");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联
            billFacade.save(bill);


            balanceBefore = credit.getStockCredit() + "";  //调整前
            Finallymoney = Double.parseDouble(credit.getStockCredit() + "") - Double.parseDouble(order.getPrices() + "");
            balanceAfter = Finallymoney + "";   //调整后

            //更新对应信用额度
            credit.setStockCredit(credit.getStockCredit() - Double.parseDouble(order.getPrices()));
            credit.setConsumeCredit(credit.getConsumeCredit() + Double.parseDouble(order.getPrices()));
            creditFacade.update(credit);

            //保存信用额度流水情况表
            CreditDetilBill creditDetilBill = new CreditDetilBill();

            creditDetilBill.setUserId(shiroUser.id);
            creditDetilBill.setCompanyCode(shiroUser.companyCode);
            creditDetilBill.setCredit("-" + balance);
            creditDetilBill.setCreditAfter(balanceAfter);
            creditDetilBill.setCreditBefore(balanceBefore);
            creditDetilBill.setCreateDate(new Date());
            creditDetilBill.setType("1");
            creditDetilBill.setPayMode("6");
            creditDetilBill.setBigOrderCode(order.getBigOrderCode());

            creditDetilBillFacade.save(creditDetilBill);
        } else {

            bill.setTradeMode("0");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联
            billFacade.save(bill);

            balanceBefore = money.getStockMoney() + "";  //调整前
            Finallymoney = Double.parseDouble(money.getStockMoney() + "") - Double.parseDouble(order.getPrices() + "");
            balanceAfter = Finallymoney + "";   //调整后

            //更新对应信用额度
            money.setStockMoney(money.getStockMoney() - Double.parseDouble(order.getPrices()));
            money.setConsumeMoney(money.getConsumeMoney() + Double.parseDouble(order.getPrices()));
            moneyFacade.update(money);


            //保存余额额度流水情况表
            MoneyDetilBill moneyDetilBill = new MoneyDetilBill();
            moneyDetilBill.setUserId(shiroUser.id);
            moneyDetilBill.setCompanyCode(shiroUser.companyCode);
            moneyDetilBill.setMoney("-" + balance);
            moneyDetilBill.setMoneyAfter(balanceAfter);
            moneyDetilBill.setMoneyBefore(balanceBefore);
            moneyDetilBill.setCreateDate(new Date());
            moneyDetilBill.setType("1");
            moneyDetilBill.setPayMode("6");
            moneyDetilBill.setBalance(balanceAfter);
            moneyDetilBill.setBigOrderCode(order.getBigOrderCode());

            moneyDetilBillFacade.save(moneyDetilBill);

        }

    }


    //取消支付
    @ResponseBody
    @RequestMapping(value = "cancelPay", method = RequestMethod.POST)
    public Boolean cancelPay(Order order) {
        try {
            order.setStatus(-4L);//取消
            order.setCancelTime(new Date());
            orderFacade.update(order);
            if (!StringUtils.isEmpty(order.getBackOrderCode())) {
                BackOrder bo = new BackOrder();
                bo.setOrderCode(order.getBackOrderCode());
                bo.setStatus(0);//取消锁定
                backOrderFacade.update(bo);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return false;
        }
        return true;
    }

    //修改订单
    @RequestMapping(value = "modifyList")
    public String modifyList(Order order, Model model, HttpServletRequest request) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        order.setOrderPlacer(shiroUser.id + "");
        order.setCharterCode(shiroUser.companyCode);
        order.setIds("-4,-3,-2,-1,0,1,2");//-4已取消，-3被拒绝，-2待匹配，-1匹配中，0待接受，1等待上车，2在途
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
        model.addAttribute("order", order);
        model.addAttribute("orders", orders);
        return "charter/order/modify_list";
    }

    //历史订单
    @RequestMapping(value = "historyList")
    public String historyList(Order order, Model model, HttpServletRequest request) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        order.setCharterCode(shiroUser.companyCode);
        order.setOrderPlacer(shiroUser.id + "");
        order.setIds("3");//完成
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
        model.addAttribute("order", order);
        model.addAttribute("orders", orders);
        return "charter/order/history_list";
    }

    //评价打分
    @RequestMapping(value = "evaluateList")
    public String evaluateList(Order order, Model model, HttpServletRequest request) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        order.setCharterCode(shiroUser.companyCode);
        //	order.setOrderPlacer(shiroUser.id+"");
        if (null == order) order = new Order();
        order.setIds("3,7"); //完成，返回
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
        model.addAttribute("order", order);
        model.addAttribute("orders", orders);
        return "charter/order/evaluate_list";
    }

    //费用结算
    @RequestMapping(value = "balanceList")
    public String balanceList(Order order, Model model, HttpServletRequest request) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        order.setCharterCode(shiroUser.companyCode);
        //order.setOrderPlacer(shiroUser.id+"");
        if (null == order) order = new Order();
        //order.setStatus(3L);//已完成
        order.setIds("0,1,2,3,5,7");
        order.setPaymentStatus(1L);//未支付
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);

        model.addAttribute("order", order);
        model.addAttribute("orders", orders);

        return "charter/order/balance_list";
    }

    //申请发票
    @RequestMapping(value = "invoiceList")
    public String invoiceList(Order order, Address address, Model model,
                              @RequestParam(value = "actionType", required = false) String actionType, HttpServletRequest request) {
        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        //	order.setOrderPlacer(shiroUser.id+"");
        order.setCharterCode(shiroUser.companyCode);
        if (null == order) order = new Order();
        order.setIds("3,7");
       // order.setStatus(3L);//已完成
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
        address.setUserId(shiroUser.id);
        List<Address> addressList = (List<Address>) addressFacade.findAddress(address);
        model.addAttribute("order", order);
        model.addAttribute("orders", orders);
        model.addAttribute("addressList", addressList);

        if (actionType != "" && actionType != null) {
            model.addAttribute("tab", 2);
        } else {
            model.addAttribute("tab", 1);
        }
        return "charter/order/invoice_list";
    }

    //订单详情
    @RequestMapping(value = "query")
    public String query(Order order, Model model, HttpServletRequest request) {
        model.addAttribute("message", order.getMessage());
        String type = "1";//默认订单详情
        if (null != order.getType() && !"".equals(order.getType())) {
            type = order.getType();
        }
        if (!StringUtils.isEmpty(order.getOrderCode())) {
            List<Order> orders = (List<Order>) orderFacade.find(order);
            if (!CollectionUtils.isEmpty(orders)) {
                order = orders.get(0);
            }
        }
        model.addAttribute("order", order);
        if ("2".equals(type)) {//评价
            if (order.getEvaluateId() != null) {
                Evaluate evaluate = new Evaluate();
                evaluate.setId(order.getEvaluateId());
                List<Evaluate> evaluateList = (List<Evaluate>) evaluateFacade.find(evaluate);
                if (!CollectionUtils.isEmpty(evaluateList)) {
                    model.addAttribute("evaluate", evaluateList.get(0));
                }
            }
            return "charter/order/evaluate";
        } else if ("3".equals(type)) {//投诉
            if (!StringUtils.isEmpty(order.getOrderCode())) {
                Complaint complaint = new Complaint();
                complaint.setOrderCode(order.getOrderCode());
                List<Complaint> complaints = (List<Complaint>) complaintFacade.find(complaint);
                if (!CollectionUtils.isEmpty(complaints)) {
                    complaint = complaints.get(0);
                }

                model.addAttribute("complaint", complaint);
            }
            return "charter/order/complaint";
        } else if ("4".equals(type)) {//发票

            ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            Map<String, Object> mapTem = new HashMap<String, Object>();


            Invoice invoice = new Invoice();
            Address address = new Address();

            address.setUserId(shiroUser.id);
            address.setStatus("1");

            invoice.setUserId(shiroUser.id);

            List<Address> addressList = (List<Address>) addressFacade.findAddress(address);
            mapTem = invoiceFacade.selectInvoiceTem(invoice);

            if (addressList.size() > 0) {
                address = addressList.get(0);
                model.addAttribute("address", address);
            }

            //model.addAttribute("address",address);
            model.addAttribute("mapTem", mapTem);

            return "charter/order/invoice";
        } else if ("5".equals(type)) {//重新发布
            return "charter/order/invoice";
        }
        return "charter/order/details";
    }

    //取消订单
    @ResponseBody
    @RequestMapping(value = "cancelOrder")
    public Map<String, Object> cancelOrder(Order order, Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            order.setOrderPlacer(shiroUser.id + "");
            order.setStatus(-4l);//取消
            orderFacade.update(order);

        } catch (Exception e) {
            state = false;
            log.info(e.getMessage());
        }
        map.put("state", state);
        return map;
    }


    @RequestMapping(value = "InvoiceTemplate")
    public String InvoiceTemplate(Invoice invoice, Model model, HttpServletRequest request) {

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

        Map<String, Object> map = new HashMap<String, Object>();

        invoice.setUserId(shiroUser.id);

        map = invoiceFacade.selectInvoiceTem(invoice);


        model.addAttribute("invoice", map);

        return "/charter/order/invoiceTemplate";
    }

    //删除订单
    @ResponseBody
    @RequestMapping(value = "deleteOrder")
    public Map<String, Object> deleteOrder(Order order, Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            order.setOrderPlacer(shiroUser.id + "");
            if (!StringUtils.isEmpty(order.getOrderCode())) {
                List<Order> orders = (List<Order>) orderFacade.find(order);
                if (!CollectionUtils.isEmpty(orders)) {
                    order = orders.get(0);
                }
            }

            Remind remind = new Remind();
            remind.setIds(shiroUser.id + "@" + order.getId());
            remind.setType(2L);
            remindFacade.save(remind);
            order.setRecordStatus(1);
            orderFacade.update(order);

        } catch (Exception e) {
            state = false;
            log.info(e.getMessage());
        }
        map.put("state", state);
        return map;
    }

    //订单提醒
    @RequestMapping(value = "remindList")
    public String remindList(Remind remind, Model model, HttpServletRequest request) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        if (null == remind) {
            remind = new Remind();
        }
        //remind.setUserId(shiroUser.id);
        remind.setCompanyCode(shiroUser.companyCode);
        //提醒类型：0：支付提醒 1：执行提醒  2：删除提醒  3：积分过期  4：投诉提醒  5：付款提醒（信用额度）
        //remind.setIds("0,2,3,4,5");
        PageInfo<Remind> reminds = (PageInfo<Remind>) remindFacade.list(remind);
        model.addAttribute("remind", remind);
        model.addAttribute("reminds", reminds);
        return "charter/order/remind_list";
    }

    //订单退款
    @ResponseBody
    @Transactional
    @RequestMapping(value = "orderRefund")
    public Map<String, Object> orderRefund(Order order) throws Exception {

        Order or = new Order();
        if (!StringUtils.isEmpty(order.getOrderCode())) {
            List<Order> orders = (List<Order>) orderFacade.find(order);
            if (!CollectionUtils.isEmpty(orders)) {
                or = orders.get(0);
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();


            Rule rule = new Rule();

            rule.setCompanyCode(shiroUser.companyCode);
            Date PlaceTime = or.getPlaceTime();
            rule.setRuleType(11l);
            rule.setTimeDifference(null);
            List<Rule> rulesParents = (List<Rule>) ruleFacade.find(rule);    //查询公司的ID

            //获取退款手续费大小限额
            Rule rr = new Rule();
            rr.setRuleType(17L);
            if (rulesParents.size() > 0) {
                rule = rulesParents.get(0);
            }
            rr.setParentId(rule.getId());
            List<Rule> rds = (List<Rule>) ruleFacade.find(rr);
            if(rds.size() > 0){
                rr = rds.get(0);
            }

            Rule coefficientRule = new Rule();
            coefficientRule.setParentId(rule.getId());
            coefficientRule.setRuleType(18l);   //获取退款系数
            long time = ((new Date().getTime()) - PlaceTime.getTime()) / (60 * 60 * 1000);  //获得下单时间和申请退单时间的小时差
            coefficientRule.setTimeDifference(time);
            List<Rule> rules = (List<Rule>) ruleFacade.find(coefficientRule);    //查询出退款率

            Double coefficient = 0.0;

            if (rules.size() > 0) {
                rule = rules.get(0);
                coefficient=rule.getCoefficient();
                if(coefficient == null){
                    coefficient=100.0;   //0.0
                }
            } else {
                coefficient=100.0;
            }

            Double coefficientMoney=0.0;
            if(!(coefficient==100)){
              //  coefficientMoney =(Double.parseDouble(or.getPrices())-(Double.parseDouble(or.getPrices())*coefficient/100));
                  coefficientMoney =((Double.parseDouble(or.getPrices())*coefficient/100));
            }



            switch (Integer.parseInt(or.getPaymentType()+"")){
                case 1:

                    break;
                case 2:
                    Credit credit  = new Credit();
                    credit.setCompanyCode(shiroUser.companyCode);
                    credit = (Credit) creditFacade.findOne(credit);

                    orderRefundSave(shiroUser,or,credit,null,coefficientMoney,rr);
                    break;
                case 3:
                    Money money = new Money();
                    money.setCompanyCode(shiroUser.companyCode);
                    money = (Money) moneyFacade.findOne(money);
                    orderRefundSave(shiroUser,or,null,money,coefficientMoney,rr);

                    break;
            }

            Refund refund = new Refund();
            //更新退款表
            refund.setOrderCode(or.getOrderCode());
            refund.setCompanyCode(shiroUser.companyCode);
            refund.setUserAccount(shiroUser.userAccount);
            refund.setUserId(shiroUser.id);
            refund.setPayType(or.getPayType());
            refund.setRefundApplyTime(new Date());
            refund.setRefundStatus("0");

            if(or.getPaymentType()==1l){
                refund.setUpdateStatus("0");
            }else {
                refund.setUpdateStatus("1");
            }
            refund.setRefundMoney(Double.parseDouble(or.getPrices()));
            refund.setRefundRealityMoney(coefficientMoney);
            refund.setCoefficient(coefficient);

            refundFacade.save(refund);



          //  order.setOrderPlacer(shiroUser.id + "");  //退款人不一定是下单人
            order.setStatus(6L);//取消
            order.setCancelTime(new Date());
            order.setBigOrderCode(or.getBigOrderCode());


            //更改运输方的订单状态
            BackOrder backOrder = new BackOrder();
            backOrder.setOrderCode(or.getBackOrderCode());
            backOrder.setStatus(3);
            backOrder.setCancelTime(new Date());
            backOrderFacade.update(backOrder);


            orderFacade.update(order);
        } catch (Exception e) {
            state = false;
            log.info(e.getMessage());
        }
        map.put("state", state);

        return map;
    }

    private Double refundLimit(Rule rr,Double coefficientMoney){
        if(rr != null && rr.getDefaultRange() != null && rr.getDefaultRange() != ""){
            int result = coefficientMoney.compareTo(Double.parseDouble(rr.getDefaultRange()));
            if(result < 0){
                coefficientMoney = Double.parseDouble(rr.getDefaultRange());
            }

            int result1 = coefficientMoney.compareTo(Double.parseDouble(rr.getToRange()));
            if(result1 > 0){
                coefficientMoney = Double.parseDouble(rr.getToRange());
            }
        }
        return coefficientMoney;
    }


    private void orderRefundSave(ShiroUser shiroUser, Order order, Credit credit, Money money,Double coefficientMoney,Rule rr) {

        if (!StringUtils.isEmpty(order.getBigOrderCode())) {
            Order o = new Order();
            o.setBigOrderCode(order.getBigOrderCode());
            //   sMSNotifyUtil.send(o, 1);//包车方下单之后通知他的车辆和司机的联系方式;
        }


        //添加账单流水
        Bill bill = new Bill();
        bill.setBillType("5");//包车方订单支付

       // Double coefficientMoney =(Double.parseDouble(order.getPrices())*coefficient/100);
        coefficientMoney = refundLimit(rr,coefficientMoney);
        BigDecimal b = new BigDecimal(coefficientMoney);
        coefficientMoney =  b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();

        bill.setMoney("+" + (Double.parseDouble(order.getPrices())-coefficientMoney) + "");//金额
        bill.setOrderCode(order.getBigOrderCode());//大订单编号
        bill.setUserId(shiroUser.id);//使用ID
        bill.setCompanyCode(shiroUser.companyCode);
        bill.setRecordStatus(0);


        String balanceBefore = null;              //调整前
        String balance = order.getPrices() + "";  //调整额
        Double Finallymoney = 0.0;                 //调整后
        String balanceAfter = null;

        if (credit != null) {

            bill.setTradeMode("1");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联
            billFacade.save(bill);


            balanceBefore = credit.getStockCredit() + "";  //调整前
            Finallymoney = Double.parseDouble(credit.getStockCredit() + "") + coefficientMoney;
            balanceAfter = Finallymoney + "";   //调整后

            //更新对应信用额度
            credit.setStockCredit(credit.getStockCredit() +Double.parseDouble(order.getPrices())- coefficientMoney);
            credit.setConsumeCredit(credit.getConsumeCredit()  - Double.parseDouble(order.getPrices()) + coefficientMoney);
            creditFacade.update(credit);

            //保存信用额度流水情况表
            CreditDetilBill creditDetilBill = new CreditDetilBill();

            creditDetilBill.setUserId(shiroUser.id);
            creditDetilBill.setCompanyCode(shiroUser.companyCode);
            creditDetilBill.setCredit("+" + balance);
            creditDetilBill.setCreditAfter(balanceAfter);
            creditDetilBill.setCreditBefore(balanceBefore);
            creditDetilBill.setCreateDate(new Date());
            creditDetilBill.setType("4");
            creditDetilBill.setPayMode("5");
            creditDetilBill.setBigOrderCode(order.getBigOrderCode());

            creditDetilBillFacade.save(creditDetilBill);
        } else {

            bill.setTradeMode("0");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联
            billFacade.save(bill);

            balanceBefore = money.getStockMoney() + "";  //调整前
            Finallymoney = Double.parseDouble(money.getStockMoney() + "") + coefficientMoney;
            balanceAfter = Finallymoney + "";   //调整后

            //更新对剩余金额     已剩金额                  订单金额                              扣除的手续费
            money.setStockMoney(money.getStockMoney()+ Double.parseDouble(order.getPrices()) -coefficientMoney);
            //已消费金额                                      订单金额                                 扣除的手续费
            money.setConsumeMoney(money.getConsumeMoney() - Double.parseDouble(order.getPrices()) + coefficientMoney);

            moneyFacade.update(money);


            //保存余额额度流水情况表
            MoneyDetilBill moneyDetilBill = new MoneyDetilBill();
            moneyDetilBill.setUserId(shiroUser.id);
            moneyDetilBill.setCompanyCode(shiroUser.companyCode);
            moneyDetilBill.setMoney("+" + balance);
            moneyDetilBill.setMoneyAfter(balanceAfter);
            moneyDetilBill.setMoneyBefore(balanceBefore);
            moneyDetilBill.setCreateDate(new Date());
            moneyDetilBill.setType("4");
            moneyDetilBill.setPayMode("5");
            moneyDetilBill.setBalance(balanceAfter);
            moneyDetilBill.setBigOrderCode(order.getBigOrderCode());

            moneyDetilBillFacade.save(moneyDetilBill);

        }

    }


    @ResponseBody
    @RequestMapping(value = "chatRefundMoney")
    public Double  chatRefundMoney(Order order){

        Order or = new Order();
        if (!StringUtils.isEmpty(order.getOrderCode())) {
            List<Order> orders = (List<Order>) orderFacade.find(order);
            if (!CollectionUtils.isEmpty(orders)) {
                or = orders.get(0);
            }
        }

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();


        Rule rule = new Rule();

        rule.setCompanyCode(shiroUser.companyCode);
        Date PlaceTime = or.getPlaceTime();
        rule.setRuleType(11l);
        rule.setTimeDifference(null);
        List<Rule> rulesParents = (List<Rule>) ruleFacade.find(rule);    //查询公司的ID

        //获取退款手续费大小限额
        Rule rr = new Rule();
        rr.setRuleType(17L);
        if (rulesParents.size() > 0) {
            rule = rulesParents.get(0);
        }
        rr.setParentId(rule.getId());
        List<Rule> rds = (List<Rule>) ruleFacade.find(rr);
        if(rds.size() > 0){
            rr = rds.get(0);
        }

        Rule coefficientRule = new Rule();
        coefficientRule.setParentId(rule.getId());
        coefficientRule.setRuleType(18l);   //获取退款系数
        long time = ((new Date().getTime()) - PlaceTime.getTime()) / (60 * 60 * 1000);  //获得下单时间和申请退单时间的小时差
        coefficientRule.setTimeDifference(time);
        List<Rule> rules = (List<Rule>) ruleFacade.find(coefficientRule);    //查询出退款率

        Double coefficient = 0.0;

        if (rules.size() > 0) {
            rule = rules.get(0);
            coefficient=rule.getCoefficient();
            if(coefficient == null){
                coefficient=100.0;
            }
        } else {
            coefficient=100.0;
        }

        Double coefficientMoney=0.0;
        if(!(coefficient==100)){
         //   coefficientMoney =(Double.parseDouble(or.getPrices())-(Double.parseDouble(or.getPrices())*coefficient/100));
            coefficientMoney =((Double.parseDouble(or.getPrices())*coefficient/100));
        }

        Double num = refundLimit(rr,coefficientMoney);

        return num ;
    }


    @RequestMapping(value = "map")
    public String map(Order order, Model model, HttpServletRequest requst) {
        model.addAttribute("order", order);
        return "charter/order/map";
    }

    public static void main(String[] args) {
        //System.out.println(String.format("%02d",1));
        String a = "中巴14座";
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        //System.out.println( m.replaceAll("").trim());
        //System.out.println(DataUtils.format(DataUtils.getByDay(new Date(), 2-1),"yyyy-MM-dd 23:59:59"));
        List<String> list = new ArrayList<String>();
        list.add("aabb");
        System.out.println(list.contains("aa"));
    }

}