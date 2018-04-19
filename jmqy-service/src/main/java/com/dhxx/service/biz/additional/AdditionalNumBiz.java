package com.dhxx.service.biz.additional;

import com.dhxx.facade.entity.additional.AdditionalNum;
import com.dhxx.service.biz.order.OrderBiz;
import com.dhxx.service.mapper.additional.AdditionalNumMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
@Transactional
public class AdditionalNumBiz {

    private static Logger log = LoggerFactory.getLogger(AdditionalNumBiz.class);

    @Autowired
    private AdditionalNumMapper additionalNumMapper;

    public Map<String,Object> find(AdditionalNum additionalNum){

        log.debug("AdditionalNumMapper.find()");
        return additionalNumMapper.find(additionalNum);
    }

    public  void save(AdditionalNum additionalNum){
        additionalNumMapper.save(additionalNum);
    }

    public  void update(AdditionalNum additionalNum){
        additionalNumMapper.update(additionalNum);
    }

}
