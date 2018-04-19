package com.dhxx.web.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.dhxx.common.constant.Constant;
import com.dhxx.facade.entity.menu.Menu;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.menu.MenuFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.google.common.base.Objects;


public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
	private UserFacade userFacade;

    @Autowired
	private MenuFacade menuFacade;



	/**
	 * 认证回调函数,登录时调用.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

		//UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		InitUsernamePasswordToken token = (InitUsernamePasswordToken) authcToken;
		String loginName = token.getUsername();
		User user = new User();
		user.setUserAccount(loginName);
		List<User> users = (List<User>)userFacade.find(user);
		if (CollectionUtils.isNotEmpty(users)) {
			user = users.get(0);
		    int status = user.getStatus();
		    if(status != 0 || user.getRoleStatus() != 0){//如果帐号被禁用，输出
		        throw new DisabledAccountException();
		    }
		   String roleName = user.getRoleName();
		    if(!"sys_admin".equals(roleName)){
                Menu menu = new Menu();
                menu.setRoleId(Long.parseLong(user.getRole()));
                List<Menu> menuList = (List<Menu>) menuFacade.haveMenus(menu);
                if(org.springframework.util.CollectionUtils.isEmpty(menuList)){//无分配权限
                    throw new UnauthorizedException();
                }
            }

			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();

		/*	if (token.getActionType() == 1){
				info = new  InitSimpleAuthenticationInfo("wx13148913794","123456",getName());
				InitSimpleAuthenticationInfo infoInit = new InitSimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getUserAccount(), user.getUserName(),
						user.getPhone(), user.getRole(), user.getRoleName(), user.getCompanyId(), user.getCompanyCode(), user.getCompanyName(), user.getCompanyType()), user.getUserPassword(), getName());

				info.setCredentials(infoInit.getCredentials());
				info.setCredentialsSalt(infoInit.getCredentialsSalt());
				info.setPrincipals(infoInit.getPrincipals());
			}else{*/
				info = new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getUserAccount(), user.getUserName(),
						user.getPhone(), user.getRole(), user.getRoleName(), user.getCompanyId(), user.getCompanyCode(), user.getCompanyName(), user.getCompanyType()), user.getUserPassword(), getName());
		//	}
		    String credentialsSalt = user.getUserAccount()+user.getSalt();
		    info.setCredentialsSalt(ByteSource.Util.bytes(credentialsSalt)); 
            return info;
		} else {
			return null;
		}
	}

    public UserFacade getUserFacade() {
        return userFacade;
    }



    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public MenuFacade getMenuFacade() {
        return menuFacade;
    }

    public void setMenuFacade(MenuFacade menuFacade) {
        this.menuFacade = menuFacade;
    }


    /**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	    ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<String> roleList = new ArrayList<String>();
        roleList.add(shiroUser.roleName);
        info.addRoles(roleList);
		return info;
	}

    /**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher(){

		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constant.HASH_ALGORITHM);
		matcher.setHashIterations(Constant.HASH_INTERATIONS);
		/*HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constant.HASH_ALGORITHM);
		matcher.setHashIterations(Constant.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);*/
		//setCredentialsMatcher(matcher);
		/*System.out.println(new CustomCredentialsMatcher());
		System.out.println(matcher);*/
		setCredentialsMatcher(new CustomCredentialsMatcher());
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Long id;
		public String userAccount;
		public String userName;
		public String phone;
		public String roleId;
		public String roleName;
		public Long companyId;
		public String companyCode;
		public String companyName;
		public Long companyType;//企业类型：0平台方 1个人包主 2包车企业 3运输企业

		public ShiroUser(Long id, String userAccount, String userName, String phone, String roleId,
						 String roleName, Long companyId, String companyCode, String companyName, Long companyType){
			this.id = id;
			this.userAccount = userAccount;
			this.userName = userName;
			this.phone = phone;
			this.roleId = roleId;
			this.roleName = roleName;
			this.companyId = companyId;
			this.companyCode = companyCode;
			this.companyName = companyName;
			this.companyType = companyType;
		}

		public String getName() {
			return userName;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return userAccount.toString();
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(userAccount);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (userAccount == null) {
				if (other.userAccount != null) {
					return false;
				}
			} else if (!userAccount.equals(other.userAccount)) {
				return false;
			}
			return true;
		}
	}
}
