package com.dhxx.service.biz.gprs;


import com.dhxx.facade.entity.gprs.GprsCar;
import com.dhxx.service.mapper.gprs.GprsMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class GprsBiz {
    private static Log log = LogFactory.getLog(GprsBiz.class);

    @Autowired
    private GprsMapper gprsMapper;
    //查询车辆
    public List<Map<String,Object>> find(GprsCar gprsCar){
        log.debug("GprsBiz.find()");
        return  gprsMapper.find(gprsCar);
    }
    //保存车辆
    public void  save(GprsCar gprsCar){
        log.debug("GprsBiz.save()");
        gprsMapper.save(gprsCar);
    }


    //更新车辆
    public void  update(GprsCar gprsCar){
        log.debug("GprsBiz.update()");
        gprsMapper.update(gprsCar);
    }
}
