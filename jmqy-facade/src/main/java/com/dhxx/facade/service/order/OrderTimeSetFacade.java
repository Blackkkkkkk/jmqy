package com.dhxx.facade.service.order;

import com.dhxx.facade.entity.order.OrderTimeSet;

import java.util.Map;

public interface OrderTimeSetFacade {

    void update(OrderTimeSet orderTimeSet);
    Map<String,Object> find(OrderTimeSet orderTimeSet);
}
