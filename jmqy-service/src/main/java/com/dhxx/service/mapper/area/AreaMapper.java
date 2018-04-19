package com.dhxx.service.mapper.area;

import com.dhxx.facade.entity.area.Area;
import com.dhxx.facade.entity.city.City;
import com.dhxx.facade.entity.province.Province;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/26
 * @description
 */
public interface AreaMapper {

    List<Province> list(Province province);

    List<City> cityList(City city);

    List<Area> areaList(Area area);
}
