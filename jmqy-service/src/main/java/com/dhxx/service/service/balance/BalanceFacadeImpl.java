package com.dhxx.service.service.balance;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.balance.Balance;
import com.dhxx.facade.service.balance.BalanceFacade;
import com.dhxx.service.biz.balance.BalanceBiz;

/**
 * @author hanrs
 * @date 2017/9/21
 * @description
 */
@Service(protocol = {"dubbo"})
public class BalanceFacadeImpl implements BalanceFacade {

    @Autowired
    private BalanceBiz balanceBiz;

    @Override
    public Object find(Balance balance){
        return balanceBiz.find(balance);
    }

    @Override
    public Object saveOrUpdate(Balance balance){
        return balanceBiz.saveOrUpdate(balance);
    }

}
