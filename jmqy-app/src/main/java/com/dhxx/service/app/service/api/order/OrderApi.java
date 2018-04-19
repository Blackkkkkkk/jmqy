package com.dhxx.service.app.service.api.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.dhxx.common.util.DateEditorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dhxx.common.sms.SingletonSMSClient;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.dhxx.facade.app.order.OrderRestFacade;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.user.UserInfo;
import com.dhxx.facade.exception.ResException;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.util.Resp;
import com.dhxx.service.app.authorization.annotation.Authorization;
import com.github.pagehelper.PageInfo;

/**
 * <p> 类说明 </p>
 *
 * @author hanrs
 * Date: 2018年02月02日
 * @version 1.01
 * app订单接口
 */
@Path("order")
@Service(protocol = "rest")
public class OrderApi implements OrderRestFacade {

    private static Logger log = LoggerFactory.getLogger(OrderApi.class);

    @Reference(protocol = "dubbo")
    private OrderFacade orderFacade;


    @POST
    @Path("list")
    @Produces("application/json; charset=UTF-8")
    @Authorization
    public Object list(Order order, @Context HttpServletRequest requst) {
        log.debug("app订单接口>>>>>>>>>>>>获取订单列表,参数=" + JSONObject.toJSONString(order));
        UserInfo user = (UserInfo) requst.getAttribute("userInfo");
        order.setDriverCode(user.getUserAccount());
        PageInfo<Order> pageInfo = (PageInfo<Order>) orderFacade.list(order);
        return Resp.SUCCESS(pageInfo.getList());
    }

    @POST
    @Path("detail")
    @Produces("application/json; charset=UTF-8")
    @Authorization
    public Object detail(Order order, @Context HttpServletRequest requst) {
        log.debug("app订单接口>>>>>>>>>>>>获取订单详情,参数=" + JSONObject.toJSONString(order));
        UserInfo user = (UserInfo) requst.getAttribute("userInfo");
        order.setDriverCode(user.getUserAccount());
        List<Order> orders = (List<Order>) orderFacade.find(order);
        if (!CollectionUtils.isEmpty(orders)) {
            return Resp.SUCCESS(orders.get(0));
        } else {
            throw ResException.ORDER_NOT_EXISTS;
        }
    }


    @POST
    @Path("reportStatus")
    @Produces("application/json; charset=UTF-8")
    @Authorization
    public Object reportStatus(Order order, @Context HttpServletRequest requst) {
        log.debug("app订单接口>>>>>>>>>>>>上报订单状态,参数=" + JSONObject.toJSONString(order));
        if (StringUtils.isEmpty(order.getOrderCode())) {
            throw ResException.ORDER_NOT_EXISTS;
        }
        orderFacade.update(order);
        return Resp.SUCCESS();
    }


    @POST
    @Path("doDelay")
    @Produces("application/json; charset=UTF-8")
    @Authorization
    public Object doDelay(Order order, @Context HttpServletRequest requst) {
        log.debug("app订单接口>>>>>>>>>>>>延误提交,参数=" + JSONObject.toJSONString(order));

        List<Order> orders = (List<Order>) orderFacade.find(order);
        if (!CollectionUtils.isEmpty(orders)) {
            String str = new String("【马上走】您的从【" + orders.get(0).getStartPoint() + "】出发, "
                    + "上车时间为【" + DateEditorUtils.dateToStr(DateEditorUtils.TIMEFORMAT, orders.get(0).getBoardingTime()) + "】的订单,");
            String b = "出现了延误, 延误原因为【" + order.getRemark() + "】,由此给您造成不便，敬请谅解。";
            for (int i = 0; i < orders.size(); i++) {

                try {
                    SingletonSMSClient.getClient().sendSMS(new String[]{orders.get(i).getLinkPhone()}, (str + b), "", 5);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw ResException.SEND_SENDMESSAGE_ERROR;
                }
            }
        }
        return Resp.SUCCESS();
    }

    @POST
    @Path("historyMileage")
    @Produces("application/json; charset=UTF-8")
    @Authorization
    public Object historyMileage(Order order, @Context HttpServletRequest requst) {
        log.debug("app订单接口>>>>>>>>>>>>查询历史公里数,参数=" + JSONObject.toJSONString(order));

        UserInfo user = (UserInfo) requst.getAttribute("userInfo");
        order.setDriverCode(user.getUserAccount());
        order.setTransporterCode(user.getCompanyCode());
        List<Order> orders = (List<Order>) orderFacade.find(order);

        Long num= 0l;
        if (!StringUtils.isEmpty(orders)) {
            Order or = new Order();
            try {

            }catch (Exception e){
                log.debug(e.toString());
                throw ResException.SEND_HISTORYMILEAGE_ERROR;
            }
            for (int i = 0; i <orders.size() ; i++) {
                or=orders.get(i);
                num +=or.getDistance();
            }
        }
        return Resp.SUCCESS(num);
    }
}
