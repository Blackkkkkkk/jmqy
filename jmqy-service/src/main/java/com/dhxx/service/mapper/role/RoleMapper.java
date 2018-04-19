package com.dhxx.service.mapper.role;

import java.util.List;

import com.dhxx.facade.entity.role.Role;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月08日
 * @version 1.01
 *
 */
public interface RoleMapper {

    void save(Role role);
    
    void delete(Role role);
    
    Long update(Role role);

    List<Role> all();
    
    List<Role> find(Role role);
    
    Role infoId(Long id);
    
}
