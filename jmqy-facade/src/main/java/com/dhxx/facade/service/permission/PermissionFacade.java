package com.dhxx.facade.service.permission;

import com.dhxx.facade.entity.permission.Permission;
import com.dhxx.facade.entity.user.Userper;

/**
 * <p> 类说明 </p>
 * @author jhy
 * Date: 2017年3月31日
 * @version 1.01
 *
 */
public interface PermissionFacade {

    void save(Permission permission);
    
    Object list(Permission permission);
    
	Object authInfo();

    //用户权限、审批
    Object menuIds(Long roleId);

    Object userper(Userper user);

    void examine(Userper user);

    Object findUser(Userper user);

    Permission find(Permission permission);
}
