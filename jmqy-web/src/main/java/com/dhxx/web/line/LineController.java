package com.dhxx.web.line;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.line.Line;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.service.line.LineFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月28日
 * @version 1.01
 * 线路管理
 */
@Controller
@RequestMapping("line")
@SuppressWarnings("unchecked")
public class LineController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(LineController.class);
	
    @Reference(protocol="dubbo")
    private LineFacade lineFacade;
    
    @Reference(protocol="dubbo")
    private OrderFacade orderFacade;
    
    @ResponseBody
    @RequestMapping(value = "find", method = RequestMethod.POST)
    public Map<String, Object> find(Line line) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	line.setTop(4);
    	List<Line> lines = (List<Line>) lineFacade.find(line);
    	map.put("lines", lines);
        return map;
    }
	
	//更新
   @ResponseBody
   @RequestMapping(value = "update", method = RequestMethod.POST)
   public Long update(Line line, Model model) {
	   try{
		   return (Long) lineFacade.update(line);
       } catch (Exception e) {
    	   log.error(e.getMessage());
    	   return -1l;
       }
   }
   
   //新增
   @ResponseBody
   @RequestMapping(value = "save", method = RequestMethod.POST)
   public Map<String, Object> saveOrUpdate(Line line){
	   Map<String, Object> map = new HashMap<String, Object>();
	   boolean state = true;
	   try{
           ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
           line.setCreateUser(shiroUser.userAccount);
           line.setCreateCompany(shiroUser.companyCode);
           lineFacade.save(line);
       } catch (Exception e) {
    	   state = false;
    	   log.error(e.getMessage());
       }
	   map.put("state", state);
       return map;
   }
   
   //收藏路线
   @ResponseBody
   @RequestMapping(value = "collect", method = RequestMethod.POST)
   public Integer collect(Order order){
	   try{
           ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
           List<Order> orders=(List<Order>)orderFacade.find(order);
		   if(!CollectionUtils.isEmpty(orders)){
				order = orders.get(0);
			}
		    //保存要收藏路线
			Line line = new Line();
	   		BeanUtils.copyProperties(order, line);
	   		line.setId(null);
	   		line.setType(1);//类型：1收藏，2搜索
	   		line.setCreateCompany(shiroUser.companyId+"");
	   		line.setCreateUser(shiroUser.id+"");
	   		lineFacade.saveOrUpdate(line);
	   } catch (Exception e) {
     	   log.error(e.getMessage());
     	   return -1;
        }
 	   return 0;
   }
    
}