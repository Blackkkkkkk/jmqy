package com.dhxx.service.biz.bill;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhxx.facade.entity.bill.Bill;
import com.dhxx.service.mapper.bill.BillMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author hanrs
 * @date 2017/9/21
 * @description
 */
@Service
@Transactional
public class BillBiz {
	
	@Autowired
    private BillMapper billMapper;
	
	public PageInfo<Bill> list(Bill bill){
		PageHelper.startPage(bill.getPageNum(), bill.getPageSize());
        List<Bill> list = billMapper.find(bill);
        PageInfo<Bill> pageInfo = new PageInfo<Bill>(list);
		return pageInfo;
	}

    public List<Bill> find(Bill bill){
    	return billMapper.find(bill);
    }

    public Long save(Bill bill){
    	return billMapper.save(bill);
    }

}
