package com.dhxx.facade.entity.weappOA;

import java.io.Serializable;

/**
 * Created by xiaoqiang on 2017/11/19.
 */
public class test implements Serializable {
    private static final long serialVersionUID = 1L;
    private String unionid;
    private String openid;
    private String phone;

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
