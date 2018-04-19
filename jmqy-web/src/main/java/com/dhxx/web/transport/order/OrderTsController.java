package com.dhxx.web.transport.order;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhxx.common.sms.SingletonSMSClient;
import com.dhxx.common.util.DateEditorUtils;
import com.dhxx.common.util.JpushClientUtil;
import com.dhxx.facade.entity.charter.complaint.Complaint;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.transport.driver.Driver;
import com.dhxx.facade.service.charter.complaint.ComplaintFacade;
import com.dhxx.facade.service.credit.CreditFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.charter.evaluate.Evaluate;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.remind.Remind;
import com.dhxx.facade.service.charter.evaluate.EvaluateFacade;
import com.dhxx.facade.service.match.MatchFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.remind.RemindFacade;
import com.dhxx.facade.service.transport.car.CarFacade;
import com.dhxx.facade.service.transport.driver.DriverFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.dhxx.web.utils.SMSNotifyUtil;
import com.github.pagehelper.PageInfo;

/**
 * <p> 类说明 </p>
 *
 * @author hanrs
 * Date: 2017年09月17日
 * @version 1.01
 * 运输方对包车方提交的定单管理
 */
@Controller
@RequestMapping("transport/order")
@SuppressWarnings("unchecked")
public class OrderTsController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(OrderTsController.class);

    @Reference(protocol = "dubbo")
    private OrderFacade orderFacade;

    @Reference(protocol = "dubbo")
    private DriverFacade driverFacade;

    @Reference(protocol = "dubbo")
    private CarFacade carFacade;

    @Reference(protocol = "dubbo")
    private EvaluateFacade evaluateFacade;

    @Reference(protocol = "dubbo")
    private RemindFacade remindFacade;

    @Reference(protocol = "dubbo")
    private MatchFacade matchFacade;

    @Reference(protocol = "dubbo")
    private ComplaintFacade complaintFacade;

    @Reference(protocol = "dubbo")
    private CreditFacade creditFacade;

    @Autowired
    private SMSNotifyUtil sMSNotifyUtil;

    //统计订单数量
    @ResponseBody
    @RequestMapping(value = "statistics", method = RequestMethod.POST)
    public Integer statistics(Order order, Model model) {
        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            if ("0".equals(order.getIds())) {//0待接受
                order.setTransporterCode(shiroUser.companyCode);
            }
            return (Integer) orderFacade.statistics(order);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }


    //统计投诉数量
    @ResponseBody
    @RequestMapping(value = "statisticsNum", method = RequestMethod.POST)
    public Integer statisticsNum(Order order, Model model, Complaint complaint) {
        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            complaint.setTransporterCode(shiroUser.companyCode);
            PageInfo<Complaint> complaints = (PageInfo<Complaint>) complaintFacade.list(complaint);

            return Integer.parseInt(complaints.getTotal() + "");
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }


    //统计评价分析数量
    @ResponseBody
    @RequestMapping(value = "statisticsNum1", method = RequestMethod.POST)
    public Integer statisticsNum1(Order order, Model model, Evaluate evaluate) {
        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            evaluate.setTransporterCode(shiroUser.companyCode);
            PageInfo<Evaluate> evaluates = (PageInfo<Evaluate>) evaluateFacade.list(evaluate);
            return Integer.parseInt(evaluates.getTotal() + "");
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }


    //待匹配的订单
    @RequestMapping(value = "match")
    public String match(Order order, Model model, HttpServletRequest request) {
        order.setStatus(-2L);//待匹配
        order.setRecordStatus(0);
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
        model.addAttribute("order", order);
        model.addAttribute("orders", orders);
        return "transport/order/match";
    }

    //待接收的订单
    @RequestMapping(value = "pend")
    public String pend(Order order, Model model, HttpServletRequest request) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        order.setTransporterCode(shiroUser.companyCode);
        order.setStatus(0L);//等待接受

        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
        model.addAttribute("order", order);
        model.addAttribute("orders", orders);
        return "transport/order/pend";
    }

    //已经接收的订单
    @RequestMapping(value = "list")
    public String list(Order order, Model model, HttpServletRequest request) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        order.setTransporterCode(shiroUser.companyCode);
        order.setIds("1,2,3,5,7");//-4已取消，-3被拒绝，-2待匹配，-1匹配中，0待接受，1等待上车，2在途,3完成,5待出发 7车辆返回
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
        model.addAttribute("order", order);
        model.addAttribute("orders", orders);
        return "transport/order/list";
    }

    //拒绝未接订单
    @ResponseBody
    @RequestMapping(value = "refuse", method = RequestMethod.POST)
    public Integer refuse(Order order, Model model) {
        try {
            order.setStatus(-3L);//-3为拒绝
            orderFacade.update(order);
            //提醒包车方订单被拒绝>>>start
            Remind remind = new Remind();
            remind.setType(8l);
            List<Order> orders = (List<Order>) orderFacade.find(order);
            if (!CollectionUtils.isEmpty(orders)) {
                order = orders.get(0);
            }
            remind.setUserId(Long.parseLong(order.getOrderPlacer()));
            remind.setOrderId(order.getId());
            remindFacade.save(remind);
            //提醒包车方订单被拒绝>>>end
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    //接收订单页面
    @RequestMapping(value = "accept", method = RequestMethod.GET)
    public String accept(Order order, Model model, HttpServletRequest request) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

        List<Order> orders = (List<Order>) orderFacade.find(order);
        if (!CollectionUtils.isEmpty(orders)) {
            order = orders.get(0);
        }
        model.addAttribute("order", order);//订单

        order.setTransporterCode(shiroUser.companyCode);
        model.addAttribute("drivers", matchFacade.selectDrivers(order));//运输方所属全部有效并且空闲的司机

        order.setTransporterCode(shiroUser.companyCode);
        model.addAttribute("cars", matchFacade.selectCars(order));//运输方所属全部有效并且空闲的车辆

        /*Car car =new Car();
		car.setCompanyCode(shiroUser.companyCode);
		model.addAttribute("cars", carFacade.find(car));//运输方所属全部有效车辆
		*/
        return "transport/order/accept";
    }

    //提交接收订单
    @ResponseBody
    @RequestMapping(value = "accept", method = RequestMethod.POST)
    public Integer doAccept(Order order, Model model) {
        try {




            //提醒包车方订单被接收>>>start    原先的司机
            Order o = new Order();
            o.setOrderCode(order.getOrderCode());
            List<Order> orders = (List<Order>) orderFacade.find(o);
            Order od = orders.get(0);


            Driver dr = new Driver();     // 原先的司机号码
            dr.setCode(od.getDriverCode());
            List<Driver> listDri = (List<Driver>) driverFacade.find(dr);
            if(!CollectionUtils.isEmpty(listDri)){
                dr = listDri.get(0);
            }


            Remind remind = new Remind();
            remind.setType(6l);
            remind.setUserId(Long.parseLong(od.getOrderPlacer()));
            remind.setOrderId(od.getId());
            remindFacade.save(remind);

            Driver driver = new Driver();   // 更改后的司机
            driver.setCode(order.getDriverCode());
            List<Driver> list = (List<Driver>) driverFacade.find(driver);
            if (!CollectionUtils.isEmpty(list)) {
                driver = list.get(0);
            }

           //变更后的订单
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            order.setStatus(5L);//1等待上车
            order.setOrderReceiver(shiroUser.id + "");
            order.setReceiveTime(new Date());
            orderFacade.update(order);

            String str = new String("【马上走】您的指派订单从【" + orders.get(0).getStartPoint() + "】出发, "
                    + "上车时间为【" + DateEditorUtils.dateToStr(DateEditorUtils.TIMEFORMAT, orders.get(0).getBoardingTime()) + "】的订单。");

            sMSNotifyUtil.send(order, 1);

            if (!od.getCarCode().equals(order.getCarCode()) || !od.getDriverCode().equals(order.getDriverCode())) {
                sMSNotifyUtil.send(od, 2);//运输方更改车辆之后通知他的车辆和司机的联系方式;
                if(!driver.getPhone().equals(dr.getPhone())) {//运输方车辆变更推送给原来的司机；
                    try {

                        //指派新司机的信息
                        //APP推送司机端的短信
                        if (JpushClientUtil.sendToAll(driver.getPhone(), "您收到一条信息", "您有新的派单任务", str, "1," + od.getOrderCode()) == 1) {
                            System.out.println("success");
                        } else {
                            System.out.println("failer");
                        }
                        SingletonSMSClient.getClient().sendSMS(new String[]{driver.getPhone()}, (str), "", 5); // 手机短信
                        sMSNotifyUtil.send(order, 2);
                         //推送原先司机的信息
                        //APP推送司机端的短信
                        //现派单任务先不用向司机发短信，先隐藏掉（由运输方私下通知司机）
                        if (JpushClientUtil.sendToAll(dr.getPhone(), "您收到一条信息", "派单任务变更", str+ "已经重新指派司机！", "1," + od.getOrderCode()) == 1) {
                            System.out.println("success");
                        } else {
                            System.out.println("failer");
                        }
                        SingletonSMSClient.getClient().sendSMS(new String[]{dr.getPhone()}, (str + "已经重新指派司机！"), "", 5);
                    } catch (RemoteException e) {
                        log.error(e.getMessage());
                    }
                }
            } else {
                //APP推送司机端的短信
                //现派单任务先不用向司机发短信，先隐藏掉（由运输方私下通知司机）
                if (JpushClientUtil.sendToAll(driver.getPhone(), "您收到一条信息", "您有新的派单任务", str, "1," + od.getOrderCode()) == 1) {
                    System.out.println("success");
                } else {
                    System.out.println("failer");
                }

                sMSNotifyUtil.send(o, 4);//运输方确地车辆和司机;
            }

            //提醒包车方订单被接收>>>end
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    //结束订单页面
    @RequestMapping(value = "finish", method = RequestMethod.GET)
    public String finish(Order order, Model model, HttpServletRequest request) {
        List<Order> orders = (List<Order>) orderFacade.find(order);
        if (!CollectionUtils.isEmpty(orders)) {
            order = orders.get(0);
        }
        model.addAttribute("order", order);//订单
        return "transport/order/finish";
    }

    //提交结束订单
    @ResponseBody
    @RequestMapping(value = "finish", method = RequestMethod.POST)
    public Integer doFinish(Order order, Model model) {
        try {
            order.setStatus(3L);//3结束
            orderFacade.update(order);

            //  ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

            List<Order> backOrderList = (List<Order>) orderFacade.find(order);
            Order or = backOrderList.get(0);


		   /*Credit credit = new Credit();

		   credit.setCompanyCode(or.getCharterCode());
		   credit = (Credit) creditFacade.findOne(credit);

		   credit.setStockCredit(credit.getStockCredit() - or.getAmount());
		   credit.setConsumeCredit(credit.getConsumeCredit() + or.getAmount());
		   creditFacade.update(credit);*/

        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    //评价订单页面
    @RequestMapping(value = "evaluate", method = RequestMethod.GET)
    public String evaluate(Order order, Model model, HttpServletRequest request) {
        model.addAttribute("order", order);//订单
        return "transport/order/evaluate";
    }

    //提交订单评价
    @ResponseBody
    @RequestMapping(value = "evaluate", method = RequestMethod.POST)
    public Integer doEvaluate(Evaluate evaluate, Model model) {
        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            evaluate.setTransporterUserId(shiroUser.id);
            evaluateFacade.save(evaluate);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    //订单详情
    @RequestMapping(value = "detail")
    public String query(Order order, Model model, HttpServletRequest request) {
        if (!StringUtils.isEmpty(order.getOrderCode())) {
            List<Order> orders = (List<Order>) orderFacade.find(order);
            if (!CollectionUtils.isEmpty(orders)) {
                order = orders.get(0);
            }
        }
        model.addAttribute("order", order);
        return "transport/order/detail";
    }

    //处理延误界面
    @RequestMapping(value = "delay")
    public String delay(Order order, Model model, HttpServletRequest request) {
        if (!StringUtils.isEmpty(order.getOrderCode())) {
            List<Order> orders = (List<Order>) orderFacade.find(order);
            if (!CollectionUtils.isEmpty(orders)) {
                order = orders.get(0);
            }
        }
        model.addAttribute("order", order);
        return "transport/order/delay";
    }

    //提交延误
    @ResponseBody
    @RequestMapping(value = "doDelay")
    public Integer doDelay(Order order, Model model, HttpServletRequest request) {
        try {
            sMSNotifyUtil.send(order, 3);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
        return 0;
    }

}