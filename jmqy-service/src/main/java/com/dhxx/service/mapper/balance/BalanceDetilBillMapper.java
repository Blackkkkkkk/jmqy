package com.dhxx.service.mapper.balance;


import com.dhxx.facade.entity.balance.BalanceDetilBill;


import java.util.List;

public interface BalanceDetilBillMapper {

    void update(BalanceDetilBill balanceDetilBill);

    BalanceDetilBill findOne(BalanceDetilBill balanceDetilBill);

    void save(BalanceDetilBill balanceDetilBill);

    List<BalanceDetilBill> list(BalanceDetilBill balanceDetilBill);
}
