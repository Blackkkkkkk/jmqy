package com.dhxx.service.biz.balance;


import com.dhxx.facade.entity.balance.BalanceDetilBill;

import com.dhxx.service.mapper.balance.BalanceDetilBillMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class BalanceDetilBillBiz {

    @Autowired
    private BalanceDetilBillMapper balanceDetilBillMapper;


    public void update(BalanceDetilBill balanceDetilBill) {
        balanceDetilBillMapper.update(balanceDetilBill);
    }

    public void save(BalanceDetilBill balanceDetilBill) {
        balanceDetilBillMapper.save(balanceDetilBill);
    }

    public BalanceDetilBill findOne(BalanceDetilBill balanceDetilBill) {
        return balanceDetilBillMapper.findOne(balanceDetilBill);
    }


    public PageInfo<BalanceDetilBill> list(BalanceDetilBill balanceDetilBill) {
        PageHelper.startPage(balanceDetilBill.getPageNum(), balanceDetilBill.getPageSize());
        List<BalanceDetilBill> list = balanceDetilBillMapper.list(balanceDetilBill);
        PageInfo<BalanceDetilBill> pageInfo = new PageInfo<BalanceDetilBill>(list);
        return pageInfo;
    }

}
