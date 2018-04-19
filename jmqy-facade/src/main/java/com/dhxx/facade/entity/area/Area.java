package com.dhxx.facade.entity.area;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * @author jhy
 * @date 2017/9/26
 * @description
 * 地区
 */
public class Area extends SysBaseEntity {

    private Long areaId;
    private String areaName;
    private Long parentId;

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
