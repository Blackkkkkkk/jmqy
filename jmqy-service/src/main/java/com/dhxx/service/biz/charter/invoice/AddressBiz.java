package com.dhxx.service.biz.charter.invoice;


import com.dhxx.facade.entity.charter.invoice.Address;
import com.dhxx.service.mapper.charter.invoice.AddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressBiz {

    private static Logger log = LoggerFactory.getLogger(AddressBiz.class);

    @Autowired
    private AddressMapper addressMapper;

    public void addAddress(Address address){
        log.info("AddressMapper.addAddress()");
        addressMapper.addAddress(address);
    }

    public int updateAddress(Address address){
        log.info("AddressMapper.addAddress()");
        return addressMapper.updateAddress(address);
    }

    public int deletAddress(Address address){
        log.info("AddressMapper.deletAddress()");
        return addressMapper.deletAddress(address);
    }

    //查看发票的个人保存列表
    public List<Address> findAddress(Address address){

        log.debug("invoiceMapper.findAddress()");
        return addressMapper.findAddress(address);
    }
}
