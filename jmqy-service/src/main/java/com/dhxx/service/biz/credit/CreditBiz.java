package com.dhxx.service.biz.credit;

import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.user.Userper;
import com.dhxx.service.mapper.credit.CreditMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class CreditBiz {

    @Autowired
    private CreditMapper creditMapper;

    public PageInfo<Credit> list(Credit credit) {
        PageHelper.startPage(credit.getPageNum(), credit.getPageSize());
        List<Credit> list = creditMapper.list(credit);
        PageInfo<Credit> pageInfo = new PageInfo<Credit>(list);
        return pageInfo;
    }

    public void update(Credit credit) {
        creditMapper.update(credit);
    }
    
    public void save(Credit credit) {
        creditMapper.save(credit);
    }

    public Credit findOne(Credit credit) {
        return creditMapper.findOne(credit);
    }


    public PageInfo<Credit> companyCreditList(Credit credit) {
        PageHelper.startPage(credit.getPageNum(), credit.getPageSize());
        List<Credit> list = creditMapper.companyCreditList(credit);
        PageInfo<Credit> pageInfo = new PageInfo<Credit>(list);
        return pageInfo;
    }

}
