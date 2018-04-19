package com.dhxx.facade.entity.balance;

import java.io.Serializable;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月20日
 * @version 1.01
 * 余额表(t_balance)
 */
public class Balance implements Serializable{
	
	private static final long serialVersionUID = -8213575735598020546L;
	
	private Long userId;//用户ID
	private String companyCode;//企业编码
	private Double amount;//总金额（不断累积：充值、收到付款）
	private Double consumption;//消费金额（不断累积：支付订单、支付佣金、提现）
	private Double balance;//账户余额
	
	//附加属性
    private String companyName;//公司名称
    private String userAccount;//企业账号
    private String param;//搜索参数
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getConsumption() {
		return consumption;
	}
	public void setConsumption(Double consumption) {
		this.consumption = consumption;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
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
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
}
