package com.dhxx.facade.entity.score;

import com.dhxx.facade.entity.SysBaseEntity;

import java.util.Date;

/**
 * @author jhy
 * @date 2017/9/17
 * @description
 * 积分 （t_score）
 */
public class Score extends SysBaseEntity {

    private Long userId;//用户ID
    private Long orderId;//订单ID
    private Double totalScore;//总积分
    private Double consumeScore;//消费积分
    private Date expireDate;//有效期

    //附加属性
    private String companyName;//公司名称
    private String userAccount;//企业账号
    private Double expireScore;//即将过期积分
    private String param;//搜索参数
    private String companyCode;//企业编码；

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Double getConsumeScore() {
        return consumeScore;
    }

    public void setConsumeScore(Double consumeScore) {
        this.consumeScore = consumeScore;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Double getExpireScore() {
        return expireScore;
    }

    public void setExpireScore(Double expireScore) {
        this.expireScore = expireScore;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

}
