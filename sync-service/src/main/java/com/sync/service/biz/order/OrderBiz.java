package com.sync.service.biz.order;


import com.sync.facade.entity.oracle.Order;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.sync.service.mapper.oracle.*;

import java.util.List;
import java.util.Map;

public class OrderBiz {
    private static Log log = LogFactory.getLog(OrderBiz.class);

    @Autowired
    private  OrderMapper orderMapper;

    public List<Map<String,Object>> find(Order order){
        log.debug("OrderBiz.find()");
        return orderMapper.find(order);
    }
}
