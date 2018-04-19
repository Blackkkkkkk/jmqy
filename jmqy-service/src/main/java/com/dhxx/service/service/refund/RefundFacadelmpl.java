package com.dhxx.service.service.refund;


import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.refund.Refund;
import com.dhxx.facade.service.refund.RefundFacade;
import com.dhxx.service.biz.refund.RefundBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(protocol = {"dubbo"})
public class RefundFacadelmpl  implements RefundFacade{

    private static Logger log = LoggerFactory.getLogger(RefundFacadelmpl.class);

    @Autowired
    private RefundBiz refundBiz;


    @Override
    public  void save(Refund refund){
        refundBiz.save(refund);
        log.debug("RefundFacadelmpl.save()");
    }

    @Override
    public  void update(Refund refund){
        refundBiz.update(refund);
        log.debug("RefundFacadelmpl.update()");
    }

    @Override
    public List<Refund> list(Refund refund) {
        log.info("RefundFacadelmpl.list()");
        return refundBiz.list(refund);
    }

}
