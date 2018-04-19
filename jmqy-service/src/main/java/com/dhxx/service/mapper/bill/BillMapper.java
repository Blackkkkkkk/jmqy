package com.dhxx.service.mapper.bill;

import java.util.List;

import com.dhxx.facade.entity.bill.Bill;

/**
 * @author hanrs
 * @date 2017/9/21
 * @description
 */
public interface BillMapper {

    List<Bill> find(Bill bill);

    Long save(Bill bill);
    

}
