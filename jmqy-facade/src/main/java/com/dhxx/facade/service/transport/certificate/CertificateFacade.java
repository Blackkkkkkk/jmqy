package com.dhxx.facade.service.transport.certificate;

import com.dhxx.facade.entity.transport.certificate.Certificate;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月18日
 * @version 1.01
 * 运输方证件管理interface
 */
public interface CertificateFacade {
	
	Object list(Certificate certificate);
	
	Object statisticsDue(Certificate certificate);

	Object drivingLicenseOverDue(Certificate certificate);

	Object carLicenseOverDue(Certificate certificate);
}
