package com.dhxx.service.biz.refund;


import com.dhxx.facade.entity.refund.Refund;
import com.dhxx.service.mapper.refund.RefundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p> 类说明 </p>
 *
 * @author xiewq
 * Date: 2018年02月10日
 * @version 1.00
 */

@Service
@Transactional
public class RefundBiz {
    @Autowired
    private RefundMapper refundMapper;

    public void save(Refund refund) {
        refundMapper.save(refund);
    }



    public void update(Refund refund) {
        refundMapper.update(refund);
    }


    public List<Refund> list(Refund refund){
        return refundMapper.list(refund);
    }


}
