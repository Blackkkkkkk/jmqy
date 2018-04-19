package com.dhxx.service.mapper.permission;

import java.util.List;

import com.dhxx.facade.entity.permission.AuthInfo;
import com.dhxx.facade.entity.permission.Permission;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.entity.user.Userper;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月08日
 * @version 1.01
 *
 */
public interface PermissionMapper {

    void save(Permission permission);
    
    List<Permission> list(Permission permission);
    
    void delete(Permission permission);
    
    void deleteByRoleId(Permission permission);
    
    List<Permission> all();
    
    List<AuthInfo> authInfo();

    List<Userper> userper(Userper user);

    void examineUser(Userper user);

    void examineCompany(Userper user);

    List<Long> menuIds(Long roleId);

    User findUser(Userper user);

    Permission find(Permission permission);

}
