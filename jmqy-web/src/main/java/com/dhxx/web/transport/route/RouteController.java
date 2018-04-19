package com.dhxx.web.transport.route;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.gprs.GprsCar;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.service.gprs.GprsFacade;
import com.dhxx.facade.entity.transport.routedeviate.TransportRouteDeviate;

import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.transport.routedeviate.TransportRouteDeviateFacade;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> 类说明 </p>
 *
 * @author hanrs
 * Date: 2017年09月18日
 * @version 1.01
 * 运输方线路管理
 */
@Controller
@RequestMapping("transport/route")
public class RouteController {

    private static Logger log = LoggerFactory.getLogger(RouteController.class);


    @Reference(protocol = "dubbo")
    private TransportRouteDeviateFacade transportRouteDeviateFacade;

    @Reference(protocol = "dubbo")
    private GprsFacade gprsFacade;

    @Reference(protocol = "dubbo")
    private OrderFacade orderFacade;



    //线路管理
    @RequestMapping(value = "route")
    public String match(Model model, HttpServletRequest request) {

        GprsCar gprsCar = new GprsCar();
        gprsCar.setOrderCode("DH-201801311737000_01");
        List<Map<String, Object>> listMap = gprsFacade.find(gprsCar);

        return "transport/route/route";
    }

    //线路管理订单详情
    @RequestMapping(value = "routeDetail")
    public String routeDetail(Model model, HttpServletRequest request, TransportRouteDeviate transportRouteDeviate, GprsCar gprsCar) {

        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        transportRouteDeviate.setResponsible(shiroUser.userAccount);
        transportRouteDeviate.setCompanyCode(shiroUser.companyCode);
        List<TransportRouteDeviate> list = transportRouteDeviateFacade.find(transportRouteDeviate);

        Order order = new Order();
        order.setOrderCode(transportRouteDeviate.getOrderCode());

        List<Order> orders=(List<Order>)orderFacade.find(order);
        if(!CollectionUtils.isEmpty(orders)){
            order = orders.get(0);
        }


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String radioValue = "";
        String handlingRemark = "";
        if (list.size() > 0) {

            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    radioValue = list.get(i).getDeviation();
                }
                handlingRemark += (list.get(i)).getHandlingRemark() + "  " + df.format(list.get(i).getCreationDate()) + "\n";
            }
        }


        List<Map<String, Object>> listMap = gprsFacade.find(gprsCar);

        if (listMap.size()>0){
            Map<String,Object> map= listMap.get(0);
            model.addAttribute("map",map);
        }
        model.addAttribute("order",order);
        model.addAttribute("handlingRemark", handlingRemark);   //历史处理信息
        model.addAttribute("ordercode", transportRouteDeviate.getOrderCode());  //订单号
        model.addAttribute("username", shiroUser.userName);   //责任人
        model.addAttribute("list", list.size()); //条数限制

        radioValue.trim();
        if(radioValue == "" || radioValue == null){
            radioValue = "2";
        }
        model.addAttribute("radioValue", radioValue);//取得最近更新的一个是否偏差值

        return "transport/route/routeDetail";
    }


    //保存后，返回保存的线路信息
    @ResponseBody
    @RequestMapping(value = "saveRouteDetail")
    public Map<String, Object> saveRouteDetail(Model model, TransportRouteDeviate transportRouteDeviate) {

        Map<String, Object> map = new HashMap<String, Object>();
        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

        boolean state = true;
        transportRouteDeviate.setResponsible(shiroUser.userAccount);
        transportRouteDeviate.setCompanyCode(shiroUser.companyCode);
        try {
            transportRouteDeviateFacade.add(transportRouteDeviate);
        } catch (Exception e) {
            state = false;
        }

        map.put("state", state);

        return map;
    }

}
