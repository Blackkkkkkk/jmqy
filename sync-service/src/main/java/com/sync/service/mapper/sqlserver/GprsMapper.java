package com.sync.service.mapper.sqlserver;

import com.sync.facade.entity.sqlserver.Car;

import java.util.List;
import java.util.Map;

public interface GprsMapper {

    List<Map<String,Object>> find(Car car);
}
