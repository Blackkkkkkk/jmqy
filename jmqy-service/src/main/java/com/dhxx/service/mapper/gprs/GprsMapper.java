package com.dhxx.service.mapper.gprs;


import com.dhxx.facade.entity.gprs.GprsCar;

import java.util.List;
import java.util.Map;

public interface GprsMapper {


    List<Map<String,Object>> find(GprsCar gprsCar);

    void save(GprsCar gprsCar);

    void update(GprsCar gprsCar);
}
