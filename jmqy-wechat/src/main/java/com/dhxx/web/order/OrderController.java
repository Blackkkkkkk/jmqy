package com.dhxx.web.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.dhxx.common.constant.Constant;
import com.dhxx.common.sms.Mo;
import com.dhxx.common.util.DataUtils;
import com.dhxx.common.wechat.WechatUtils;
import com.dhxx.facade.entity.bill.Bill;
import com.dhxx.facade.entity.charter.evaluate.Evaluate;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.facade.entity.transport.order.BackOrder;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.entity.weappOA.LocationPostBase;
import com.dhxx.facade.entity.weappOA.Usermessage;
import com.dhxx.facade.service.bill.BillFacade;
import com.dhxx.facade.service.charter.evaluate.EvaluateFacade;
import com.dhxx.facade.service.credit.CreditFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.publicCode.wechatpay.PublicCodeWechatPay;
import com.dhxx.facade.service.transport.car.CarFacade;
import com.dhxx.facade.service.transport.order.BackOrderFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.facade.service.weChatOA.WechatOAFacade;
import com.dhxx.web.login.get;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.dhxx.web.utils.MessagePush;
import com.dhxx.web.wechat.PayResultNotice;
import com.dhxx.web.wechat.UniteOrder;
import com.dhxx.web.wechat.UniteOrderResult;
import com.dhxx.web.wechat.WeixinMpUtils;
import com.github.pagehelper.PageInfo;
import com.lijing.wechatpay.impl.PayImpl;
import com.lijing.wechatpay.util.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.dhxx.web.utils.wechatUtils;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


import org.springframework.web.servlet.ModelAndView;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

/**
 * Created by Administrator on 2017/11/7.
 */
@SessionAttributes(value={"order"},types={Order.class})
@Controller
@RequestMapping("order")
public class OrderController {

    private static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Reference(protocol="dubbo")
    private EvaluateFacade evaluateFacade;

    @Reference(protocol = "dubbo")
    private OrderFacade orderFacade;

    @Reference(protocol = "dubbo")
    private UserFacade userFacade;


    @Reference(protocol = "dubbo")
    private WechatOAFacade wechatOAFacade;

    @Reference(protocol = "dubbo")
    private CarFacade carFacade;

    @Reference(protocol = "dubbo")
    private BackOrderFacade backOrderFacade;

    @Reference(protocol = "dubbo")
    private CreditFacade creditFacade;

    @Reference(protocol = "dubbo")
    private BillFacade billFacade;

    @Reference(protocol="dubbo")
    private PublicCodeWechatPay publicCodeWechatPay;

    private static String ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";


    @RequestMapping(value = "order")
    public String wechatapp(Order order, Model model, HttpServletRequest request, HttpSession session) {



        Usermessage usermessage = (Usermessage) session.getAttribute("usermessage");
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        //    ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String bigOrderCode = order.getBigOrderCode();
        try {
            if (!StringUtils.isEmpty(bigOrderCode)) {
                Order o = new Order();
                o.setBigOrderCode(bigOrderCode);
                o.setRecordStatus(1);
                orderFacade.update(o);
            } else {
                bigOrderCode = Constant.OR_CODE + DataUtils.getCurrentTimeStr() + 1;
                order.setBigOrderCode(bigOrderCode);
            }

            //订单保存的自定义信息
            User us = new User();
            us.setUserAccount("wx"+usermessage.getPhone());
            List list = (List) userFacade.find(us);

            us = (User) list.get(0);

            order.setLinkMan(us.getUserName());
            order.setLinkPhone(us.getPhone());
            order.setOrderPlacer(us.getId() + "");
            order.setCharterCode(us.getCompanyCode());
            order.setRecordStatus(0);
            order.setPaymentStatus(1L);//待支付
            order.setPlaceTime(new Date());
            if (order.getFlag() == 1) {//预定
                //计算预留上车时间和预留下车时间>>>start
                double duration = (order.getDuration() != null ? order.getDuration() : 0) / 60.0;
                order.setReserveUpTime(DataUtils.getByHour(order.getBoardingTime(), -2));//单前预留时间2小时
                if (!StringUtils.isEmpty(order.getEndPoint())) {
                    //TripType == 1:单程从上车时间算,==2：往返的从返回的上车时间
                    if(order.getTripType() == 1) {
                        order.setReserveOffTime(DataUtils.getByMinute(order.getBoardingTime(), (int) Math.ceil(duration * 2.5) + 2 * 60));
                    } else {
                        order.setReserveOffTime(DataUtils.getByMinute(order.getTrackBoardTime(), (int) Math.ceil(duration * 1.25)+ 2 * 60));
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
                List  carCodesList = java.util.Arrays.asList(order.getCarCode().split(","));
                List  carAmountsList = java.util.Arrays.asList(order.getCarAmounts().split(","));
                List  carTypesList = java.util.Arrays.asList(order.getCarTypes().split(","));


                Map<String, Integer> map1 = new HashMap<String, Integer>();

                String  recarAmounts="";
                String  recarTypes="";


                Order od = null;
                for (int i = 0; i < backOrderCodes.length; i++) {
                    od = new Order();
                    BeanUtils.copyProperties(order, od);
                    od.setPrices(pricess[i]);//在每个订单保存总价格
                    od.setOrderCode(bigOrderCode + "_" + String.format("%02d", i + 1));
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
                    //    od.setReserveOffTime(DataUtils.getByMinute(backOrder.getLatestDepartureTime(), (int) Math.ceil(duration * 1.25)+2*60));
                        //计算预留上车时间和预留下车时间>>>end
                        BackOrder bo = new BackOrder();
                        bo.setOrderCode(backOrder.getOrderCode());
                        bo.setStatus(1);
                        backOrderFacade.update(bo);
                    }


                    map1 = getMap(carCodesList);
                    recarTypes="";
                    recarAmounts="";
                    for (String key : map1.keySet()) {

                        recarTypes  += key + ",";
                        recarAmounts += map1.get(key) + ",";
                    }
                    od.setCarAmounts(recarAmounts);
                    od.setCarTypes(recarTypes);

                    orderFacade.save(od);
                    session.setAttribute("od", od);
                }


                //选择车辆的时候判断是否数量全部满足
                Order pushOr = new Order();
                BeanUtils.copyProperties(order, pushOr);
                //统计多种车型情况下的全部总数目
                int num = 0;
                for (Object k :carAmountsList) {
                    if(k!=null && !k.equals("")) {
                        num += Integer.parseInt(k+"");
                    }
                }
                //检查选择车辆的数目和填写车辆的数目是否一致
                if(num>carCodesList.size()) {
                    if (carAmountsList.size() == 1) {//如果车型只有一种的情况

                        //当车辆选择少于填写车辆数目的时候
                        if (Integer.parseInt(carAmountsList.get(0) + "") > carCodesList.size()) {
                            pushOr.setCarAmounts((Integer.parseInt(carAmountsList.get(0) + "") - carCodesList.size()) + ",");//填写车辆数目减去所选车型
                            pushOr.setCarTypes(carTypesList.get(0)+"");
                        }
                    } else {

                        map1 = getMap(carCodesList);
                        recarAmounts="";
                        recarTypes="";
                        //判断每种型号的差数
                        for (int j = 0; j < carTypesList.size(); j++) {
                            //如果选择了N种类型，实际出现了少于N的时候不做判断会报空指针
                            if (map1.containsKey(carTypesList.get(j))) {
                                System.out.println("11");
                                if (Integer.parseInt(carAmountsList.get(j) + "") > map1.get(carTypesList.get(j)).intValue()) {
                                    recarAmounts +=(Integer.parseInt(carAmountsList.get(j) + "") - map1.get(carTypesList.get(j)).intValue())+",";
                                    recarTypes+=carTypesList.get(j)+",";
                                }
                            }else {
                                recarAmounts +=Integer.parseInt(carAmountsList.get(j) + "")+",";
                                recarTypes+=carTypesList.get(j)+",";
                            }
                        }
                        pushOr.setCarAmounts(recarAmounts);
                        pushOr.setCarTypes(recarTypes);
                    }

                    pushOr.setCarCode("");
                    //区别上面的大订单号，当前时间加1秒
                    Calendar cal   =   Calendar.getInstance();
                    cal.add(Calendar.DATE, 0);
                    cal.add (Calendar.SECOND, 1);
                    String time = new SimpleDateFormat( "yyyyMMddhhmmsss").format(cal.getTime());
                    String bigOrderCode1 = Constant.OR_CODE+time;
                    pushOr.setBigOrderCode(bigOrderCode1);
                    pushOr.setOrderCode(bigOrderCode1+"_"+String.format("%02d",num));
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
                session.setAttribute("order", od);
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
        //  return map;

        //存回session层，确保数据同步性

        session.setAttribute("usermessage", usermessage);

        return "order/order";
    }
    //根据车牌号从数据库查找车辆类型
    public Map<String,Integer> getMap(List carCodesList){
        System.out.println("carCodesList="+carCodesList);
        //根据carCode从数据库查找出对应的车辆的类型放进list中
        List<String> l =new ArrayList<String>();
        for (Object x : carCodesList) {
            l.add(carFacade.findcartype(x+""));
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
        return  map1;
    }

    @RequestMapping(value = "getImgSrc")
    public Map<String, Object> getImgSrc(@RequestParam(value = "order") String or, @RequestParam(value = "money") String money, HttpSession session) {

        Map<String, Object> map = new HashMap<String, Object>();

        String url = "http://219.130.135.53:8090/WXImage.ASPX?orderid=" + or + "&size=200&money=" + money;
        String res = null;
        try {
            String html = get.getOneHtml(url);
            res = get.match(html, "img", "src");

        } catch (Exception e) {

        }

        map.put("src", res);

        return map;

    }
  // 微信支付成功后重定向网址
    @RequestMapping(value = "weChatOrtherPay")
    public String weChatOrtherPay(@RequestParam(value = "BigOrderCode") String bigOrderCode,
                                  @RequestParam(value = "Money") Double Money,
                                  HttpSession session,Model model) {

        System.out.println("获取的bigOrderCode="+bigOrderCode);
        System.out.println("获取的money="+Money);
        Order order = new Order();
        order.setBigOrderCode(bigOrderCode);
      //  order.setAmount(Money); //判断金额

        Usermessage usermessage = (Usermessage) session.getAttribute("usermessage");
        String state=publicCodeWechatPay.NewPayResult(order,null,Money,usermessage.getPhone());

        System.out.println("获取的state="+state);

        if (state.equals("success")){
            return "user/history";
        }else{
            return "order/order";
        }

      /*
      //请求端口查询是否支付成功
        WechatUtils wechatUtils = new WechatUtils();
        String url = "http://mobile.jmqyjt.com/Interface/PayStateall.aspx?order="+bigOrderCode;
        String re=wechatUtils.httpRequest(url,"GET",null);

      //  String re = httpRequest(url,"GET",null);
        System.out.println("re="+re);
     if(!re.equals("F")) {
         if ((Long.parseLong(re)) == Money) {

             Usermessage usermessage = (Usermessage) session.getAttribute("usermessage");

             //TODO:未做订单金额效验
             Order order = new Order();
             order.setBigOrderCode(bigOrderCode);//大订单编号
             order.setPaymentStatus(2L);//已支付
             order.setPaymentType(1L);//现付

             order.setStatus(0L);//待接受
             order.setPlaceTime(new Date());//下单时间
             orderFacade.update(order);


             Bill bill = new Bill();
             bill.setBillType("0");//包车方订单支付

             Double amount = Double.parseDouble(Money + "");
             BigDecimal bigDecimal = new BigDecimal(amount / 100);
             double money = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
             bill.setMoney(money + "");//金额
             bill.setTradeMode("2");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联
             bill.setOrderCode(bigOrderCode);//大订单编号
             //  bill.setThreeOrderNo(resultNtice.getTransaction_id());//第三方订单编号
             bill.setUserId(Long.parseLong(usermessage.getPhone()));//使用ID
             billFacade.save(bill);

             return "user/history";
         } else {
             System.out.println("支付失败,金额是=" + re);

             return "order/order";
         }
     }else {
         System.out.println("支付失败,金额是=" + re);

         return "order/order";
     }
     */
    }
 /*   //判断是否支付成功
    @RequestMapping(value ="NewPay")
    public Map<String, Object> NewPay(@RequestParam(value = "order") String or,@RequestParam(value = "money") String mo
            ,@RequestParam(value = "code",required = false) String code,HttpServletRequest request,HttpSession session) {

        Map<String, Object> map = new HashMap<String, Object>();

        //请求端口查询是否支付成功
        WechatUtils wechatUtils = new WechatUtils();
        String url = "http://mobile.jmqyjt.com/Interface/PayStateall.aspx?order="+or;
        String re=wechatUtils.httpRequest(url,"GET",null);
//获取用户名
        Usermessage usermessage =(Usermessage)session.getAttribute("usermessage");

        if (usermessage==null||usermessage.getPhone()==null){
            System.out.println("usermessage.getPhone()="+usermessage.getPhone());
            usermessage=new Usermessage();
            wechatUtils =new WechatUtils();

         //   String code = "001YM1bo1MePnk0Xe1ao1OH7bo1YM1bf"; // code 用户登录公众号会获取

            //获取openid 和access_token
             url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WechatUtils.getWeixinAppid()+"&secret="+WechatUtils.getWeixinAppsecret()+"&code="+code+"&grant_type=authorization_code ";
             re= wechatUtils.httpRequest(url,"GET",null);
            JSONObject jsStr = JSONObject.parseObject(re);
            String access_token = jsStr.getString("access_token");
            String openid = jsStr.getString("openid");

            // 获取unionid
            url = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN  ";
            re = wechatUtils.httpRequest(url,"GET",null);
            System.out.println("jsStr="+jsStr);
            jsStr = JSONObject.parseObject(re);
            String unionid = jsStr.getString("unionid");

          //  String unionid = "oIOsdwaway2jgY9AHOTr8iXC1_nQ";
            //根据unid反向获取

            Map<String,Object> map1 =wechatOAFacade.findUserMessage(unionid);
            usermessage.setPhone(map1.get("WECHAT_PHONE").toString());
            usermessage.setUnionid(map1.get("WECHAT_UNIONID").toString());
            usermessage.setOpenid(map1.get("WECHAT_OPENID").toString());
        }else {
            usermessage =(Usermessage) session.getAttribute("usermessage");
        }
        re.trim();

        if(re.indexOf("F")<0&&re.indexOf("NOPAY")<0){

            String bigOrderCode = or;
            //TODO:未做订单金额效验
            Order order = new Order();
            order.setBigOrderCode(bigOrderCode);//大订单编号
            order.setPaymentStatus(2L);//已支付
            order.setPaymentType(1L);//现付
            System.out.println("更新前bigOrderCode="+bigOrderCode);
            order.setStatus(0L);//待接受
            order.setPlaceTime(new Date());//下单时间
            orderFacade.update(order);
            System.out.println("更新后的bigOrderCode="+order.getBigOrderCode());

            Bill bill = new Bill();
            bill.setBillType("0");//包车方订单支付

            Double amount = Double.parseDouble(mo);
            BigDecimal bigDecimal = new BigDecimal(amount/100);
            double money = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            bill.setMoney(money+"");//金额
            bill.setTradeMode("2");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联
            bill.setOrderCode(bigOrderCode);//大订单编号
          //  bill.setThreeOrderNo(resultNtice.getTransaction_id());//第三方订单编号
            bill.setUserId(Long.parseLong(usermessage.getPhone()));//使用ID
            billFacade.save(bill);
        }
        map.put("re",re);

        return map;

    }

*/

    @RequestMapping(value ="saveReleaseDetail", method = RequestMethod.POST)
    public Map<String, Object> saveReleaseDetail( Model model, HttpServletRequest request,HttpSession session){

        Order order = (Order) session.getAttribute("order");

        Map<String, Object> map = new HashMap<String, Object>();
        Integer state = 1;
        order.setPayType(1L);
        try {
       //     ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            if(order.getPayType() == 1){//全额支付
                if(!StringUtils.isEmpty(order.getBigOrderCode())){
                    orderFacade.update(order);
                }
                state = 2;//跳去微信扫描支付
            }else if(order.getPayType() == 3){ //记账支付  // 微信端暂无记账支付
                Credit credit = new Credit();
                //拿手机号码做Userid
                credit.setUserId(Long.valueOf((String) session.getAttribute("setOrderPlacer")).longValue());
                credit = (Credit) creditFacade.findOne(credit);
                if(order.getAmount() > credit.getStockCredit()){
                    state = 3;//信用额度不够
                }else {
                    if(!StringUtils.isEmpty(order.getBigOrderCode())){
                        order.setPaymentType(2L);//月结
                        order.setStatus(0L);//待接受
                        orderFacade.update(order);
                    }

                    //添加账单流水
                    Bill bill = new Bill();
                    bill.setBillType("0");//包车方订单支付
                    bill.setMoney(order.getAmount()+"");//金额
                    bill.setTradeMode("1");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联
                    bill.setOrderCode(order.getBigOrderCode());//大订单编号
                    bill.setUserId(Long.valueOf((String) session.getAttribute("setOrderPlacer")).longValue());//使用ID
                    billFacade.save(bill);

                    //更新对应信用额度
                    credit.setStockCredit(credit.getStockCredit() - order.getAmount());
                    credit.setConsumeCredit(credit.getConsumeCredit() + order.getAmount());
                    creditFacade.update(credit);
                }
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            state = 99;
            e.printStackTrace();
        }
        map.put("state", state);
        session.setAttribute("order",order);
        return map;
    }



  @RequestMapping(value = "evaluation")
  public ModelAndView evaluation(Evaluate evaluate,Order order,HttpSession session,@RequestParam(value = "action",required = false) String action){


      ModelAndView modelAndView = new ModelAndView();
     if(action!=null&&action!=""){
        if(action.equals("2")) {
          if (!StringUtils.isEmpty(order.getOrderCode())) {
              List<Order> orders = (List<Order>) orderFacade.find(order);
              if (!CollectionUtils.isEmpty(orders)) {
                  order = orders.get(0);
              }
          }

          if (order.getEvaluateId() != null) {
              Evaluate evaluate1 = new Evaluate();
              evaluate1.setId(order.getEvaluateId());
              List<Evaluate> evaluateList = (List<Evaluate>) evaluateFacade.find(evaluate1);
              if (!CollectionUtils.isEmpty(evaluateList)) {
                  modelAndView.addObject("evaluate", evaluateList.get(0));
              }
          }

        }else{
           //更新评价
            try {
                User us = new User();
                Usermessage usermessage =(Usermessage)session.getAttribute("usermessage");
                System.out.println("外查看手机id"+usermessage.getPhone());
                if(usermessage.getPhone() != null && usermessage.getPhone() != ""){

                    //根据手机号码查id，格式为wx+手机号码
                    us.setUserAccount("wx"+usermessage.getPhone());
                    List list = (List) userFacade.find(us);
                    us = (User) list.get(0);
                    System.out.println("内查看手机id"+usermessage.getPhone());
                }
                System.out.println("查看id"+us.getId() );
               // ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
                evaluate.setUserId(us.getId());//用户编号
                evaluate.setCreateTime(new Date());
                Evaluate temp = (Evaluate)evaluateFacade.save(evaluate);

                //因mapper调整，需先查询
                List<Evaluate> evaluateList  = (List<Evaluate>)evaluateFacade.find(evaluate);
                if(!CollectionUtils.isEmpty(evaluateList)){
                    System.out.println("为空进来了");
                    Order or = new Order();
                    or.setOrderCode(order.getOrderCode());
                    or.setOrderPlacer(us.getId()+"");
                    or.setEvaluateId(evaluateList.get(0).getId());
                    System.out.println("报错的订单ID="+or.getOrderCode());
                    orderFacade.update(or);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
      }
      modelAndView.addObject("order",order);
      modelAndView.setViewName("user/evaluation");

      return modelAndView;
  }


    //保持评价信息
    @ResponseBody
    @RequestMapping(value="saveEvaluate",method = RequestMethod.POST)
    public Map<String, Object> saveEvaluate(Evaluate evaluate, Model model, HttpServletRequest request ,HttpSession session,@RequestParam(value = "code",required = false) String code) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {

            Usermessage usermessage =(Usermessage)session.getAttribute("usermessage");

            if (usermessage==null||usermessage.getPhone()==null){

                usermessage=new Usermessage();

                if (code!=null&&code!="") {
                    usermessage=setName(code,usermessage);
                    session.setAttribute("usermessage",usermessage);
                }

            }else {
                usermessage =(Usermessage) session.getAttribute("usermessage");
            }


            User us  = new User();
            us.setUserAccount("wx"+usermessage.getPhone());
            List list=(List) userFacade.find(us);
            us=(User) list.get(0);


            evaluate.setUserId(us.getId());//用户编号
            evaluate.setCreateTime(new Date());
            Evaluate temp = (Evaluate)evaluateFacade.save(evaluate);

            //因mapper调整，需先查询
            List<Evaluate> evaluateList  = (List<Evaluate>)evaluateFacade.find(evaluate);
            if(!CollectionUtils.isEmpty(evaluateList)){
                Order order = new Order();
                order.setOrderCode(evaluate.getOrderCode());
                order.setOrderPlacer(us.getId()+"");
                order.setEvaluateId(evaluateList.get(0).getId());
                orderFacade.update(order);
            }
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
            log.error(e.getMessage());
        }
        map.put("state", state);
        return map;
    }
    //进入发布订单页面或者修改重新发布
    @RequestMapping(value = "pushOrder")
    public String pushOrder(Order order,Model model, HttpServletRequest request,HttpSession session) {
        System.out.println("11===1");
        if(!StringUtils.isEmpty(order.getOrderCode()) || !StringUtils.isEmpty(order.getBigOrderCode())){
            List<Order> orders=(List<Order>)orderFacade.find(order);
            if(!CollectionUtils.isEmpty(orders)){

                order = orders.get(0);

                LocationPostBase locationPostBase = new LocationPostBase();
                locationPostBase.setStartPoint(order.getStartPoint());
                locationPostBase.setStartLng(order.getStartLng());
                locationPostBase.setStartLat(order.getStartLat());
                locationPostBase.setStartCity(order.getStartCity());
                locationPostBase.setStartDistrict(order.getStartArea());
                locationPostBase.setEndPoint(order.getEndPoint());
                locationPostBase.setEndLng(order.getEndLng());
                locationPostBase.setEndLat(order.getEndLat());
                locationPostBase.setEndCity(order.getEndCity());
                locationPostBase.setEndDistrict(order.getEndArea());

                session.setAttribute("locationPostBase",locationPostBase);

            }
        }else{
            //设置默认
            order.setTripType(1L);
            order.setCharterType(1L);
        }
        session.setAttribute("order1",order);
        System.out.println("111");
        System.out.println(session.getAttribute("order1"));
       // model.addAttribute("order",order);
        return "search/updatesearch";
    }
    //取消订单
    @ResponseBody
    @RequestMapping(value ="cancelOrder")
    public Map<String, Object> cancelOrder(Order order,Model model,HttpServletRequest request,HttpSession session,@RequestParam(value = "code",required = false) String code){

         //获取用户
        Usermessage usermessage =(Usermessage)session.getAttribute("usermessage");

        if (usermessage==null||usermessage.getPhone()==null){

            usermessage=new Usermessage();

            if (code!=null&&code!="") {
                usermessage=setName(code,usermessage);
                session.setAttribute("usermessage",usermessage);
            }

        }else {
            usermessage =(Usermessage) session.getAttribute("usermessage");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            User us  = new User();
            us.setUserAccount("wx"+usermessage.getPhone());
            List list=(List) userFacade.find(us);
            us=(User) list.get(0);
            order.setOrderPlacer(us.getId()+"");
            order.setStatus(-4L);//取消
            orderFacade.update(order);

        } catch (Exception e) {
            state = false;
            log.info(e.getMessage());
        }
        map.put("state", state);
        return map;
    }

    @RequestMapping(value = "pay")
    public void WechatPay(HttpSession session){


        Order order = (Order) session.getAttribute("order");
        order.setStatus(-4L);
        System.out.println("11111="+order.getStatus());

        session.setAttribute("order",order);
    }

    @RequestMapping(value = "getScanQRCode")
    public  Map<String,Object> getScanQRCode(HttpServletRequest request){

        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println("111");

        map=WechatUtils.getJsSignMap(request);

        System.out.println(map);
        return map;
    }


    public   Usermessage  setName(String code,Usermessage usermessage){

        WechatUtils wechatUtils = new WechatUtils();
        //获取openid 和access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WechatUtils.getWeixinAppid() + "&secret=" + WechatUtils.getWeixinAppsecret() + "&code=" + code + "&grant_type=authorization_code ";
        String re = wechatUtils.httpRequest(url, "GET", null);
        JSONObject jsStr = JSONObject.parseObject(re);
        String access_token = jsStr.getString("access_token");
        String openid = jsStr.getString("openid");

        // 获取unionid
        url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN  ";
        re = wechatUtils.httpRequest(url, "GET", null);
        System.out.println("jsStr=" + jsStr);
        jsStr = JSONObject.parseObject(re);
        String unionid = jsStr.getString("unionid");

        //  String unionid = "oIOsdwaway2jgY9AHOTr8iXC1_nQ";
        //根据unid反向获取

        Map<String, Object> map1 = wechatOAFacade.findUserMessage(unionid);
        if(map1==null || map1.size()<1)
        { }else {
            usermessage.setPhone(map1.get("WECHAT_PHONE").toString());
            usermessage.setUnionid(map1.get("WECHAT_UNIONID").toString());
            usermessage.setOpenid(map1.get("WECHAT_OPENID").toString());
        }
        return  usermessage;

    }
}
