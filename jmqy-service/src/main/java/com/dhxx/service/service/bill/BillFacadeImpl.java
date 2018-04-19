package com.dhxx.service.service.bill;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.bill.Bill;
import com.dhxx.facade.service.bill.BillFacade;
import com.dhxx.service.biz.bill.BillBiz;

/**
 * @author hanrs
 * @date 2017/9/21
 * @description
 */
@Service(protocol = {"dubbo"})
public class BillFacadeImpl implements BillFacade {

    @Autowired
    private BillBiz billBiz;
    
    @Override
    public Object list(Bill bill){
        return billBiz.list(bill);
    }

    @Override
    public Object find(Bill bill){
        return billBiz.find(bill);
    }

    @Override
    public Object save(Bill bill){
        return billBiz.save(bill);
    }

}
