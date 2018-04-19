package com.dhxx.facade.service.area;

import com.dhxx.facade.entity.area.Area;
import com.dhxx.facade.entity.city.City;
import com.dhxx.facade.entity.province.Province;

/**
 * @author jhy
 * @date 2017/9/26
 * @description
 */
public interface AreaFacade {

    Object list(Province province);

    Object cityList(City city);

    Object areaList(Area area);

}
