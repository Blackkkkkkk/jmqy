package com.sync.service.biz.Gprs;

import com.sync.facade.entity.sqlserver.Car;
import com.sync.service.mapper.sqlserver.GprsMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class GprsBiz {

    private static Log log = LogFactory.getLog(GprsBiz.class);

    @Autowired
    private GprsMapper gprsMapper;

    public List<Map<String,Object>> find(Car car){
        log.debug("GprsBiz.find()");
        return  gprsMapper.find(car);
    }
}
