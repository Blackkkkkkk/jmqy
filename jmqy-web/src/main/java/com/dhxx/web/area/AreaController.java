package com.dhxx.web.area;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.area.Area;
import com.dhxx.facade.entity.city.City;
import com.dhxx.facade.entity.province.Province;
import com.dhxx.facade.service.area.AreaFacade;
import com.dhxx.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jhy
 * @date 2017/9/26
 * @description
 * 地区
 */
@Controller
@RequestMapping("area")
public class AreaController extends BaseController {

    @Reference(protocol="dubbo")
    private AreaFacade areaFacade;

    //省
    @ResponseBody
    @RequestMapping(value="province")
    public Map<String, Object> province(Province province) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            List<Province> provinceList = (List<Province>) areaFacade.list(province);
            map.put("provinceList", provinceList);
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

    //市
    @ResponseBody
    @RequestMapping(value="city")
    public Map<String, Object> city(City city) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            List<City> cityList = (List<City>) areaFacade.cityList(city);
            map.put("cityList", cityList);
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

    //区
    @ResponseBody
    @RequestMapping(value="area")
    public Map<String, Object> area(Area area) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            List<Area> areaList = (List<Area>) areaFacade.areaList(area);
            map.put("areaList", areaList);
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

    //全部区
    @ResponseBody
    @RequestMapping(value="Allarea")
    public Map<String, Object> Allarea(Area area) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            List<Area> areaList = (List<Area>) areaFacade.areaList(area);
            map.put("areaList", areaList);
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

}
