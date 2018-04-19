package com.dhxx.service.app.service.api.gprs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.dhxx.facade.app.gprs.GprsRestFacade;
import com.dhxx.facade.entity.gprs.GprsCar;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.user.UserInfo;
import com.dhxx.facade.exception.ResException;
import com.dhxx.facade.service.gprs.GprsFacade;
import com.dhxx.facade.util.Resp;
import com.dhxx.service.app.authorization.annotation.Authorization;
import com.dhxx.service.app.service.api.order.OrderApi;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p> 类说明 </p>
 *
 * @author xiewq
 * Date: 2018年03月09日
 * @version 1.01
 * app登陆接口
 */
@Path("gprs")
@Service(protocol = "rest")
public class GprsApi implements GprsRestFacade {

    private static Logger log = LoggerFactory.getLogger(GprsApi.class);

    @Reference(protocol = "dubbo")
    private GprsFacade gprsFacade;

    @POST
    @Path("save")
    @Produces("application/json; charset=UTF-8")
    @Authorization
    public Object save(GprsCar gprsCar, @Context HttpServletRequest requst) {

        log.debug("app订单接口>>>>>>>>>>>>保存grps详情,参数=" + JSONObject.toJSONString(gprsCar));


        try {

            List<Map<String, Object>> carList = gprsFacade.find(gprsCar);

            if (carList.size() > 0) {

                Map<String, Object> map = carList.get(0);

                String longLat = map.get("longLat") + "";
                if(gprsCar.getLongLat()!=null && gprsCar.getLongLat()!="") {
                    longLat += gprsCar.getLongLat();
                }
                gprsCar.setLongLat(longLat);

                gprsFacade.update(gprsCar);

            }else {
                gprsFacade.save(gprsCar);
            }
        } catch (Exception e) {
            log.debug(e.toString());
            throw ResException.SEND_SAVEGPRS_ERROR;
        }
        return Resp.SUCCESS();
    }


    @POST
    @Path("getGprs")
    @Produces("application/json; charset=UTF-8")
    @Authorization

    public Object getGprs(GprsCar gprsCar, @Context HttpServletRequest requst) {

        log.debug("app订单接口>>>>>>>>>>>>获取grps详情,参数=" + JSONObject.toJSONString(gprsCar));

        List<Map<String, Object>> carList = new ArrayList<Map<String,Object>>();
        try {
            carList = gprsFacade.find(gprsCar);
        } catch (Exception e) {
            log.debug(e.toString());
            throw ResException.SEND_SAVEGPRS_ERROR;
        }
        return Resp.SUCCESS(carList);
    }
}
