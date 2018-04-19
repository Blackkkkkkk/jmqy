package com.dhxx.service.biz.finance;

import com.dhxx.facade.entity.finance.Finance;
import com.dhxx.service.mapper.finance.FinanceMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/19
 * @description
 */
@Service
@Transactional
public class FinanceBiz {

    @Autowired
    private FinanceMapper financeMapper;

    public PageInfo<Finance> list(Finance finance){
        PageHelper.startPage(finance.getPageNum(), finance.getPageSize());
        List<Finance> list = financeMapper.list(finance);
        PageInfo<Finance> pageInfo = new PageInfo<Finance>(list);
        return pageInfo;
    }

}
