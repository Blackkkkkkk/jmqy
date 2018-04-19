package com.dhxx.facade.entity.city;

import com.dhxx.facade.entity.SysBaseEntity;
import com.dhxx.facade.entity.area.Area;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/26
 * @description
 * 城市
 */
public class City extends SysBaseEntity {

    private Long cityId;
    private String cityName;
    private Long parentId;
    private List<Area> areaList;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
