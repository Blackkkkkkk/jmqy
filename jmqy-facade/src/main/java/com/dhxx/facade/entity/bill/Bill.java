package com.dhxx.facade.entity.bill;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月21日
 * @version 1.01
 * 账单流水表(t_bill)
 */
public class Bill extends SysBaseEntity{

	private Long id;//表id
	private String billType;//账单类型：0包车方订单支付，1运输方订单佣金支付，2运输方订单入账，3包车方充值，4运输方提现,5包车方退款
	private String money;//账单金额
	private String tradeMode;//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联 5申请退款
	private String orderCode;//订单号（非必要，比如提现或者充值）
	private String threeOrderNo;//第三方支付订单号
	private Long userId;//当事人id
	private String companyCode;//企业编码
	
	//附加属性
    private String companyName;//公司名称
    private String userName;//企业主账号名
    private String param;//搜索参数
	private String userAccount;//用户账号


	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getTradeMode() {
		return tradeMode;
	}
	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getThreeOrderNo() {
		return threeOrderNo;
	}
	public void setThreeOrderNo(String threeOrderNo) {
		this.threeOrderNo = threeOrderNo;
	}
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
}
