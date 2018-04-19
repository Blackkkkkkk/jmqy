package com.dhxx.service.mapper.charter.invoice;

import com.dhxx.facade.entity.charter.invoice.Address;

import java.util.List;

public interface AddressMapper {
     //新增地址
    void addAddress(Address address);
    //修改地址
    int     updateAddress(Address address);
    //删除地址
    int     deletAddress(Address address);

    List<Address> findAddress(Address address);
}
