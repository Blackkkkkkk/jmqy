package com.dhxx.service.service.charter.complaint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.charter.complaint.Complaint;
import com.dhxx.facade.service.charter.complaint.ComplaintFacade;
import com.dhxx.service.biz.charter.complaint.ComplaintBiz;

/**
 * 订单评价
 * @author dingbin
 *
 */
@Service(protocol = {"dubbo"})
public class ComplaintFacadeImpl implements ComplaintFacade{

	private static Logger log = LoggerFactory.getLogger(ComplaintFacadeImpl.class);

	@Autowired
	private ComplaintBiz complaintBiz;
	
	@Override
	public Object save(Complaint complaint) {
		log.info("ComplaintFacadeImpl.save()");
		complaintBiz.save(complaint);
		return complaint;
	}

    @Override
    public Object list(Complaint complaint) {
        return complaintBiz.list(complaint);
    }
    
    @Override
    public Object find(Complaint complaint) {
        return complaintBiz.find(complaint);
    }

	@Override
	public void update(Complaint complaint) {
		complaintBiz.update(complaint);
	}

}