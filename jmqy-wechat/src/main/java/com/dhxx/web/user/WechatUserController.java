package com.dhxx.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.dhxx.common.wechat.WechatUtils;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.entity.weappOA.Usermessage;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.facade.service.weChatOA.WechatOAFacade;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@Controller
@RequestMapping("user")
public class WechatUserController {

    @Reference(protocol="dubbo")
    private WechatOAFacade wechatOAFacade;

    @Reference(protocol="dubbo")
    private OrderFacade orderFacade;

    @Reference(protocol="dubbo")
    private UserFacade userFacade;


    //个人主页
    @RequestMapping(value = "")
    public  String  user(){return "user/user";}

    //订单历史
    @RequestMapping(value = "history")
    public ModelAndView history(HttpSession session,@RequestParam(value = "code",required = false) String code){


        Usermessage usermessage =(Usermessage)session.getAttribute("usermessage");

        if (usermessage==null||usermessage.getPhone()==null){

            usermessage=new Usermessage();


            if (code!=null&&code!="") {
                usermessage=setName(code,usermessage);
                session.setAttribute("usermessage",usermessage);
            }

        }else {
            usermessage =(Usermessage) session.getAttribute("usermessage");
        }
        ModelAndView mav = new ModelAndView("/user/history");
        //获得对象数据后，绑定mav并返回
        return mav;
    }

    @RequestMapping(value = "orderdetails")
    public ModelAndView orderdetails(Order order){

        List<Order> orders=(List<Order>)orderFacade.find(order);

        order = (Order)orders.get(0);

        ModelAndView mav = new ModelAndView();
        mav.addObject("order",order);
        mav.setViewName("user/orderdetails");
        return  mav;
    }


    //订单列表
    @RequestMapping(value = "list1/{ids}")
    public String list(Order order, Model model, HttpServletRequest request, HttpSession session,@RequestParam(value = "code",required = false) String code){

        Usermessage usermessage =(Usermessage)session.getAttribute("usermessage");

        if (usermessage==null||usermessage.getPhone()==null){

            usermessage=new Usermessage();

            if (code!=null&&code!="") {
                usermessage=setName(code,usermessage);
                session.setAttribute("usermessage",usermessage);
            }
        }else {
            usermessage =(Usermessage) session.getAttribute("usermessage");
        }


        System.out.println("usermessage="+usermessage.getPhone());
        model.addAttribute("ids",order.getIds());

        //判断是否有用户
        if (usermessage.getPhone()==null){
            System.out.println("1");
            order.setCharterCode("无");
            order.setOrderPlacer("无");
        }else{
            User us  = new User();
            us.setUserAccount("wx"+usermessage.getPhone());
            List list=(List) userFacade.find(us);
            us=(User) list.get(0);

            order.setOrderPlacer(us.getId()+"");
            order.setCharterCode(us.getCompanyCode());
            System.out.println("查询getCompanyCode="+us.getCompanyCode());
            System.out.println("查询ID="+us.getId());
        }
        if("3".equals(order.getIds())){
             order.setIds("3");
            //  order.setIds("-4,3");//取消和完成的订单
        }else if("2".equals(order.getIds())){
            order.setIds("1,2");//在途订单
        }else {
        }
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
        session.setAttribute("order",order);
        session.setAttribute("orders",orders);

        model.addAttribute("order",order);
        model.addAttribute("orders",orders);

        System.out.println();
        return "user/history";
    }

    //设置用户名
    public   Usermessage  setName(String code,Usermessage usermessage){

        WechatUtils wechatUtils = new WechatUtils();
        //获取openid 和access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WechatUtils.getWeixinAppid() + "&secret=" + WechatUtils.getWeixinAppsecret() + "&code=" + code + "&grant_type=authorization_code ";
        String re = wechatUtils.httpRequest(url, "GET", null);
        JSONObject jsStr = JSONObject.parseObject(re);
        String access_token = jsStr.getString("access_token");
        String openid = jsStr.getString("openid");

        // 获取unionid
        url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN  ";
        re = wechatUtils.httpRequest(url, "GET", null);
        System.out.println("jsStr=" + jsStr);
        jsStr = JSONObject.parseObject(re);
        String unionid = jsStr.getString("unionid");

        //  String unionid = "oIOsdwaway2jgY9AHOTr8iXC1_nQ";
        //根据unid反向获取

        Map<String, Object> map1 = wechatOAFacade.findUserMessage(unionid);
        if(map1==null || map1.size()<1)
        { }else {
            usermessage.setPhone(map1.get("WECHAT_PHONE").toString());
            usermessage.setUnionid(map1.get("WECHAT_UNIONID").toString());
            usermessage.setOpenid(map1.get("WECHAT_OPENID").toString());
        }
        return  usermessage;

    }




}

