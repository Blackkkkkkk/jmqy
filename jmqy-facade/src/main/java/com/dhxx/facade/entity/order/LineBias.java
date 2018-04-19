package com.dhxx.facade.entity.order;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2018年01月22日
 * @version 1.01
 * 线路偏差表(t_line_bias)
 */
public class LineBias extends SysBaseEntity {

	private Long id;//主键
	private String orderCode;//订单编码
	private String auditContent;//审核内容
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getAuditContent() {
		return auditContent;
	}
	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}
	

}