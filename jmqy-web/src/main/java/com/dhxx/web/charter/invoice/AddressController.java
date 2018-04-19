package com.dhxx.web.charter.invoice;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.charter.invoice.Address;
import com.dhxx.facade.service.charter.invoice.AddressFacade;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("charter/invoice/Address")
public class AddressController {

    @Reference(protocol="dubbo")
    private AddressFacade addressFacade;



    //添加收货地址
    @RequestMapping(value = "addAddress")
    public String addAddress(Address address, Model model,@RequestParam(value = "actionType") String actionType,
                             HttpServletRequest request) {

        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();


        List<Address> addressList= (List<Address>) addressFacade.findAddress(address);
        Map<String, Object> map = new HashMap<String, Object>();

        if(actionType.equals("1")){
        }else{
            address = addressList.get(0);
            model.addAttribute("addressmap",address);
        }
        model.addAttribute("actionType",actionType);

        return "charter/order/addAddress";
    }





    //保存收货地址
    @RequestMapping(value = "saveAddress")
    public String saveAddress(Address address,@RequestParam(value = "actionType") String actionType,
                              HttpServletRequest request) {

        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        address.setUserId(shiroUser.id);//用户编号
        if(actionType.equals("1")) {


            address.setStatus("0");
            address.setCreateTime(new Date());
            addressFacade.addAddress(address);
        }else if(actionType.equals("2")){
            addressFacade.updateAddress(address);
        }else if (actionType.equals("3")){
            addressFacade.deletAddress(address);
        }else if(actionType.equals("4")){
            //先把状态都归0
            Address ad = new Address();
            ad.setUserId(shiroUser.id);//用户编号
            ad.setStatus("0");
            addressFacade.updateAddress(ad);
            //设置为默认地址
            address.setStatus("1");
            addressFacade.updateAddress(address);
        }
        //	ad.save(address);

        return "charter/order/invoice_list";
    }


    //删除和设置默认收货地址
    @ResponseBody
    @RequestMapping(value = "ActionAddress")
    public Map<String, Object> ActionAddress(Address address,@RequestParam(value = "actionType") String actionType,
                                             HttpServletRequest request) {
        Map<String,Object> map = new HashMap<String,Object>();
        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        if(actionType.equals("3")) {
            addressFacade.deletAddress(address);
            map.put("state","success");
        }else{
            //先把状态都归0
            Address ad = new Address();
            ad.setUserId(shiroUser.id);//用户编号
            ad.setStatus("0");
            addressFacade.updateAddress(ad);
            //设置为默认地址
            address.setStatus("1");
            addressFacade.updateAddress(address);
            map.put("state","success");
        }

        return map;
    }
}
