package com.dhxx.service.biz.charter.invoice;

import com.dhxx.facade.entity.charter.invoice.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhxx.facade.entity.charter.invoice.Invoice;
import com.dhxx.service.mapper.charter.invoice.InvoiceMapper;

import java.util.List;
import java.util.Map;

/**
 * <p> 发票表 </p>
 * @author dingbin
 * Date: 2017年09月11日
 * @version 1.01
 *
 */
@Service
@Transactional
public class InvoiceBiz {

	private static Logger log = LoggerFactory.getLogger(InvoiceBiz.class);
	
	@Autowired
	private InvoiceMapper invoiceMapper;

	//保存
	public void save(Invoice invoice){
		log.info("InvoiceBiz.save()");
		invoiceMapper.save(invoice);
	}

	//查询
	public Map<String, Object> find(Invoice invoice){
		log.info("InvoiceBiz.find()");
		return  invoiceMapper.find(invoice);
	}

	//更新
	public int update(Invoice invoice){
		log.info("InvoiceBiz.update()");
		return  invoiceMapper.update(invoice);
	}

	//查询模板
	public Map<String, Object> selectInvoiceTem(Invoice invoice){
		log.info("InvoiceBiz.selectInvoiceTem()");
		return  invoiceMapper.selectInvoiceTem(invoice);
	}


	//更新模板
	public int delInvoiceTem(Invoice invoice){
		log.info("InvoiceBiz.delInvoiceTem()");
		return  invoiceMapper.delInvoiceTem(invoice);
	}

	//保存
	public void saveInvoiceTem(Invoice invoice){
		log.info("InvoiceBiz.saveInvoiceTem()");
		invoiceMapper.saveInvoiceTem(invoice);
	}
}
