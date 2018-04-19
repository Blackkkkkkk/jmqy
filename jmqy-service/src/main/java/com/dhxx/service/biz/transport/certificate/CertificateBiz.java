package com.dhxx.service.biz.transport.certificate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dhxx.facade.entity.transport.certificate.Certificate;
import com.dhxx.service.mapper.transport.certificate.CertificateMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class CertificateBiz {

    private static Logger log = LoggerFactory.getLogger(CertificateBiz.class);
    
	@Autowired
    private CertificateMapper certificaterMapper;

	public PageInfo<Certificate> list(Certificate certificate) {
		log.debug("CertificateBiz.list()");
		PageHelper.startPage(certificate.getPageNum(), certificate.getPageSize());
        List<Certificate> list = certificaterMapper.list(certificate);
        PageInfo<Certificate> pageInfo = new PageInfo<Certificate>(list);
		return pageInfo;
	}

	public Integer statisticsDue(Certificate certificate) {
		log.debug("CertificateBiz.statisticsDue(),certificate="+JSONObject.toJSONString(certificate));
		return certificaterMapper.statisticsDue(certificate);
	}
	//返回过期司机的数据
	public  List<Certificate> drivingLicenseOverDue(Certificate certificate){
		log.debug("CertificateBiz.drivingLicenseOverDue()");
		return   certificaterMapper.drivingLicenseOverDue(certificate);
	}
	//返回过期车辆的数据
	public  List<Certificate> carLicenseOverDue(Certificate certificate){
		log.debug("CertificateBiz.carLicenseOverDue()");
		return   certificaterMapper.carLicenseOverDue(certificate);
	}
}
