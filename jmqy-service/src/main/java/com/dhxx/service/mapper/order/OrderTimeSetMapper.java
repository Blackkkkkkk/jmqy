package com.dhxx.service.mapper.order;

import com.dhxx.facade.entity.order.OrderTimeSet;

import java.util.Map;

public interface OrderTimeSetMapper {

    void update(OrderTimeSet orderTimeSet);

    Map<String,Object> find(OrderTimeSet orderTimeSet);
}
