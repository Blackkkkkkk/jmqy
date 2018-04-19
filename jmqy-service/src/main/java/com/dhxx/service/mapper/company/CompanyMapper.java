package com.dhxx.service.mapper.company;

import java.util.List;

import com.dhxx.facade.entity.company.Company;

/**
 * @author dingbin
 * @date 2017/9/17
 * @description
 */
public interface CompanyMapper {
	
    Company findOne(Company company);
	 
    List<Company> find(Company company);

    void save(Company company);

	void update(Company company);

    List<Company> list(Company company);

    Company selectOne(Company company);

    void set(Company company);
}
