package com.dhxx.service.service.role;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.role.Role;
import com.dhxx.facade.service.role.RoleFacade;
import com.dhxx.service.biz.role.RoleBiz;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月08日
 * @version 1.01
 *
 */
@Service(protocol = {"dubbo"})
public class RoleFacadeImpl implements RoleFacade {
    
    @Autowired
    private RoleBiz roleBiz;

    @Override
    public Object save(Role role) {
        roleBiz.save(role);
        return role;
    }
    
    @Override
    public void delete(Role role) {
        roleBiz.delete(role);
    }
    
    @Override
    public Object update(Role role){
    	return roleBiz.update(role);
    }
    
    @Override
    public Object infoId(Long id) {
        return roleBiz.infoId(id);
    }

    @Override
    public Object all() {
        return roleBiz.all();
    }
    
    @Override
    public Object list(Role role) {
        return roleBiz.list(role);
    }

    @Override
    public Object find(Role role) {
        return roleBiz.find(role);
    }

}
