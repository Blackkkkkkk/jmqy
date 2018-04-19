package com.dhxx.facade.service.company;

import com.dhxx.facade.entity.company.Company;
import com.dhxx.facade.entity.user.User;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月18日
 * @version 1.01
 * 企业管理interface
 */
public interface CompanyFacade {
	
	Object findOne(Company company);
	
	Object find(Company company);

	void save(Company company);

	void update(Company company);

    Object list(Company company);

    Object selectOne(Company company);

    void set(Company company);

    Object init(User user);
}
