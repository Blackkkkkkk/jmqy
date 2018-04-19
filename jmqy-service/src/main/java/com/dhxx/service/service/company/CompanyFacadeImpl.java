package com.dhxx.service.service.company;

import com.dhxx.facade.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.company.Company;
import com.dhxx.facade.service.company.CompanyFacade;
import com.dhxx.service.biz.company.CompanyBiz;
/**
 * 
 * @author hanrs
 *
 */
@Service(protocol = {"dubbo"})
public class CompanyFacadeImpl implements CompanyFacade {
    
	private static Logger log = LoggerFactory.getLogger(CompanyFacadeImpl.class);
	
    @Autowired
    private CompanyBiz companyBiz;
    
    @Override
	public Object findOne(Company company) {
		return companyBiz.findOne(company);
	}

	@Override
	public Object find(Company company) {
		log.debug("CompanyFacadeImpl.find()");
		return companyBiz.find(company);
	}

    @Override
    public void save(Company company) {
        companyBiz.save(company);
    }

	@Override
	public void update(Company company) {
		companyBiz.update(company);

	}

    @Override
    public Object list(Company company) {
        return companyBiz.list(company);
    }

    @Override
    public Object selectOne(Company company) {
        return companyBiz.selectOne(company);
    }

    @Override
    public void set(Company company) {
        companyBiz.set(company);
    }

    @Override
    public Object init(User user) {
        return companyBiz.init(user);
    }

}
