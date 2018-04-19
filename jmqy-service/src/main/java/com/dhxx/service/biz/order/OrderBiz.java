package com.dhxx.service.biz.order;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dhxx.common.sms.SingletonSMSClient;
import com.dhxx.common.util.DateEditorUtils;
import com.dhxx.common.util.JpushClientUtil;
import com.dhxx.facade.entity.transport.driver.Driver;
import com.dhxx.service.mapper.transport.driver.DriverMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.score.Score;
import com.dhxx.facade.entity.transport.order.BackOrder;
import com.dhxx.facade.entity.user.User;
import com.dhxx.service.mapper.order.OrderMapper;
import com.dhxx.service.mapper.score.ScoreMapper;
import com.dhxx.service.mapper.transport.order.BackOrderMapper;
import com.dhxx.service.mapper.user.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * <p> 类说明 </p>
 *
 * @author dingbin
 * Date: 2017年09月11日
 * @version 1.01
 */
@Service
@Transactional
public class OrderBiz {
    private static Logger log = LoggerFactory.getLogger(OrderBiz.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BackOrderMapper backOrderMapper;

    @Autowired
    private DriverMapper driverMapper;

    //订单列表（分页）
    public PageInfo<Order> list(Order order) {
        log.debug("OrderBiz.list()");
        //设置起始页
        PageHelper.startPage(order.getPageNum(), order.getPageSize());
        List<Order> list = orderMapper.find(order);
        PageInfo<Order> pageInfo = new PageInfo<Order>(list);
        return pageInfo;
    }

    //订单列表（不分页）
    public List<Order> find(Order order) {
        return orderMapper.find(order);
    }

    //更新
    public void update(Order order) {
        orderMapper.update(order);
        if (order.getStatus() != null && order.getStatus() == 3) {
            //查找订单
            order = orderMapper.find(order).get(0);
            //订单结束赠送积分给包车方
            Score score = new Score();
            score.setOrderId(order.getId());
            score.setTotalScore(order.getAmount());
            //查找包车方主账号
            User user = new User();
            user.setCompanyCode(order.getCharterCode());
            user.setType(1);
            user = userMapper.find(user).get(0);
            score.setUserId(user.getId());


            Calendar calendar = Calendar.getInstance();
            Date date = new Date(System.currentTimeMillis());
            calendar.setTime(date);

            calendar.add(Calendar.YEAR, 1);  //积分期限为一年
            date = calendar.getTime();
            System.out.println(date);
            score.setExpireDate(date);
            //保存积分

            scoreMapper.save(score);
            //结束相关联的返程单
            if (!StringUtils.isEmpty(order.getBackOrderCode())) {
                BackOrder backOrder = new BackOrder();
                backOrder.setOrderCode(order.getBackOrderCode());
                backOrder.setStatus(2);
                backOrderMapper.update(backOrder);
            }
        }
    }

    //保存
    public void save(Order order) {
        orderMapper.save(order);
    }

    //删除
    public void delete(Order order) {
        orderMapper.delete(order);
    }

    public PageInfo<Order> remind(Order order) {
        PageHelper.startPage(order.getPageNum(), order.getPageSize());
        List<Order> list = orderMapper.remind(order);
        PageInfo<Order> pageInfo = new PageInfo<Order>(list);
        return pageInfo;
    }

    public Integer statistics(Order order) {
        return orderMapper.statistics(order);
    }

    //查找推荐车辆
    public List<Order> findCarByScore(Order order) {
        return orderMapper.findCarByScore(order);
    }


    //检测匹配中的订单是否支付超时
    public void checkMatch() {
        backOrderMapper.checkMatch();
        orderMapper.checkMatch();

        //车辆锁定十分钟
        orderMapper.checkCancel();
        orderMapper.checkCancelBackOrder();
    }




    //检测待接受的订单是否被运输方接收超时
    public void checkAccept() {
        orderMapper.checkAccept();
    }

    public void updateCreDitPay(Order order) {
        orderMapper.updateCreDitPay(order);
    }

    //统计某个大订单号的行数
    public Integer selectCount(Order order) {
        return orderMapper.selectCount(order);
    }


    public PageInfo<Order> refund(Order order) {
        log.info("orderMapper.refund()");
        PageHelper.startPage(order.getPageNum(), order.getPageSize());
        List<Order> list = orderMapper.refund(order);
        PageInfo<Order> pageInfo = new PageInfo<Order>(list);
        return pageInfo;
    }


    public void checkOrder() {

        Order order = new Order();

        try {

            List<Order> list = (List<Order>) orderMapper.checkOrder(order);
            if (!CollectionUtils.isEmpty(list)) {

                for (int i = 0; i < list.size(); i++) {
                    order = list.get(i);

                    String str = new String("【马上走】您的指派订单从【" + order.getStartPoint() + "】出发, "
                            + "上车时间为【" + DateEditorUtils.dateToStr(DateEditorUtils.TIMEFORMAT, order.getBoardingTime()) + "】的订单，");

                    Driver driver = new Driver();   // 更改后的司机
                    driver.setCode(order.getDriverCode());
                    List<Driver> listDriver = (List<Driver>) driverMapper.list(driver);
                    if (!CollectionUtils.isEmpty(list)) {
                        driver = listDriver.get(0);

                        //推送原先司机的信息
                        //APP推送司机端的短信
                        //现派单任务先不用向司机发短信，先隐藏掉（由运输方私下通知司机）
                        if (JpushClientUtil.sendToAll(driver.getPhone(), "您收到一条信息", "订单发车提醒", str + "半个小时后发车。", "1," + order.getOrderCode()) == 1) {
                            System.out.println("success");
                        } else {
                            System.out.println("failer");
                        }
                        SingletonSMSClient.getClient().sendSMS(new String[]{driver.getPhone()}, (str + "半个小时后发车。"), "", 5);
                    }
                }
            }
        } catch (Exception e) {
            log.debug(e.toString());
        }


    }

}
