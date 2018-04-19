package com.dhxx.facade.entity.charter.invoice;

import java.io.Serializable;
import java.util.Date;
import com.dhxx.common.page.Page;


/**
 * <p> 类说明 </p>
 * @author dingbin
 * Date: 2017年09月15日
 * @version 1.01
 * 发票表(t_invoice)
 */
public class Invoice extends Page implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1624076778821409849L;
	
	private Long id;//主键
	private Long userId;//用户ID
	private String orderCode;//订单编码
	private Long invoiceType;//发票类型  1电子发票 2纸质增值税普票 3纸质增值税专票
	private Long titleCategory;//抬头类型  1公司抬头 2个人/非企业单位
	private float invoiceAmount;//发票金额
	private String invoiceTitle;//发票抬头
	private String taxpayerNumber;//纳税人识别号
	private String invoiceContent;//发票内容
	private String email;//电子邮件



	private String orderStatus; //订单状态  1：申请中  2：已通过  3：已寄出
	private String courierNumber;//快递单号
	private String courierCompany;//快递公司



	private String address;//地址
	private String remark;//备注
	private Date createTime;//日期
	private String recipient; //收件人
	private String contactWay;//联系方式

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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCourierNumber() {
		return courierNumber;
	}

	public void setCourierNumber(String courierNumber) {
		this.courierNumber = courierNumber;
	}

	public String getCourierCompany() {
		return courierCompany;
	}

	public void setCourierCompany(String courierCompany) {
		this.courierCompany = courierCompany;
	}
	
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
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Long getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(Long invoiceType) {
		this.invoiceType = invoiceType;
	}
	public Long getTitleCategory() {
		return titleCategory;
	}
	public void setTitleCategory(Long titleCategory) {
		this.titleCategory = titleCategory;
	}
	public float getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(float invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getTaxpayerNumber() {
		return taxpayerNumber;
	}
	public void setTaxpayerNumber(String taxpayerNumber) {
		this.taxpayerNumber = taxpayerNumber;
	}
	public String getInvoiceContent() {
		return invoiceContent;
	}
	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}