package com.dhxx.service.mapper.charter.invoice;

import com.dhxx.facade.entity.charter.invoice.Invoice;
import com.dhxx.facade.entity.charter.invoice.Address;

import java.util.List;
import java.util.Map;

public interface InvoiceMapper {
	
	void save(Invoice invoice);

	Map<String,Object> find(Invoice invoice);

	int  update(Invoice invoice);


	Map<String,Object> selectInvoiceTem(Invoice invoice);

	void saveInvoiceTem(Invoice invoice);

	int  delInvoiceTem(Invoice invoice);
}
