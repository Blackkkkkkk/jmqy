package com.dhxx.facade.service.charter.complaint;

import com.dhxx.facade.entity.charter.complaint.Complaint;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月07日
 * @version 1.01
 * 订单投诉interface
 */
public interface ComplaintFacade {

	Object save(Complaint complaint);

	Object list(Complaint complaint);

	Object find(Complaint complaint);

	void update(Complaint complaint);

}
