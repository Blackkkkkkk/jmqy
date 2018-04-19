package com.dhxx.service.mapper.balance;

import java.util.List;

import com.dhxx.facade.entity.balance.Balance;

/**
 * @author hanrs
 * @date 2017/9/21
 * @description
 */
public interface BalanceMapper {

    List<Balance> find(Balance balance);

    Long saveOrUpdate(Balance balance);
    

}
