package com.dhxx.service.service.area;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.area.Area;
import com.dhxx.facade.entity.city.City;
import com.dhxx.facade.entity.province.Province;
import com.dhxx.facade.service.area.AreaFacade;
import com.dhxx.service.biz.area.AreaBiz;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jhy
 * @date 2017/9/26
 * @description
 */
@Service(protocol = {"dubbo"})
public class AreaFacadeImpl implements AreaFacade {

    @Autowired
    private AreaBiz areaBiz;

    @Override
    public Object list(Province province) {
        return areaBiz.list(province);
    }

    @Override
    public Object cityList(City city) {
        return areaBiz.cityList(city);
    }

    @Override
    public Object areaList(Area area) {
        return areaBiz.areaList(area);
    }

}
