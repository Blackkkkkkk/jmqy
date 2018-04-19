package com.dhxx.facade.service.role;

import com.dhxx.facade.entity.role.Role;

/**
 * <p> 类说明 </p>
 * @author jhy
 * Date: 2017年3月30日
 * @version 1.01
 * 系统角色
 */
public interface RoleFacade {
    
	Object save(Role role);
    
    void delete(Role role);
    
    Object update(Role role);

    Object all();
    
    Object infoId(Long id);

	Object list(Role role);

	Object find(Role role);
    
}
