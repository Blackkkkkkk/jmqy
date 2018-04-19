package com.dhxx.facade.service.charter.invoice;


import com.dhxx.facade.entity.charter.invoice.Address;

/**
 * <p> 类说明 </p>
 * @author xie
 * Date: 2017年12月28日
 * @version 1.01
 * 发票地址类interface
 */



public interface AddressFacade {
   //新增地址
    Object  addAddress(Address address);
    //修改地址
    int     updateAddress(Address address);
    //删除地址
    int     deletAddress(Address address);

    Object findAddress(Address address);
}
