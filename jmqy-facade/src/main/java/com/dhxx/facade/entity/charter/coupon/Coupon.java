package com.dhxx.facade.entity.charter.coupon;

import java.io.Serializable;
import java.util.Date;

import com.dhxx.common.page.Page;

/**
 * <p> 类说明 </p>
 * @author dingbin
 * Date: 2017年09月11日
 * @version 1.01
 * 优惠卷表(t_coupon)
 */
public class Coupon extends Page implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6034642939317144944L;
	
	private Long id;//表id 主键
	private String code;//优惠券码
	private String name;//卡卷名称（红包名称）
	private Long money;//卡卷金额
	private Long discount;//卡卷折扣（折扣卷折扣）
	private Long full;//达标金额（满多少金额使用)
	private Long cap;//优惠封顶金额
	private String city;//使用城市或者区域
	private Long type;//卡卷类型（1：抵扣卷，2：折扣卷，3：分期卷，4：推荐卷，5：新人注册卷，6：乘客的随机红包，7：支付红包，8：积分卷）
	private String pic;//优惠价图片路径
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private String detail;//优惠券内容
	private Long totalFee;//活动发放总金额
	private Long payType;//优惠可使用的支付类型（1：微信，2：支付宝，3：齐商易付，4：一网通）其他表示不限制
	private Long status;//状态: 0,冻结中，1启用中
	private Date createTime;//创建时间
	private String remark;//备注
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getMoney() {
		return money;
	}
	public void setMoney(Long money) {
		this.money = money;
	}
	public Long getDiscount() {
		return discount;
	}
	public void setDiscount(Long discount) {
		this.discount = discount;
	}
	public Long getFull() {
		return full;
	}
	public void setFull(Long full) {
		this.full = full;
	}
	public Long getCap() {
		return cap;
	}
	public void setCap(Long cap) {
		this.cap = cap;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Long getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}
	public Long getPayType() {
		return payType;
	}
	public void setPayType(Long payType) {
		this.payType = payType;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}