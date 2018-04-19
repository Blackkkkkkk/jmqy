package com.dhxx.service.biz.order;


import com.dhxx.facade.entity.order.OrderTimeSet;
import com.dhxx.service.mapper.order.OrderTimeSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class OrderTimeSetBiz {

    private static Logger log = LoggerFactory.getLogger(OrderBiz.class);

    @Autowired
    private OrderTimeSetMapper orderTimeSetMapper;

    public  void  update(OrderTimeSet orderTimeSetBiz){
        orderTimeSetMapper.update(orderTimeSetBiz);
    }
    public Map<String,Object> find(OrderTimeSet orderTimeSetBiz) { return  orderTimeSetMapper.find(orderTimeSetBiz);}

}
