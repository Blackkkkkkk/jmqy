package com.dhxx.service.app.service.api.transport;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import com.alibaba.fastjson.JSONObject;
import com.dhxx.common.util.StringUtils;
import com.dhxx.facade.app.transport.DriverRestFacade;
import com.dhxx.facade.entity.charter.evaluate.Evaluate;

import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.remind.Remind;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.entity.user.UserInfo;
import com.dhxx.facade.service.charter.evaluate.EvaluateFacade;

import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.remind.RemindFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.facade.util.Resp;
import com.dhxx.service.app.authorization.annotation.Authorization;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> 类说明 </p>
 *
 * @author xiewq
 * Date: 2018年03月09日
 * @version 1.01
 * app司机接口
 */
@Path("driver")
@Service(protocol = "rest")
public class DriverApi implements DriverRestFacade {

    private static Logger log = LoggerFactory.getLogger(DriverApi.class);

    @Reference(protocol = "dubbo")
    private EvaluateFacade evaluateFacade;

    @Reference(protocol = "dubbo")
    private RemindFacade remindFacade;


    @Reference(protocol = "dubbo")
    private UserFacade userFacade;

    @Reference(protocol = "dubbo")
    private OrderFacade orderFacade;

    @POST
    @Path("list")
    @Produces("application/json; charset=UTF-8")
    @Authorization
    public Object list(@Context HttpServletRequest requst) {
        Evaluate evaluate = new Evaluate();
        log.debug("app订单接口>>>>>>>>>>>>获取司机列表,参数=" + JSONObject.toJSONString(evaluate));

        UserInfo user = (UserInfo) requst.getAttribute("userInfo");

        evaluate.setDriverCode(user.getUserAccount());
        evaluate.setDriverName(user.getUserName());
        //evaluate.setDriverCode("DR-20170925000022");
        //  evaluate.setDriverName("张大");


        PageInfo<Evaluate> pageInfo = (PageInfo<Evaluate>) evaluateFacade.list(evaluate);

        //获取司机的全部订单
        List<Evaluate> list = (List<Evaluate>) evaluateFacade.find(evaluate);
        //计算平均分
        Double driverScore = 0.0;
        Double carScore = 0.0;
        int numScore = 0;
        for (int i = 0; i < list.size(); i++) {

            Evaluate eva = new Evaluate();

            eva = list.get(i);

            if (eva.getToDriverScore() != null) {
                driverScore += Double.parseDouble(eva.getToDriverScore() + "");
                numScore++;
            }
            if (eva.getToCarScore() != null) {
                carScore += Double.parseDouble(eva.getToCarScore() + "");
            }

        }

        //返回的评分list
        List scoreList = new ArrayList();
        if (driverScore != 0.0) {
            driverScore = driverScore / numScore;
            scoreList.add(0, String.format("%.2f", driverScore));
        } else {
            scoreList.add(0, 0);
        }

        if (carScore != 0.0) {
            carScore = carScore / numScore;
            scoreList.add(1, String.format("%.2f", carScore));
        } else {
            scoreList.add(1, 0);
        }


        Map<String, List> map = new HashMap<String, List>();

        map.put("list", pageInfo.getList());
        map.put("scoreList", scoreList);

        return Resp.SUCCESS(map);
    }


    @POST
    @Path("pushMessage")
    @Produces("application/json; charset=UTF-8")
    @Authorization
    public Object pushMessage(Order order,@Context HttpServletRequest requst) {

        UserInfo user = (UserInfo) requst.getAttribute("userInfo");

        User us = new User();
        us.setCompanyCode(user.getCompanyCode());
        us.setType(1);

        List<User> list =(List<User>) userFacade.find(us);

        if(!CollectionUtils.isEmpty(list)){
            us = list.get(0);
        }

       List<Order> listOrder = (List<Order>) orderFacade.find(order);

        if(!CollectionUtils.isEmpty(listOrder)){
            order = listOrder.get(0);
        }

        System.out.println(us.getId());
        Remind remind = new Remind();

        remind.setType(10L);
        remind.setUserId(us.getId());
        remind.setOrderId(order.getId());
        remindFacade.save(remind);

        return Resp.SUCCESS();

    }
}
