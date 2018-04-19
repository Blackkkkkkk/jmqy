package com.dhxx.facade.entity.province;

import com.dhxx.facade.entity.SysBaseEntity;
import com.dhxx.facade.entity.city.City;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/26
 * @description
 * 省份
 */
public class Province extends SysBaseEntity {

    private Long id;
    private String name;
    private List<City> cityList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
