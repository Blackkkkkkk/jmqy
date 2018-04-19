package com.dhxx.web.search;


import java.beans.PropertyEditorSupport;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.dhxx.common.sms.Mo;
import com.dhxx.common.util.DataUtils;
import com.dhxx.common.wechat.WechatUtils;
import com.dhxx.facade.entity.additional.AdditionalNum;
import com.dhxx.facade.entity.area.Area2;
import com.dhxx.facade.entity.match.Cars;
import com.dhxx.facade.entity.match.Match;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.order.OrderTimeSet;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.entity.weappOA.LocationPostBase;
import com.dhxx.facade.entity.weappOA.Usermessage;
import com.dhxx.facade.service.additional.AdditionalNumFacade;
import com.dhxx.facade.service.line.LineFacade;
import com.dhxx.facade.service.match.MatchFacade;
import com.dhxx.facade.entity.line.Line;
import com.dhxx.facade.service.order.OrderTimeSetFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import org.springframework.beans.BeanUtils;


import com.dhxx.facade.service.weChatOA.WechatOAFacade;
import org.jboss.resteasy.spi.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import  java.util.Map;


/**
 * Created by Administrator on 2017/11/7.
 */

//@SessionAttributes(value={"order","test"},types={Order.class})
@SessionAttributes(value={"order","test"},types={Order.class,String.class})
@Controller
@RequestMapping("search")
public class SearchController {
    private static Logger log = LoggerFactory.getLogger(SearchController.class);


    @Reference(protocol="dubbo")
    private WechatOAFacade wechatOAFacade;

    @Reference(protocol = "dubbo")
    private UserFacade userFacade;

    @Reference(protocol="dubbo")
    private LineFacade lineFacade;

    @Reference(protocol="dubbo")
    private AdditionalNumFacade additionalNumFacade;

    @Reference(protocol = "dubbo")
    private OrderTimeSetFacade orderTimeSetFacade;
    //String 转Date
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

        //  binder.registerCustomEditor(Long.class, new LongEditor());
    }


    @Reference(protocol="dubbo")
    private MatchFacade matchFacade;

    @RequestMapping(value = "wechatapp")
    public String wechatapp(Line line, Area2 area, OrderTimeSet orderTimeSet,Model model, HttpServletRequest request, HttpSession session, @RequestParam(value = "code",required = false) String code) {

        session.removeAttribute("order");
        session.removeAttribute("locationPostBase");
        Map<String,Object> map = new HashMap<String,Object>();
        map = orderTimeSetFacade.find(orderTimeSet);

        Usermessage usermessage =(Usermessage)session.getAttribute("usermessage");


        //设置用户名
        if (usermessage==null||usermessage.getPhone()==null){
            usermessage=new Usermessage();
            if (code!=null&&code!="") {
                usermessage=setName(code,usermessage);
                session.setAttribute("usermessage",usermessage);
            }
            else{
                usermessage = (Usermessage) session.getAttribute("usermessage");
            }
        }

        if(usermessage!= null) {
        if(usermessage.getPhone() != null && usermessage.getPhone() != "") {

            System.out.println("进来了="+usermessage.getPhone());
            setLineValue(usermessage.getPhone(),model);
        }
        }


        model.addAttribute("area", area);
        model.addAttribute("map",map);

        return "search/search";
    }
    //跳转百度地图
    @RequestMapping(value = "BaiDuMap")
    public ModelAndView BaiDuMap(@RequestParam(value = "state" ,required = false) String state,
                                 @RequestParam(value = "str" ,required = false)String str ){

        System.out.println(str);

        ModelAndView mav = new ModelAndView();
        mav.addObject("str",str);
        mav.setViewName("search/BaiDuMap");
        if (state!=null &&state!="") {
            mav.addObject("state", state);
        }
        return  mav;
    }
    //选择后设置值
    @RequestMapping(value ="LocationSetValue" ,method = RequestMethod.GET)
    public  String LocationSetValue(Model model,LocationPostBase op,OrderTimeSet orderTimeSet, HttpSession session, @RequestParam (value = "state",required = false)String state){

        Usermessage usermessage =(Usermessage)session.getAttribute("usermessage");
        LocationPostBase locationPostBase = (LocationPostBase) session.getAttribute("locationPostBase");


        if(session.getAttribute("locationPostBase")!=null){

            locationPostBase =(LocationPostBase) session.getAttribute("locationPostBase");
        }else {
            locationPostBase = new LocationPostBase();
        }

        if(state!=null&&state!="") {
            if (state.equals("start")) {

                locationPostBase.setStartProvince(op.getStartProvince());
                locationPostBase.setStartCity(op.getStartCity());
                locationPostBase.setStartLat(op.getStartLat());
                locationPostBase.setStartLng(op.getStartLng());
                locationPostBase.setStartDistrict(op.getStartDistrict());
                locationPostBase.setStartStreet(op.getStartStreet());
                locationPostBase.setStartStreetNumber(op.getStartStreetNumber());
                locationPostBase.setStartPoint(op.getStartPoint());

            } else if (state.equals("end")) {

                locationPostBase.setEndProvince(op.getEndProvince());
                locationPostBase.setEndCity(op.getEndCity());
                locationPostBase.setEndLat(op.getEndLat());
                locationPostBase.setEndLng(op.getEndLng());
                locationPostBase.setEndDistrict(op.getEndDistrict());
                locationPostBase.setEndStreet(op.getEndStreet());
                locationPostBase.setEndStreetNumber(op.getEndStreetNumber());
                locationPostBase.setEndPoint(op.getEndPoint());
            }

        }

        if(op.getViaPoint() != null && op.getViaPoint()!=""){
            locationPostBase.setViaPoint(op.getViaPoint());
        }

        if(usermessage!= null) {
            if (usermessage.getPhone() != null && usermessage.getPhone() != "") {
                setLineValue(usermessage.getPhone(),model);
            }
        }


        session.setAttribute("locationPostBase",locationPostBase);


        Map<String,Object> map = new HashMap<String,Object>();

        map = orderTimeSetFacade.find(orderTimeSet);
        model.addAttribute("map",map);

        return "/search/searchreturn";
    }


    public  void setLineValue (String phone ,Model model){

        User us = new User();
        us.setUserAccount("wx"+phone);
        System.out.println("shou="+us.getUserAccount());
        //   us.setUserAccount("wx13148913794");
        List list = (List) userFacade.find(us);
        us = (User) list.get(0);

        System.out.println("id="+us.getId());
        Line line = new Line();
        try {
            //保存或者更新搜索路线
            if (us.getId() != null) {
                line.setType(1);//类型：1收藏，2搜索
                line.setCreateUser(us.getId() + "");
                line.setTop(4);
                List<Line> colLines = (List<Line>) lineFacade.find(line);
                model.addAttribute("colLines", colLines);

                line.setType(2);//类型：1收藏，2搜索
                List<Line> hisLines = (List<Line>) lineFacade.find(line);
                model.addAttribute("hisLines", hisLines);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }


    //查询后的页面
    @RequestMapping(value = "search2")
    public String search1(Area2 area, Model model, HttpServletRequest request ,Order order,HttpSession session) {



        model.addAttribute("area", area);

        model.addAttribute("test","this is test");
        //    System.out.println("order="+order.getCarType());
        return "search/search1";

    }

  //搜索车辆
    @ResponseBody
    @RequestMapping(value = "wechatsearCarTypes" )
    public ModelAndView wechatsearCars(Order order, Model model, AdditionalNum additionalNum, HttpServletRequest request, HttpSession session) {

        session.removeAttribute("order");
        session.removeAttribute("bigOrderCode");

        double Amount = 0.0;  //选中附加选项价格总价

        Usermessage usermessage =(Usermessage) session.getAttribute("usermessage");
        ModelAndView modelAndView = new ModelAndView();
    //    session.removeAttribute("bigOrderCode");
     //   order.setBigOrderCode("");
        Map<String, Object> map = new HashMap<String, Object>();
   //  trackBoardTime
        boolean state = true;

        try {
            //计算预留上车时间和预留下车时间>>>start
            order.setReserveUpTime(DataUtils.getByHour(order.getBoardingTime(),-2));//单前预留时间2小时
            double duration = (order.getDuration()!=null?order.getDuration():0)/60.0;
            if(!StringUtils.isEmpty(order.getEndPoint())){
                //TripType == 1:单程从上车时间算,==2：往返的从返回的上车时间
                if(order.getTripType()==1){
                    order.setReserveOffTime(DataUtils.getByMinute(order.getBoardingTime(), (int) Math.ceil(duration*2.5)+2*60));
                }else{
                    order.setReserveOffTime(DataUtils.getByMinute(order.getTrackBoardTime(), (int) Math.ceil(duration*1.25)+2*60));
                }
                order.setFlag(1);//按公里数和时长计费
            }
            if(order.getCharterDays() != null){
                order.setReserveOffTime(DataUtils.format(DataUtils.getByDay(order.getBoardingTime(), order.getCharterDays().intValue()-1),"yyyy-MM-dd 23:59:59"));
                order.setFlag(2);//按车/天计费
            }


            //计算预留上车时间和预留下车时间>>>end

            List<Match> matchs = (List<Match>) matchFacade.selectByCarTypes(order);
           // model.addAttribute("matchs", matchs);
            session.setAttribute("matchs",matchs);

            // System.out.println("1111="+session.getAttribute("test11"));
            //保存或者更新搜索路线
           if (!StringUtils.isEmpty(order.getStartPoint()) && !StringUtils.isEmpty(order.getEndPoint())) {
               System.out.println("11111");
                if(usermessage!=null&&usermessage.getPhone()!=null){
                    Line line = new Line();
                    BeanUtils.copyProperties(order, line);
                    User us  = new User();
                    us.setUserAccount("wx"+usermessage.getPhone());
                    List list=(List) userFacade.find(us);
                    us=(User) list.get(0);

                    line.setId(null);
                    line.setType(2);//类型：1收藏，2搜索
                    line.setCreateCompany(us.getCompanyId()+"");
                    line.setCreateUser(us.getId()+"");
                    lineFacade.saveOrUpdate(line);
                }

           }

            String[] additionalArray = order.getAdditional().split(" "); //附加选项数量
            if((order.getAdditional()).length()>0) {  //判断是否有附加选项
                String additionalNumLength = (order.getAdditionalNumFacade()).substring(0, (((order.getAdditionalNumFacade()).length()) - 1));
                String[] additionalNumFacadeArray = additionalNumLength.split(","); //附加选项数量


                Map<String, Object> additionalNumMap = new HashMap<String, Object>(); //获取保存的价格设定
                additionalNumMap = additionalNumFacade.find(additionalNum);


                for (int i = 0; i < additionalArray.length; i++) {

                    if (additionalArray[i].equals("餐费")) {
                        Amount += Double.parseDouble(additionalNumFacadeArray[i]) * Double.parseDouble(additionalNumMap.get("meals") + "");
                    } else if (additionalArray[i].equals("住宿费")) {
                        Amount += Double.parseDouble(additionalNumFacadeArray[i]) * Double.parseDouble(additionalNumMap.get("accommodation") + "");
                    } else if (additionalArray[i].equals("高速路费")) {
                        Amount += Double.parseDouble(additionalNumFacadeArray[i]) * Double.parseDouble(additionalNumMap.get("highway") + "");
                    } else if (additionalArray[i].equals("保险")) {
                        Amount += Double.parseDouble(additionalNumFacadeArray[i]) * Double.parseDouble(additionalNumMap.get("insurance") + "");
                    } else if (additionalArray[i].equals("水")) {
                        Amount += Double.parseDouble(additionalNumFacadeArray[i]) * Double.parseDouble(additionalNumMap.get("water") + "");
                    }

                }
            }


        } catch (Exception e) {
            state = false;
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        // model.addAttribute("state", state);

        map.put("state","true");
      //  map.put("Amount", Amount);
        order.setAmount(Amount);
        model.addAttribute("order",order);
        modelAndView.setViewName("/search/search1");

        return modelAndView;
    }


//搜索推荐路线
    @ResponseBody
    @RequestMapping(value = "searLines")
    public Map<String, Object>  searLines(Model model, HttpServletRequest request,HttpSession session) {
        Order order =(Order) session.getAttribute("order");
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            List<Cars> carsList = (List<Cars>) matchFacade.selectLines(order);
            map.put("carsList", carsList);
            session.setAttribute("carsList",carsList);
        } catch (Exception e) {
            state = false;
            log.debug(e.getMessage());
            e.printStackTrace();
        }

        map.put("state", state);
        return map;
    }


    //字符串转换时间
    public  Date getdate(String darestring){
        Date date = new Date();
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(darestring);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
        return date;
    }


    //将字符串首字母转大写
    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
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

        if(usermessage.getUnionid()!=null&&usermessage.getUnionid()!=""){
            unionid=usermessage.getUnionid();
        }

        System.out.println("方法里面的unionid="+unionid);


        Map<String, Object> map1 = wechatOAFacade.findUserMessage(unionid);
        if(map1==null || map1.size()<1)
        {
            if(unionid!=null&&unionid!=""){
                usermessage.setUnionid(unionid);
            }
            if(openid!=null&&openid!=""){
                usermessage.setOpenid(openid);
            }
        }else {
            System.out.println("map1=" + map1);
            usermessage.setPhone(map1.get("WECHAT_PHONE").toString());
            usermessage.setUnionid(map1.get("WECHAT_UNIONID").toString());
            usermessage.setOpenid(map1.get("WECHAT_OPENID").toString());
        }

        System.out.println("方法Uniusermessage="+usermessage.getUnionid());
        System.out.println("方法Openusermessage="+usermessage.getOpenid());
        return  usermessage;

    }
}
