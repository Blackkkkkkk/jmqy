package com.dhxx.service.service.permission;

import com.dhxx.facade.entity.permission.Permission;
import com.dhxx.facade.entity.user.Userper;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.service.permission.PermissionFacade;
import com.dhxx.service.biz.permission.PermissionBiz;

/**
 * <p> 类说明 </p>
 * @author jhy
 * Date: 2017年3月31日
 * @version 1.01
 *
 */
@Service(protocol = {"dubbo"})
public class PermissionFacadeImpl implements PermissionFacade {
    
    @Autowired
    private PermissionBiz permissionBiz;

    @Override
    public void save(Permission permission) {
        permissionBiz.save(permission);
    }

    @Override
    public Object list(Permission permission) {
        return permissionBiz.list(permission);
    }

	@Override
	public Object authInfo() {
		return permissionBiz.authInfo();
	}

    @Override
    public Object menuIds(Long roleId) {
        return permissionBiz.menuIds(roleId);
    }

    @Override
    public Object userper(Userper user) {
        return permissionBiz.userper(user);
    }

    @Override
    public void examine(Userper user) { permissionBiz.examine(user); }

    @Override
    public Object findUser(Userper user) { return permissionBiz.findUser(user); }

    @Override
    public  Permission find(Permission permission){return permissionBiz.find(permission);}

}
