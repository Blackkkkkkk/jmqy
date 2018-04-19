package com.sync.service.mapper.oracle;

import com.sync.facade.entity.oracle.Order;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    List<Map<String,Object>> find(Order order);
}
