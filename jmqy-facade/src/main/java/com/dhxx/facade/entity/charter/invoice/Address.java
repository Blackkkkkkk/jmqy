package com.dhxx.facade.entity.charter.invoice;

import java.io.Serializable;
import java.util.Date;

public class Address  implements Serializable {
    private Long id;//主键
    private Long userId;//用户ID
    private String address;//地址
    private Date createTime;//日期
    private String recipient; //收件人
    private String contactWay;//联系方式
    private String status;//是否为默认地址：1 默认地址  0 非默认地址



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
