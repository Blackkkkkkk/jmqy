package com.dhxx.service.biz.company;

import java.util.Date;
import java.util.List;

import com.dhxx.common.constant.Constant;
import com.dhxx.facade.entity.permission.Permission;
import com.dhxx.facade.entity.role.Role;
import com.dhxx.facade.entity.user.User;
import com.dhxx.service.mapper.permission.PermissionMapper;
import com.dhxx.service.mapper.role.RoleMapper;
import com.dhxx.service.mapper.user.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhxx.facade.entity.company.Company;
import com.dhxx.service.mapper.company.CompanyMapper;

@Service
@Transactional
public class CompanyBiz {

    private static Logger log = LoggerFactory.getLogger(CompanyBiz.class);
    
	@Autowired
    private CompanyMapper companyMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
    private PermissionMapper permissionMapper;
	
	public Company findOne(Company company) {
        return companyMapper.findOne(company);
    }	

	public List<Company> find(Company company) {
		log.debug("CompanyBiz.find()");
		return companyMapper.find(company);
	}

    public void save(Company company) {
        companyMapper.save(company);
    }

	public void update(Company company) {
		companyMapper.update(company);
	}

    public PageInfo<Company> list(Company company) {
        PageHelper.startPage(company.getPageNum(), company.getPageSize());
	    List<Company> list = companyMapper.list(company);
        PageInfo<Company> pageInfo = new PageInfo<Company>(list);
	    return pageInfo;
    }

    public Company selectOne(Company company) {
        return companyMapper.selectOne(company);
    }

    public void set(Company company) {
        companyMapper.set(company);
    }

    public String init(User user) {
        //初始化公司
        Company company = new Company();
        company.setCompanyCode(Constant.CP_CODE);//企业编码
        company.setStatus(0L);//状态: 0:启用 1:禁用(待审核)
        company.setType(3L);//type: 1个人包主 2包车企业 3运输企业
        String roleDescribe = "运输企业";
        user.setStatus(0);// 状态 0: 启用 1:禁用
        company.setJobNumPre(user.getJobNumPre());//企业工号前缀
        company.setCompanyName(user.getCompanyName());//企业名称
        company.setCompanyAddress(user.getAddress());//企业地址
        company.setRegisterDate(new Date());//注册时间
        companyMapper.save(company);
        Long companyId = company.getId();
        String companyCode = Constant.CP_CODE+String.format("%010d", companyId);
        //初始化角色
        Role role = new Role();
        role.setRoleName(user.getRoleName());//  角色名称
        role.setRoleDescribe(roleDescribe);//  描述
        role.setRoleType(0);//  角色类型：0:public-公共的，1:private-私人的
        role.setCompanyCode(companyCode);//  所属企业编码(当角色类型：private-私人的时候为必须)
        role.setStatus(0);//状态: 0:启用 1:禁用
        roleMapper.save(role);

        //初始化菜单
        String menuIds = "35,36,37,38";//menuMapper.findMenuIds();
        Permission permission = new Permission();
        permission.setIds(menuIds);
        permission.setRoleId(role.getId());
        permission.setCompanyId(companyId+"");
        permissionMapper.save(permission);

        //初始化用户
        user.setUserCode(Constant.PN_CODE);
        user.setRole(role.getId()+"");
        user.setType(1);//住账号标识
        user.setCompanyCode(companyCode);
        user.setRegisterDate(new Date());
        userMapper.save(user);
        return companyCode;
    }
}
