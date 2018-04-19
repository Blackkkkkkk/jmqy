package com.dhxx.service.biz.money;

import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.money.Money;
import com.dhxx.service.mapper.credit.CreditMapper;
import com.dhxx.service.mapper.money.MoneyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
@Service
@Transactional
public class MoneyBiz {
    private static Logger log = LoggerFactory.getLogger(MoneyBiz.class);

    @Autowired
    private MoneyMapper moneyMapper;

    public PageInfo<Money> list(Money money) {
        PageHelper.startPage(money.getPageNum(), money.getPageSize());
        List<Money> list = moneyMapper.list(money);
        PageInfo<Money> pageInfo = new PageInfo<Money>(list);
        return pageInfo;
    }

    public void update(Money money) {
        moneyMapper.update(money);
    }
    
    public void save(Money money) {
        moneyMapper.save(money);
    }

    public Money findOne(Money money) {
        log.debug("MoneyBiz.findOne()");

        return moneyMapper.findOne(money);
    }


    public PageInfo<Money> companyCreditList(Money money) {
        PageHelper.startPage(money.getPageNum(), money.getPageSize());
        List<Money> list = moneyMapper.companyCreditList(money);
        PageInfo<Money> pageInfo = new PageInfo<Money>(list);
        return pageInfo;
    }

}
