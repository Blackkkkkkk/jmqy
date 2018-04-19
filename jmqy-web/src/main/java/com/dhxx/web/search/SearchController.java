package com.dhxx.web.search;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dhxx.common.sms.SingletonSMSClient;
import com.dhxx.facade.entity.additional.AdditionalNum;
import com.dhxx.facade.entity.company.Company;
import com.dhxx.facade.entity.order.OrderTimeSet;
import com.dhxx.facade.entity.permission.Permission;
import com.dhxx.facade.entity.rule.BillingRate;
import com.dhxx.facade.entity.rule.Rule;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.additional.AdditionalNumFacade;
import com.dhxx.facade.service.company.CompanyFacade;
import com.dhxx.facade.service.order.OrderTimeSetFacade;
import com.dhxx.facade.service.permission.PermissionFacade;
import com.dhxx.facade.service.rule.BillingRateFacade;
import com.dhxx.facade.service.rule.RuleFacade;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.common.util.DataUtils;
import com.dhxx.facade.entity.charter.evaluate.Evaluate;
import com.dhxx.facade.entity.line.Line;
import com.dhxx.facade.entity.match.Cars;
import com.dhxx.facade.entity.match.Match;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.facade.entity.transport.order.BackOrder;
import com.dhxx.facade.service.bill.BillFacade;
import com.dhxx.facade.service.charter.evaluate.EvaluateFacade;
import com.dhxx.facade.service.credit.CreditFacade;
import com.dhxx.facade.service.line.LineFacade;
import com.dhxx.facade.service.match.MatchFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.remind.RemindFacade;
import com.dhxx.facade.service.transport.car.CarFacade;
import com.dhxx.facade.service.transport.order.BackOrderFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;

/**
 * @author jhy
 * @date 2017/9/24
 * @description
 */
@Controller
@RequestMapping("search")
@SuppressWarnings("unchecked")
public class SearchController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(SearchController.class);

    @Reference(protocol = "dubbo")
    private OrderFacade orderFacade;

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
    private MatchFacade matchFacade;

    @Reference(protocol = "dubbo")
    private EvaluateFacade evaluateFacade;

    @Reference(protocol = "dubbo")
    private AdditionalNumFacade additionalNumFacade;

    @Reference(protocol = "dubbo")
    private OrderTimeSetFacade orderTimeSetFacade;

    @Reference(protocol = "dubbo")
    private BillingRateFacade billingRateFacade;


    @Reference(protocol = "dubbo")
    private CompanyFacade companyFacade;

    @Reference(protocol = "dubbo")
    private PermissionFacade permissionFacade;

    @Reference(protocol = "dubbo")
    private RuleFacade ruleFacade;

    @RequestMapping(value = "charter")
    public String charter(Order order, Model model, OrderTimeSet orderTimeSet, HttpServletRequest request, Car car) {

        Map<String, Object> map = new HashMap<String, Object>();
        map = orderTimeSetFacade.find(orderTimeSet);

        List<Car> carsList = (List<Car>) carFacade.carList(car);

        model.addAttribute("carsTypeList", carsList);
        model.addAttribute("map", map);
        model.addAttribute("order", order);
        return "search/charter";
    }

    @RequestMapping(value = "map")
    public String map(Order order, Model model, HttpServletRequest requst) {
        model.addAttribute("order", order);
        return "charter/order/map";
    }

    @ResponseBody
    @RequestMapping(value = "searCarTypes")
    public Map<String, Object> searCars(Order order, OrderTimeSet orderTimeSet, AdditionalNum additionalNum, Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String carTypeChange ="1"; //2：如果没匹配车型将路线列更改为显示运输单位。 1：有匹配车型
        boolean state = true;
        double Amount = 0.0;  //选中附加选项价格总价

        Map<String, Object> mapTimeSet = new HashMap<String, Object>();
        mapTimeSet = orderTimeSetFacade.find(orderTimeSet);
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        try {
            //计算预留上车时间和预留下车时间>>>start
            order.setReserveUpTime(DataUtils.getByHour(order.getBoardingTime(), -(Integer.parseInt((mapTimeSet.get("beforesettime")) + ""))));//单前预留时间2小时
            double duration = (order.getDuration() != null ? order.getDuration() : 0) / 60.0;
            if (!StringUtils.isEmpty(order.getEndPoint())) {
                //TripType == 1:单程从上车时间算,==2：往返的从返回的上车时间
                if (order.getTripType() == 1) {
                    order.setReserveOffTime(DataUtils.getByMinute(order.getBoardingTime(), (int) Math.ceil(duration * ((1 + Integer.parseInt(mapTimeSet.get("middlesettime") + "") * 0.01) * 2)) + (Integer.parseInt((mapTimeSet.get("aftersettime")) + "")) * 60));
                } else {
                    order.setReserveOffTime(DataUtils.getByMinute(order.getTrackBoardTime(), (int) Math.ceil(duration * (1 + Integer.parseInt(mapTimeSet.get("middlesettime") + "") * 0.01))));
                }
                order.setFlag(1);//按公里数和时长计费
            }
            if (order.getCharterDays() != null) {
                order.setReserveOffTime(DataUtils.format(DataUtils.getByDay(order.getBoardingTime(), order.getCharterDays().intValue() - 1), "yyyy-MM-dd 23:59:59"));
                order.setFlag(2);//按车/天计费
            }
            //计算预留上车时间和预留下车时间>>>end

            if (shiroUser != null) {
                order.setCreateCompany(shiroUser.companyCode);//登陆用户的优惠折扣使用
            }

            //设置途经里程计费比率
            BillingRate billingRate = new BillingRate();

            billingRate.setRuleType("12");
            billingRate = billingRateFacade.find(billingRate);
            if (order.getViaPoint() != null && order.getViaPoint() != "") {
                order.setRate((Double.parseDouble(billingRate.getRate()) / 100) + "");
            }


            // 设置下单账号公司编码，作为车辆锁定选车的筛选条件
         /*   if(shiroUser.companyCode == null || shiroUser.companyCode ==""){
                order.setPlacerCode(shiroUser.id+"");
            }else{
                order.setPlacerCode(shiroUser.companyCode);
            }*/

         if(shiroUser!=null) {
             if (shiroUser.companyCode != null && shiroUser.companyCode != "") {
                 order.setPlacerCode(shiroUser.companyCode);
             } else {
                 order.setPlacerCode(shiroUser.id + "");
             }
         }

            //判断是否有该地区系数值

            Rule ruleArean = new Rule();
            ruleArean.setRuleType(1l);

            //地区系数的id
            Long ruleParentId = 0l;

            // 遍历取值 ，如果都无系数就取蓬江区，逻辑判断不在sql实现
            List<String> areanList = new ArrayList<String>();
            areanList.add(0, order.getStartArea());
            areanList.add(1, order.getStartCity());
            areanList.add(2, order.getEndArea());
            areanList.add(3, order.getEndCity());
            areanList.add(4, "蓬江区");


            //遍历判断
            Arean:
            for (int i = 0; i < areanList.size(); i++) {
                ruleArean.setRuleValue(areanList.get(i));

                if (areanList.get(i) != null && areanList.get(i) != "") {
                    List<Rule> listsetArean = (List<Rule>) ruleFacade.find(ruleArean);
                    if (!CollectionUtils.isEmpty(listsetArean)) {
                        ruleArean = listsetArean.get(0);
                        ruleParentId = ruleArean.getId();
                        order.setAreaPlace((areanList.get(i)));
                        break Arean;
                    }
                }
            }

            Rule rule = new Rule();

            List<Rule> list = new ArrayList<Rule>();

            //设置包车天数
            if (order.getTripType() == 2) {
                rule.setRuleValue(order.getAreaPlace());
                //   order.setCharteredBusCoefficient(billingRate.getRate() + "");
                list = (List<Rule>) ruleFacade.find(rule);

                if (!CollectionUtils.isEmpty(list)) {
                    rule = list.get(0);
                    Long id = rule.getId();

                    rule = new Rule();

                    rule.setRuleType(14l);
                    rule.setParentId(id);

                    list = (List<Rule>) ruleFacade.find(rule);
                    if (!CollectionUtils.isEmpty(list)) {
                        rule = list.get(0);
                        order.setCharteredBusCoefficient(rule.getCoefficient() + "");
                        System.out.println("1");
                    } else {
                        order.setCharteredBusCoefficient("1");
                    }
                }

            }


            //原先的取地区值
            /*
            if (order.getStartArea() != null && order.getStartArea() != "") {
                rule.setRuleValue(order.getStartArea());
            } else {
                rule.setRuleValue(order.getCity());
            }*/


            if (order.getDistanceDifference() != null && order.getDistanceDifference() != "") {
                //  order.setMileagePrice(getValue(ruleParentId,order,2l,(order.getDistance()-Double.parseDouble(order.getDistanceDifference()+""))));
                order.setViaPrice(getValue(ruleParentId, order, 15l, Double.parseDouble(order.getDistanceDifference() + "")));
            } else {

                //  order.setMileagePrice(getValue(ruleParentId,order,2l,Double.parseDouble(order.getDistance()+"")));
            }

            //如果地区为空，设置为null
            if (order.getEndArea() == "") {
                order.setEndArea(null);
            }

            //如果选择的车型没有，继续向上搜索
            Car car = new Car();
            List<Car> carsList = (List<Car>) carFacade.carList(car);   //搜索出所有车型

            List<Match> matchs = (List<Match>) matchFacade.selectByCarTypes(order);

            if (CollectionUtils.isEmpty(matchs.get(0).getCars())) {  //判断该车型是否有车

                String carType = order.getCarType();

                if (!CollectionUtils.isEmpty(carsList)) {   //如果车型不为空
                    matchs:
                    for (int i = 0; i < carsList.size(); i++) {
                        if (carType.equals(carsList.get(i).getCarType())) {    //车型排序，和当前选中车型对比，加1取向下的车型
                            if (carsList.size() - i >= 1) {
                                i = i + 1;                                       //i+1
                                for (int j = i; j < carsList.size(); j++) {    //从下个车型开始遍历，搜索出车辆，如果搜索到车辆则跳出循环
                                    order.setCarType((carsList.get(j)).getCarType());   //设置车型
                                    String carTypes[] = (order.getCarTypes() + "").split(",");  //更改字段,改字段是多种车型的字段
                                    String carTypess = (carsList.get(j)).getCarType() + ",";
                                    if (carTypes.length > 1) {                         //当前规则是替换首个，其余不变
                                        for (int k = 1; k < carTypes.length; k++) {
                                            carTypess += carTypes[k] + ",";
                                        }
                                    }
                                    order.setCarTypes(carTypess);
                                    matchs = (List<Match>) matchFacade.selectByCarTypes(order);
                                    if (!CollectionUtils.isEmpty(matchs.get(0).getCars())) {
                                        carTypeChange ="2";
                                        break matchs;
                                    } else {
                                        continue;
                                    }
                                }
                            } else {
                                i = i;
                                break matchs;
                            }
                        }
                    }
                }
            }
            map.put("matchs", matchs);

            //保存或者更新搜索路线
            if (!StringUtils.isEmpty(order.getStartPoint()) && !StringUtils.isEmpty(order.getEndPoint())) {
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
            // 附加选项暂无
          /*  String[] additionalArray = order.getAdditional().split(" "); //附加选项数量

            if ((order.getAdditional()).length() > 0) {  //判断是否有附加选项
                String additionalNumLength = (order.getAdditionalNumFacade()).substring(0, (((order.getAdditionalNumFacade()).length()) - 1));
                String[] additionalNumFacadeArray = additionalNumLength.split(","); //附加选项数量

                Map<String, Object> additionalNumMap = new HashMap<String, Object>(); //获取保存的价格设定
                additionalNumMap = additionalNumFacade.find(additionalNum);


                for (int i = 0; i < additionalArray.length; i++) {

                    if (additionalArray[i].equals("餐费")) {
                        Amount += Double.parseDouble(additionalNumFacadeArray[i]) * Double.parseDouble(additionalNumMap.get("meals") + "");
                    } else if (additionalArray[i].equals("住宿费")) {
                        Amount += Double.parseDouble(additionalNumFacadeArray[i]) * Double.parseDouble(additionalNumMap.get("accommodation") + "");
                    } else if (additionalArray[i].equals("高速路费")) {
                        Amount += Double.parseDouble(additionalNumFacadeArray[i]) * Double.parseDouble(additionalNumMap.get("highway") + "");
                    } else if (additionalArray[i].equals("保险")) {
                        Amount += Double.parseDouble(additionalNumFacadeArray[i]) * Double.parseDouble(additionalNumMap.get("insurance") + "");
                    } else if (additionalArray[i].equals("水")) {
                        Amount += Double.parseDouble(additionalNumFacadeArray[i]) * Double.parseDouble(additionalNumMap.get("water") + "");
                    }

                }
            }*/

        } catch (Exception e) {
            state = false;
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        map.put("state", state);
        map.put("Amount", Amount);
        map.put("carTypeChange",carTypeChange);

        return map;
    }



    // 获取里程额单价和途经里程额单价
    public double getValue(Long ruleParentId, Order order, Long type, Double num) {

        Rule rule = new Rule();

        rule.setParentId(ruleParentId);
        rule.setRuleType(type);
        List<Rule> listMileage = (List<Rule>) ruleFacade.find(rule);

        double mileagePrice = 0.0;   //计算得到里程单价值

        if (!CollectionUtils.isEmpty(listMileage)) {
            double[] longDefaultRange = new double[listMileage.size()];   //区间开始值
            double[] longToRange = new double[listMileage.size()];        //区间结束值
            double[] longCoefficient = new double[listMileage.size()];    //系数

            for (int i = 0; i < listMileage.size(); i++) {        //获取该区域的所有区间系数值
                rule = listMileage.get(i);
                longDefaultRange[i] = Double.parseDouble(rule.getDefaultRange());
                longToRange[i] = Double.parseDouble(rule.getToRange());
                longCoefficient[i] = rule.getCoefficient();
            }

            double distanceNum = (num / 1000);

            price:
            for (int i = 0; i < longDefaultRange.length; i++) {
                if (distanceNum > longDefaultRange[i]) {
                    if (distanceNum > longToRange[i]) {
                        mileagePrice += (longToRange[i] - longDefaultRange[i]) * longCoefficient[i];    //大于区间结束值
                    } else {
                        mileagePrice += (distanceNum - longDefaultRange[i]) * longCoefficient[i];           //小于区间结束值
                    }
                } else {
                    break price;
                }
            }
        }
        return mileagePrice;
    }

    @ResponseBody
    @RequestMapping(value = "searLines")
    public Map<String, Object> searLines(Order order, Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            List<Cars> carsList = (List<Cars>) matchFacade.selectLines(order);
            map.put("carsList", carsList);
        } catch (Exception e) {
            state = false;
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        map.put("state", state);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "searchRelease")
    public Map<String, Object> searchRelease(Order order, Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        try {
            //推荐车辆
            if (!StringUtils.isEmpty(order.getStartPoint()) && !StringUtils.isEmpty(order.getEndPoint())) {
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

            if (order.getTripType() == null || order.getTripType() == 1) {
                //推荐返程路线
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
    @RequestMapping(value = "lines")
    public Map<String, Object> lines(Line line, Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        try {
            //保存或者更新搜索路线
            if (shiroUser != null) {
                line.setType(1);//类型：1收藏，2搜索
                line.setCreateUser(shiroUser.id + "");
                line.setTop(4);
                List<Line> colLines = (List<Line>) lineFacade.find(line);
                map.put("colLines", colLines);

                line.setType(2);//类型：1收藏，2搜索
                List<Line> hisLines = (List<Line>) lineFacade.find(line);
                map.put("hisLines", hisLines);
            }
        } catch (Exception e) {
            state = false;
            log.info(e.getMessage());
        }
        map.put("state", state);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "checkLogin")
    public boolean checkLogin() {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        if (shiroUser != null) {
            return true;
        } else {
            return false;
        }

    }

    //包车方查看某辆车的评价列表
    @RequestMapping(value = "evaluate")
    public String evaluate(Evaluate evaluate, Model model, HttpServletRequest requst) {
        if (!StringUtils.isEmpty(evaluate.getCarCode())) {
            evaluate.setFlag(1);//1:查看包车方评价内容;2:查看运输方评价内容。
            List<Evaluate> evaluateList = (List<Evaluate>) evaluateFacade.find(evaluate);
            model.addAttribute("evaluateList", evaluateList);
        }
        return "charter/order/fen";
    }

    //包车方查看某辆车的照片
    @RequestMapping(value = "carImg")
    public String carImg(Car car, Model model, HttpServletRequest requst) {
        if (!StringUtils.isEmpty(car.getCarCode())) {
            List<Car> cars = (List<Car>) carFacade.find(car);
            if (!CollectionUtils.isEmpty(cars)) {
                car = cars.get(0);
            }
        }
        model.addAttribute("car", car);
        return "search/carImg";
    }

}
