package com.dhxx.service.mapper.transport.certificate;

import java.util.List;

import com.dhxx.facade.entity.transport.certificate.Certificate;

public interface CertificateMapper {

	List<Certificate> list(Certificate certificate);
	
	Integer statisticsDue(Certificate certificate);

	List<Certificate> drivingLicenseOverDue(Certificate certificate);

	List<Certificate> carLicenseOverDue(Certificate certificate);
}
