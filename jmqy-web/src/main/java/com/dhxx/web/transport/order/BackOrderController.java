package com.dhxx.web.transport.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dhxx.facade.entity.charter.complaint.Complaint;
import com.dhxx.facade.entity.charter.evaluate.Evaluate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.common.constant.Constant;
import com.dhxx.common.util.DataUtils;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.facade.entity.transport.order.BackOrder;
import com.dhxx.facade.service.match.MatchFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.transport.car.CarFacade;
import com.dhxx.facade.service.transport.order.BackOrderFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月16日
 * @version 1.01
 * 运输方回程单管理
 */
@Controller
@RequestMapping("transport/backOrder")
@SuppressWarnings("unchecked")
public class BackOrderController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(BackOrderController.class);
	
    @Reference(protocol="dubbo")
    private BackOrderFacade backOrderFacade;

    @Reference(protocol="dubbo")
    private OrderFacade orderFacade;
    
    @Reference(protocol="dubbo")
    private CarFacade carFacade;
    
    @Reference(protocol="dubbo")
    private MatchFacade matchFacade;
    
    @RequestMapping(value = "list/{type}")
    public String list(BackOrder backOrder, Model model, HttpServletRequest requst) {
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		backOrder.setCreateCompany(shiroUser.companyCode);
    	PageInfo<BackOrder> backOrders = (PageInfo<BackOrder>) backOrderFacade.list(backOrder);
        model.addAttribute("backOrder", backOrder);
        model.addAttribute("backOrders", backOrders);
        return "transport/backOrder/list";
    }


	//订单详情
	@RequestMapping(value = "query")
	public String query(BackOrder backOrder,Model model,HttpServletRequest request){

		List<BackOrder> backOrders = (List<BackOrder>) backOrderFacade.find(backOrder);
		model.addAttribute("backOrder",backOrders.get(0));
		return "transport/backOrder/details";
	}





	@RequestMapping(value = "form")
    public String form(BackOrder backOrder, Model model, HttpServletRequest requst) {
		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
		if(!StringUtils.isEmpty(backOrder.getOrderCode())){
			List<BackOrder> backOrders = (List<BackOrder>) backOrderFacade.find(backOrder);
			if(!CollectionUtils.isEmpty(backOrders)){
				backOrder = backOrders.get(0);
				
				if(backOrder.getType() == 1){//空程单时，单相关的去程单查询出来
					Order order = new Order();
					order.setOrderCode(backOrder.getCharterOrderCode());
					List<Order> orders = (List<Order>)orderFacade.find(order);
					if(!CollectionUtils.isEmpty(orders)){
						order = orders.get(0);
					}
					model.addAttribute("order", order);
				}
			}	
		}else if(backOrder.getId() != null){//运输方接收订单后生成一个返程单
			Order order = new Order();
			order.setId(backOrder.getId());
			backOrder.setId(null);
			List<Order> orders = (List<Order>)orderFacade.find(order);
			if(!CollectionUtils.isEmpty(orders)){
				order = orders.get(0);
				backOrder.setCharterOrderCode(order.getOrderCode());
				backOrder.setStartPoint(order.getEndPoint());
				backOrder.setStartLng(order.getEndLng());
				backOrder.setStartLat(order.getEndLat());
				backOrder.setStartArea(order.getEndArea());
				backOrder.setStartCity(order.getEndCity());
				backOrder.setEndPoint(order.getStartPoint());
				backOrder.setEndLng(order.getStartLng());
				backOrder.setEndLat(order.getStartLat());
				backOrder.setEndArea(order.getStartArea());
				backOrder.setEndCity(order.getStartCity());
				backOrder.setViaPoint(order.getViaPoint());
				long amount = (long) (order.getAmount()*0.8);
				if(amount > 100) {
					backOrder.setDiscountPrices(Math.round(amount/100.0)*100.0);
				}else {
					backOrder.setDiscountPrices(amount*1.0);
				}
				backOrder.setCarNum(order.getCarNum());
				backOrder.setCarCode(order.getCarCode());
				backOrder.setDriverCode(order.getDriverCode());
				if(order.getDistance()!= null){
					backOrder.setRange(String.format("%.2f",order.getDistance()*0.3));//允许偏离公里数（默认30%）
				}
				Car car = new Car();
        		car.setCarCode(order.getCarCode());
        		List<Car> carList = (List<Car>)carFacade.find(car);
        		car = carList.get(0);
				backOrder.setSeatNumber(car.getSeatNumber());
				backOrder.setCarType(car.getCarType());
				backOrder.setEarliestDepartureTime(DataUtils.getByMinute(order.getBoardingTime(), (int) Math.ceil(order.getDuration()*1.30/60.0)));
				backOrder.setLatestDepartureTime(DataUtils.getByHour(backOrder.getEarliestDepartureTime(),2));
				backOrder.setEffeTime(3);
				backOrder.setType(1);//1：空程，2单程
			}
			model.addAttribute("order", order);
		}else {
			backOrder.setEffeTime(3);
			backOrder.setType(2);//1：空程，2单程
		}
        model.addAttribute("backOrder", backOrder);
        Car car =new Car();
		car.setCompanyCode(shiroUser.companyCode);
		model.addAttribute("cars", carFacade.find(car));
        return "transport/backOrder/form";
    }
	
	/*
	* recordStatus:
	* 0正常，1删除，
	*/
   @ResponseBody
   @RequestMapping(value = "updateById", method = RequestMethod.POST)
   public Long updateById(BackOrder backOrder, Model model) {
	   try{
		   return (Long) backOrderFacade.update(backOrder);
       } catch (Exception e) {
    	   log.error(e.getMessage());
    	   return -1l;
       }
   }
   
   /*
	* recordStatus:
	* 0正常，1删除，
	*/
  @ResponseBody
  @RequestMapping(value = "effeSeTime", method = RequestMethod.POST)
  public String effeSeTime(BackOrder backOrder, Model model) {
	   try{
		   return DataUtils.formatStr(DataUtils.getByHour(backOrder.getBoardingTime(),backOrder.getEffeTime()),"yyyy-MM-dd hh:mm:ss");//设置可以被搜索的时间界限
      } catch (Exception e) {
   	   log.error(e.getMessage());
   	   return null;
      }
  }
   
   //更新或者新增
   @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
   public String saveOrUpdate(BackOrder backOrder, Model model, RedirectAttributes redirectAttributes){
	   String message = (backOrder.getId() == null?"新建":"更新")+"成功@1";
	   try{
		   if(backOrder.getId() == null){
			   backOrder.setOrderCode(Constant.ROR_CODE+DataUtils.getCurrentTimeStr());
		   }
		   if (!StringUtils.isEmpty(backOrder.getCharterOrderCode())) {
			   Order order = new Order();
			   order.setOrderCode(backOrder.getCharterOrderCode());
			   List<Order> orders = (List<Order>)orderFacade.find(order);
			   backOrder.setEffeSeTime(DataUtils.getByHour(orders.get(0).getBoardingTime(),backOrder.getEffeTime()));//设置可以被搜索的时间界限
			   backOrder.setType(1);//1：空程，2单程
			} else {
				 backOrder.setEffeSeTime(DataUtils.getByDay(new Date(), 1000));//设置可以被搜索的时间界限
				 backOrder.setType(2);//1：空程，2单程
			}
		   //计算预留上车时间和预留下车时间>>>start
		   	backOrder.setReserveUpTime(DataUtils.getByHour(backOrder.getEarliestDepartureTime(),-2));//单前预留时间2小时
		   	Car car = new Car();
    		car.setCarCode(backOrder.getCarCode());
    		List<Car> carList = (List<Car>)carFacade.find(car);
    		car = carList.get(0);
    		double duration = (backOrder.getDuration()!=null?backOrder.getDuration():0)/60.0;
	   		if(car.getCity().equals(backOrder.getEndCity())){
	   			backOrder.setReserveOffTime(DataUtils.getByMinute(backOrder.getLatestDepartureTime(), (int) Math.ceil(duration*1.5)+2*60));
	   		}else {
	   			backOrder.setReserveOffTime(DataUtils.getByMinute(backOrder.getLatestDepartureTime(), (int) Math.ceil(duration*2.5)+2*60));
			}
	   		//计算预留上车时间和预留下车时间>>>end
           ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
           backOrder.setCreateUser(shiroUser.userAccount);
           backOrder.setUpdateUser(shiroUser.userAccount);
           backOrder.setCreateCompany(shiroUser.companyCode);
           backOrderFacade.saveOrUpdate(backOrder);
       } catch (Exception e) {
    	   message = message.replace("成功@1", "失败@2");
    	   log.error(e.getMessage());
       }
	   redirectAttributes.addFlashAttribute("message", message); 
       return "redirect:/transport/backOrder/list/"+backOrder.getType();
   }
    
   @ResponseBody
   @RequestMapping(value = "searchCarsByBack")
   public Map<String, Object>  searchCarsByBack(Order order,Model model, HttpServletRequest request) {
	   	Map<String, Object> map = new HashMap<String, Object>();
	   	try {
	   		ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
	   		order.setReserveUpTime(DataUtils.getByHour(order.getBoardingTime(),-2));//单前预留时间2小时
			double duration = (order.getDuration()!=null?order.getDuration():0)/60.0;
			order.setReserveOffTime(DataUtils.getByMinute(order.getDebusTime(), (int) Math.ceil(duration*2.5)+2*60));
			order.setTransporterCode(shiroUser.companyCode);
			map.put("cars", matchFacade.selectCars(order));//运输方所属全部有效并且空闲的车辆
	   	} catch (Exception e) {
	   		log.error(e.getMessage());
		}
	   	return map;
   }
}