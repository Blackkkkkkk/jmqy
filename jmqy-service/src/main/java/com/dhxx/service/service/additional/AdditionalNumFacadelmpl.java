package com.dhxx.service.service.additional;


import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.additional.AdditionalNum;
import  com.dhxx.facade.service.additional.AdditionalNumFacade;
import com.dhxx.service.biz.additional.AdditionalNumBiz;
import com.dhxx.service.service.order.OrderFacadeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


@Service(protocol = {"dubbo"})
public class AdditionalNumFacadelmpl implements  AdditionalNumFacade {

    private static Logger log = LoggerFactory.getLogger(AdditionalNumFacadelmpl.class);

    @Autowired
    private AdditionalNumBiz additionalNumBiz;

    @Override
    public Map<String,Object> find(AdditionalNum additionalNum){

        log.info("AdditionalNumFacadelmpl.find()");
        return additionalNumBiz.find(additionalNum);
    }

    @Override
    public  void  save(AdditionalNum additionalNum){

        log.info("AdditionalNumFacadelmpl.save()");
        additionalNumBiz.save(additionalNum);

    }
    @Override
    public  void  update(AdditionalNum additionalNum){

        log.info("AdditionalNumFacadelmpl.update()");
        additionalNumBiz.update(additionalNum);

    }
}
