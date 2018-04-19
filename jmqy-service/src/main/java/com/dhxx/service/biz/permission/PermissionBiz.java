package com.dhxx.service.biz.permission;

import java.util.ArrayList;
import java.util.List;

import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.entity.user.Userper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dhxx.facade.entity.permission.AuthInfo;
import com.dhxx.facade.entity.permission.Permission;
import com.dhxx.service.mapper.permission.PermissionMapper;

/**
 * <p> 类说明 </p>
 * @author jhy
 * Date: 2017年3月31日
 * @version 1.01
 *
 */
@Service
@Transactional
public class PermissionBiz {
    
    @Autowired
    private PermissionMapper permissionMapper;
    
    public List<Permission> list(Permission permission){
        return permissionMapper.list(permission);
    }
    
    public void save(Permission permission){
        String ids = permission.getIds();
        String a = permission.getCompanyId();
        if(permission.getCompanyId() != null){
        	permissionMapper.delete(permission);
        }else{
        	permissionMapper.deleteByRoleId(permission);
        }
        if(StringUtils.isNotEmpty(ids)){
           permissionMapper.save(permission);
        }
    }


    
    public List<AuthInfo> authInfo(){
        return permissionMapper.authInfo();
    }

    public PageInfo<Userper> userper(Userper user) {
        PageHelper.startPage(user.getPageNum(), user.getPageSize());
        List<Userper> list = permissionMapper.userper(user);
        PageInfo<Userper> pageInfo = new PageInfo<Userper>(list);
        return pageInfo;
    }

    public void examine(Userper user) {
        //permissionMapper.examineUser(user);
        permissionMapper.examineCompany(user);
    }

    public List<Long> menuIds(Long roleId) {
        return permissionMapper.menuIds(roleId);
    }

    public User findUser(Userper user) {
        return permissionMapper.findUser(user);
    }

    public  Permission find(Permission permission){ return permissionMapper.find(permission); }
}
