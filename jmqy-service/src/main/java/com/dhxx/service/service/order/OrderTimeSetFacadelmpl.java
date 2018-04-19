package com.dhxx.service.service.order;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.order.OrderTimeSet;
import com.dhxx.facade.service.order.OrderTimeSetFacade;
import com.dhxx.service.biz.order.OrderTimeSetBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service(protocol = {"dubbo"})
public class OrderTimeSetFacadelmpl implements OrderTimeSetFacade{

    @Autowired
    private OrderTimeSetBiz orderTimeSetBiz;

    private static Logger log = LoggerFactory.getLogger(OrderTimeSetFacadelmpl.class);

    public  void update(OrderTimeSet orderTimeSet){
        log.info("OrderTimeSetFacadelmpl.update()");
        orderTimeSetBiz.update(orderTimeSet);
    }

    public Map<String,Object> find(OrderTimeSet orderTimeSet){
        log.info("OrderTimeSetFacadelmpl.find()");
        return   orderTimeSetBiz.find(orderTimeSet);
    }

}
