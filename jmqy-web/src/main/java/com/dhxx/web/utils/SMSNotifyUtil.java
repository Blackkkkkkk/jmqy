package com.dhxx.web.utils;

import java.rmi.RemoteException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.common.sms.SingletonSMSClient;
import com.dhxx.common.util.DateEditorUtils;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.service.order.OrderFacade;

/**
 * @author hanrs
 * @description 短信通知工具
 */
@EnableAsync
@Component
@Lazy(false)
public class SMSNotifyUtil {

    private static Logger log = LoggerFactory.getLogger(SMSNotifyUtil.class);

    @Reference(protocol = "dubbo")
    private OrderFacade orderFacade;

    @SuppressWarnings("unchecked")
    @Async
    public void send(Order order, int type) {
        /*
    	 * type::
    	 * 1: 包车方下单之后通知他的车辆和司机的联系方式;
    	 * 2: 运输方更改车辆之后通知他的车辆和司机的联系方式;
    	 * 3： 延误通知包车方;
    	 * 4： 运输方确定车辆和司机;
    	*/
        List<Order> orders = (List<Order>) orderFacade.find(order);
        if (!CollectionUtils.isEmpty(orders)) {
            //包车方
            String str = new String("【马上走】您的从【" + orders.get(0).getStartPoint() + "】出发, "
                    + "上车时间为【" + DateEditorUtils.dateToStr(DateEditorUtils.TIMEFORMAT, orders.get(0).getBoardingTime()) + "】的订单,");

            for (int i = 0; i < orders.size(); i++) {
                String b = "";

                if (type == 1) {

                    b = "已经下单成功,拟安排车辆:" + orders.get(i).getCarNum() + ",拟安排司机：" + orders.get(i).getDriverName() + "(" + orders.get(i).getDriverPhone() + ")。";
                }
                if (type == 2) {
                    b = "运输方更改了订单车辆信息,安排车辆:" + orders.get(i).getCarNum() + ",安排司机：" + orders.get(i).getDriverName() + "(" + orders.get(i).getDriverPhone() + ")。";

                }
                if (type == 4) {
                    b = "运输方确定了订单车辆信息,安排车辆:" + orders.get(i).getCarNum() + ",安排司机：" + orders.get(i).getDriverName() + "(" + orders.get(i).getDriverPhone() + ")。";

                }
                if (type == 3) {
                    b = "出现了延误, 延误原因为【" + order.getRemark() + "】, 预计到达时间为：【" + DateEditorUtils.dateToStr(DateEditorUtils.TIMEFORMAT, order.getBoardingTime()) + "】。由此给您造成不便，敬请谅解。";
                }
                if (type == 5) {
                    b = "待运输方安排车辆,请稍后注意查看相关信息，谢谢您的租乘!。";
                }


                try {
                    System.out.println("11");
                    SingletonSMSClient.getClient().sendSMS(new String[]{orders.get(i).getLinkPhone()}, (str + b), "", 5);
                } catch (RemoteException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

}
