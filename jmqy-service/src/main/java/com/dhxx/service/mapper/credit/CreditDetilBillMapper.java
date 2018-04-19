package com.dhxx.service.mapper.credit;


import com.dhxx.facade.entity.credit.CreditDetilBill;

import java.util.List;

public interface CreditDetilBillMapper {

    void update(CreditDetilBill creditDetilBill);

    CreditDetilBill findOne(CreditDetilBill creditDetilBill);

    void save(CreditDetilBill creditDetilBill);

    List<CreditDetilBill> list(CreditDetilBill creditDetilBill);
}
