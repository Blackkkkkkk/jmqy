package com.dhxx.service.biz.user;

import java.util.Date;
import java.util.List;

import com.dhxx.facade.entity.money.Money;
import com.dhxx.service.mapper.money.MoneyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhxx.common.constant.Constant;
import com.dhxx.facade.entity.company.Company;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.permission.Permission;
import com.dhxx.facade.entity.role.Role;
import com.dhxx.facade.entity.user.User;
import com.dhxx.service.mapper.company.CompanyMapper;
import com.dhxx.service.mapper.credit.CreditMapper;
import com.dhxx.service.mapper.menu.MenuMapper;
import com.dhxx.service.mapper.permission.PermissionMapper;
import com.dhxx.service.mapper.role.RoleMapper;
import com.dhxx.service.mapper.user.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class UserBiz {

    private static Logger log = LoggerFactory.getLogger(UserBiz.class);
    
	@Autowired
    private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private CreditMapper creditMapper;

	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private MoneyMapper moneyMapper;

	public PageInfo<User> list(User user) {
		log.debug("UserBiz.list()");
		PageHelper.startPage(user.getPageNum(), user.getPageSize());
        List<User> list = userMapper.find(user);
        PageInfo<User> pageInfo = new PageInfo<User>(list);
		return pageInfo;
	}

	public List<User> find(User user) {
		log.debug("UserBiz.info()");
		/*String userName = user.getUserAccount();
		if(StringUtils.isNotEmpty(userName)){
            String[] nameArr = userName.split(",");
            if(nameArr.length > 1){
                user.setUserAccount(nameArr[0]);
                user.setRoleName(nameArr[1]);
            }
        }*/
		return userMapper.find(user);
	}
	
	public void save(User user) {
		user.setRegisterDate(new Date());
		userMapper.save(user);
	}
	
	public Long update(User user) {
		return userMapper.update(user);
	}
	
	public void delete(User user) {
		log.debug("UserBiz.delete()");
		userMapper.delete(user);
	}

	public  String  wechatfind(String s){return userMapper.wechatfind(s);}

    public void init(User user) {
	    String roleDescribe = "";
	    String roleName = user.getRoleName();
	    //初始化公司
        Company company = new Company();
        company.setCompanyCode(Constant.CP_CODE);//企业编码
        if("charter".equals(roleName)){
            company.setStatus(0L);//状态: 0:启用 1:禁用(待审核)
            company.setType(1L);//type: 1个人包主 2包车企业 3运输企业
            roleDescribe = "个人租车";
            user.setCompanyName(roleDescribe);
            user.setStatus(0);// 状态 0: 启用 1:禁用
        }
        if("charter_admin".equals(roleName)){
            company.setStatus(1L);//状态: 0:启用 1:禁用(待审核)
            company.setType(2L);//type: 1个人包主 2包车企业 3运输企业
            roleDescribe = "包车企业";
            user.setStatus(1);// 状态 0: 启用 1:禁用
        }
        if("transport_admin".equals(roleName)){
            company.setStatus(1L);//状态: 0:启用 1:禁用(待审核)
            company.setType(3L);//type: 1个人包主 2包车企业 3运输企业
            roleDescribe = "运输企业";
            user.setStatus(1);// 状态 0: 启用 1:禁用
        }
        company.setCompanyName(user.getCompanyName());//企业名称
        company.setCompanyAddress(user.getAddress());//企业地址
        company.setRegisterDate(new Date());//注册时间
        companyMapper.save(company);
        Long companyId = company.getId();
        String companyCode = Constant.CP_CODE+String.format("%010d", companyId);
        //初始化角色
        Role role = new Role();
        role.setRoleName(roleName);//  角色名称
        role.setRoleDescribe(roleDescribe);//  描述
        role.setRoleType(0);//  角色类型：0:public-公共的，1:private-私人的
        role.setCompanyCode(companyCode);//  所属企业编码(当角色类型：private-私人的时候为必须)
        role.setStatus(0);//状态: 0:启用 1:禁用
        roleMapper.save(role);
        //初始化用户
        user.setUserCode(Constant.PN_CODE);
        user.setRole(role.getId()+"");
        user.setType(1);//主账号标识
        user.setCompanyCode(companyCode);
	    user.setRegisterDate(new Date());
        userMapper.save(user);
        //初始化信用额度
        if("charter_admin".equals(roleName)){
            Credit credit = new Credit();
            credit.setUserId(user.getId());
            credit.setTotalCredit(0D);
            credit.setConsumeCredit(0D);
            credit.setStockCredit(0D);
            creditMapper.save(credit);
        }

        //初始化余额额度
        if("charter_admin".equals(roleName)){
            Money money = new Money();
            money.setUserId(user.getId());
            money.setTotalMoney(0D);
            money.setConsumeMoney(0D);
            money.setStockMoney(0D);
            moneyMapper.save(money);
        }

        if("charter".equals(roleName)){
            //初始化菜单
            String menuIds = menuMapper.findMenuIds();
            Permission permission = new Permission();
            permission.setIds(menuIds);
            permission.setRoleId(role.getId());
            permission.setCompanyId(companyId+"");
            permissionMapper.save(permission);
            
            Credit credit = new Credit();
            credit.setUserId(user.getId());
            credit.setTotalCredit(0D);
            credit.setConsumeCredit(0D);
            credit.setStockCredit(0D);
            creditMapper.save(credit);


            Money money = new Money();
            money.setUserId(user.getId());
            money.setTotalMoney(0D);
            money.setConsumeMoney(0D);
            money.setStockMoney(0D);
            moneyMapper.save(money);
        }
    }

}
