package com.dhxx.service.service.charter.invoice;

import com.dhxx.facade.entity.charter.invoice.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.charter.invoice.Invoice;
import com.dhxx.facade.service.charter.invoice.InvoiceFacade;
import com.dhxx.service.biz.charter.invoice.InvoiceBiz;

import java.util.Map;

/**
 * 订单发票
 * @author dingbin
 *
 */
@Service(protocol = {"dubbo"})
public class InvoiceFacadeImpl  implements InvoiceFacade{
	private static Logger log = LoggerFactory.getLogger(InvoiceFacadeImpl.class);

	@Autowired
	private InvoiceBiz invoiceBiz; 
	
	@Override
	public Object save(Invoice invoice) {
		log.info("InvoiceFacadeImpl.save()");
		invoiceBiz.save(invoice);
		return invoice;
	}

	@Override
	public Map<String,Object> find(Invoice invoice) {
		log.info("InvoiceFacadeImpl.find()");
		return invoiceBiz.find(invoice);
	}

	@Override
	public int  update(Invoice invoice) {
		log.info("InvoiceFacadeImpl.update()");
		return invoiceBiz.update(invoice);
	}

	@Override
	public Map<String,Object> selectInvoiceTem(Invoice invoice) {
		log.info("InvoiceFacadeImpl.selectInvoiceTem()");
		return invoiceBiz.selectInvoiceTem(invoice);
	}


	@Override
	public int  delInvoiceTem(Invoice invoice) {
		log.info("InvoiceFacadeImpl.delInvoiceTem()");
		return invoiceBiz.delInvoiceTem(invoice);
	}

	@Override
	public Object saveInvoiceTem(Invoice invoice) {
		log.info("InvoiceFacadeImpl.saveInvoiceTem()");
		invoiceBiz.saveInvoiceTem(invoice);
		return invoice;
	}



}
