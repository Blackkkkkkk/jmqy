package com.dhxx.service.biz.money;


import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.entity.money.MoneyDetilBill;
import com.dhxx.service.mapper.credit.CreditDetilBillMapper;
import com.dhxx.service.mapper.money.MoneyDetilBillMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class MoneyDetilBillBiz {

    @Autowired
    private MoneyDetilBillMapper moneyDetilBillMapper;



    public void update(MoneyDetilBill moneyDetilBill) {
        moneyDetilBillMapper.update(moneyDetilBill);
    }
    
    public void save(MoneyDetilBill moneyDetilBill) {
        moneyDetilBillMapper.save(moneyDetilBill);
    }

    public MoneyDetilBill findOne(MoneyDetilBill moneyDetilBill) {
        return moneyDetilBillMapper.findOne(moneyDetilBill);
    }


    public PageInfo<MoneyDetilBill> list(MoneyDetilBill moneyDetilBill) {
        PageHelper.startPage(moneyDetilBill.getPageNum(), moneyDetilBill.getPageSize());
        List<MoneyDetilBill> list = moneyDetilBillMapper.list(moneyDetilBill);
        PageInfo<MoneyDetilBill> pageInfo = new PageInfo<MoneyDetilBill>(list);
        return pageInfo;
    }

}
