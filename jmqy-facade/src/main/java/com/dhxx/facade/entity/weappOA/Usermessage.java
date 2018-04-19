package com.dhxx.facade.entity.weappOA;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/8.
 */
public class Usermessage  implements Serializable {
    private static final long serialVersionUID = 1L;

    private  String openid;  // 微信用户的openid
    private  String unionid; //微信用户的unionid
    private  String phone;   //用户的手机



    private  String phonecode;
    private  String codeEncoder;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }

    public String getCodeEncoder() {
        return codeEncoder;
    }

    public void setCodeEncoder(String codeEncoder) {
        this.codeEncoder = codeEncoder;
    }
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
