package com.dhxx.service.biz.credit;


import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.service.mapper.credit.CreditDetilBillMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CreditDetilBillBiz {

    @Autowired
    private CreditDetilBillMapper creditDetilBillMapper;



    public void update(CreditDetilBill creditDetilBill) {
        creditDetilBillMapper.update(creditDetilBill);
    }
    
    public void save(CreditDetilBill creditDetilBill) {
        creditDetilBillMapper.save(creditDetilBill);
    }

    public CreditDetilBill findOne(CreditDetilBill creditDetilBill) {
        return creditDetilBillMapper.findOne(creditDetilBill);
    }


    public PageInfo<CreditDetilBill> list(CreditDetilBill creditDetilBill) {
        PageHelper.startPage(creditDetilBill.getPageNum(), creditDetilBill.getPageSize());
        List<CreditDetilBill> list = creditDetilBillMapper.list(creditDetilBill);
        PageInfo<CreditDetilBill> pageInfo = new PageInfo<CreditDetilBill>(list);
        return pageInfo;
    }

}
