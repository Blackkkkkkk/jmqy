package com.dhxx.service.mapper.money;


import com.dhxx.facade.entity.credit.CreditDetilBill;
import com.dhxx.facade.entity.money.MoneyDetilBill;

import java.util.List;

public interface MoneyDetilBillMapper {

    void update(MoneyDetilBill moneyDetilBill);

    MoneyDetilBill findOne(MoneyDetilBill moneyDetilBill);

    void save(MoneyDetilBill moneyDetilBill);

    List<MoneyDetilBill> list(MoneyDetilBill moneyDetilBill);
}
