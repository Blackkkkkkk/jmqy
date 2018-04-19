package com.dhxx.web.shiro;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dhxx.facade.service.user.UserFacade;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dhxx.common.constant.Constant;
import com.dhxx.facade.entity.permission.AuthInfo;
import com.dhxx.facade.service.permission.PermissionFacade;

/**
 * <p> 类说明 </p>
 * @author jhy
 * Date: 2017年4月5日
 * @version 1.01
 * 动态生成 filterChainDefinitions
 */
@Service(value="authService")
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private PermissionFacade permissionFacade;

    private static Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    
    @Resource
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
    
    @Override
    public String loadFilterChainDefinitions() {
        StringBuffer sb = new StringBuffer();
        sb.append(getOperationAuthRule());
        sb.append(getFixedAuthRule());
        log.debug("filterChainDefinitions");
        return sb.toString();
    }
    
    /**
     * 从数据库获取动态权限验证规则串
     */
    public String getOperationAuthRule(){
        List<AuthInfo> infoList = (List<AuthInfo>) userFacade.authInfo();
        StringBuffer sb = new StringBuffer();
        if(infoList != null && infoList.size() > 0){
            for (AuthInfo info : infoList) {
                String url = "/"+info.getMenuUrl();
                List<String> roleList = info.getRoleList();
                if(roleList != null && roleList.size() > 0){
                    sb.append(url).append(" = ").append("authc, roles[\"");
                    for (int i=0; i<roleList.size(); i++) {
                        if(i == roleList.size()-1){
                            sb.append(roleList.get(i));
                        }else{
                            sb.append(roleList.get(i) + ",");
                        }
                    }
                    sb.append("\"]").append(Constant.CRLF);
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * 从配额文件获取固定权限验证规则串
     */                 
    private String getFixedAuthRule(){
        StringBuffer sb = new StringBuffer();
        Ini ini = new Ini();
        ini.loadFromPath(Constant.SHIRO_CONFIG);
        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
        if (!CollectionUtils.isEmpty(section)) {  
            for (Map.Entry<String, String> entry : section.entrySet()) {
                sb.append(entry.getKey()).append(" = ").append(entry.getValue()).append(Constant.CRLF);
            }
        }
        return sb.toString();
    }
    
    @Override
    public synchronized void reCreateFilterChains() {
        AbstractShiroFilter shiroFilter = null;
        try{
            shiroFilter = (AbstractShiroFilter)shiroFilterFactoryBean.getObject();
        } catch(Exception e) {
            log.error("getShiroFilter from shiroFilterFactoryBean error!", e);
            throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
        }
        
        PathMatchingFilterChainResolver filterChainResolver =(PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager =(DefaultFilterChainManager)filterChainResolver.getFilterChainManager();
  
        //清空老的权限控制
        manager.getFilterChains().clear();
        
        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
        shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
        //重新构建生成
        Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
         for(Map.Entry<String, String> entry : chains.entrySet()) {
             String url = entry.getKey();
             String chainDefinition =entry.getValue().trim().replace(" ", "");
             manager.createChain(url,chainDefinition);
         }
    }

}
