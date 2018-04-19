package com.dhxx.service.biz.area;

import com.dhxx.facade.entity.area.Area;
import com.dhxx.facade.entity.city.City;
import com.dhxx.facade.entity.province.Province;
import com.dhxx.service.mapper.area.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/26
 * @description
 */
@Service
@Transactional
public class AreaBiz {

    @Autowired
    private AreaMapper areaMapper;

    public List<Province> list(Province province) {
        return areaMapper.list(province);
    }

    public List<City> cityList(City city) {
        return areaMapper.cityList(city);
    }

    public List<Area> areaList(Area area) {
        return areaMapper.areaList(area);
    }
}
