package com.dhxx.facade.service.charter.invoice;

import com.dhxx.facade.entity.charter.invoice.Address;
import com.dhxx.facade.entity.charter.invoice.Invoice;
import com.dhxx.facade.entity.order.Order;

import java.util.Map;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月07日
 * @version 1.01
 * 订单发票interface
 */
public interface InvoiceFacade {
	Object save(Invoice invoice);

	Map<String,Object> find(Invoice invoice);

	int  update(Invoice invoice);

	Map<String,Object> selectInvoiceTem(Invoice invoice);

	Object saveInvoiceTem(Invoice invoice);

	int  delInvoiceTem(Invoice invoice);
}
