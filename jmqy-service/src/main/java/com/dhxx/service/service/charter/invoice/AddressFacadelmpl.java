package com.dhxx.service.service.charter.invoice;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.charter.invoice.Address;
import  com.dhxx.facade.service.charter.invoice.AddressFacade;
import com.dhxx.service.biz.charter.invoice.AddressBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Service(protocol = {"dubbo"})
public class AddressFacadelmpl implements AddressFacade{

    private static Logger log = LoggerFactory.getLogger(InvoiceFacadeImpl.class);

   @Autowired
   private AddressBiz addressBiz;

    @Override
    public Object addAddress(Address address) {
        log.info("AddressFacadelmpl.save()");
        addressBiz.addAddress(address);
        return address;
    }

    @Override
    public  int  updateAddress(Address address) {
        log.info("AddressFacadelmpl.updateAddress()");
        return addressBiz.updateAddress(address);
    }

    @Override
    public  int deletAddress(Address address){
        log.info("AddressFacadelmpl.deletAddress()");
        return addressBiz.deletAddress(address);

    }


    @Override
    public Object findAddress(Address address) {
        log.info("InvoiceFacadeImpl.list()");
        return addressBiz.findAddress(address);
    }
}
