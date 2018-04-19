package com.dhxx.service.biz.balance;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dhxx.facade.entity.balance.Balance;
import com.dhxx.service.mapper.balance.BalanceMapper;

/**
 * @author hanrs
 * @date 2017/9/21
 * @description
 */
@Service
@Transactional
public class BalanceBiz {
	
	@Autowired
    private BalanceMapper balanceMapper;

    public List<Balance> find(Balance balance){
    	return balanceMapper.find(balance);
    }

    public Long saveOrUpdate(Balance balance){
    	return balanceMapper.saveOrUpdate(balance);
    }

}
