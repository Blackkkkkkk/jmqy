package com.dhxx.service.mapper.money;

import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.money.Money;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
public interface MoneyMapper {

    List<Money> list(Money money);

    void update(Money money);

    Money findOne(Money money);

    void save(Money money);

    List<Money> companyCreditList(Money money);
}
