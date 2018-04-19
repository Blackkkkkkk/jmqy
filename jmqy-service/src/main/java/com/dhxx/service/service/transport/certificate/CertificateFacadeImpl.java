package com.dhxx.service.service.transport.certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.transport.certificate.Certificate;
import com.dhxx.facade.service.transport.certificate.CertificateFacade;
import com.dhxx.service.biz.transport.certificate.CertificateBiz;

import java.util.List;

@Service(protocol = {"dubbo"})
public class CertificateFacadeImpl implements CertificateFacade {
    
	private static Logger log = LoggerFactory.getLogger(CertificateFacadeImpl.class);
	
    @Autowired
    private CertificateBiz certificateBiz;

	@Override
	public Object list(Certificate certificate) {
		log.debug("CertificateFacadeImpl.list()");
		return certificateBiz.list(certificate);
	}

	@Override
	public Object statisticsDue(Certificate certificate) {
		log.debug("CertificateFacadeImpl+.statisticsDue()");
		return certificateBiz.statisticsDue(certificate);
	}

	@Override
	public List<Certificate> drivingLicenseOverDue(Certificate certificate){
		log.debug("CertificateFacadeImpl.drivingLicenseOverDue()");
		return certificateBiz.drivingLicenseOverDue(certificate);
	}

	@Override
	public List<Certificate> carLicenseOverDue(Certificate certificate){
		log.debug("CertificateFacadeImpl.carLicenseOverDue()");
		return certificateBiz.carLicenseOverDue(certificate);
	}
}
