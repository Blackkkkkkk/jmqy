package com.dhxx.service.biz.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhxx.facade.entity.role.Role;
import com.dhxx.service.mapper.role.RoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年08月08日
 * @version 1.01
 *
 */
@Service
@Transactional
public class RoleBiz {

    @Autowired
    private RoleMapper roleMapper;
    
    public void save(Role role){
        roleMapper.save(role);
    }
    
    public void delete(Role role){
        roleMapper.delete(role);
    }
    
    public Long update(Role role){
    	return roleMapper.update(role);
    }
    
    public Role infoId(Long id){
        return roleMapper.infoId(id);
    }
    
    public List<Role> all(){
        return roleMapper.all();
    }
    
    public PageInfo<Role> list(Role role) {
		PageHelper.startPage(role.getPageNum(), role.getPageSize());
        List<Role> list = roleMapper.find(role);
        PageInfo<Role> pageInfo = new PageInfo<Role>(list);
		return pageInfo;
	}

	public List<Role> find(Role role) {
		return roleMapper.find(role);
	}
    
}
