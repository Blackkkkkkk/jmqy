package com.dhxx.service.service.gprs;



import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.gprs.GprsCar;
import com.dhxx.facade.service.gprs.GprsFacade;

import com.dhxx.service.biz.gprs.GprsBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(protocol = {"dubbo"})
public class GprsFacadelmpl implements GprsFacade {

    private static Logger log = LoggerFactory.getLogger(GprsFacadelmpl.class);

    @Autowired
    private GprsBiz gprsBiz;


    @Override
    public List<Map<String, Object>> find(GprsCar gprsCar) {

        log.debug("GprsFacadelmpl.find()");
        return gprsBiz.find(gprsCar);
    }

    @Override
    public void save(GprsCar gprsCar) {
        log.debug("GprsFacadelmpl.save()");
        gprsBiz.save(gprsCar);
    }

    @Override
    public  void  update(GprsCar gprsCar){
        log.debug("GprsFacadelmpl.update()");
        gprsBiz.update(gprsCar);
    }
}
