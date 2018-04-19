package com.dhxx.facade.entity.remind;

import com.dhxx.facade.entity.SysBaseEntity;

import java.util.Date;

/**
 * @author jhy
 * @date 2017/9/17
 * @description
 * 订单提醒(t_remind)
 */
public class Remind extends SysBaseEntity {

	private Long id;//主键
    private Long userId;//用户ID
    private Long orderId;//订单ID
    private Date remindTime;//提醒时间
    private Long type;//提醒类型：0：支付提醒 1：执行提醒  2：删除提醒  3：积分过期  4：投诉提醒  5：付款提醒（信用额度）6:运输方接收订单后，给包车方的提醒,7:包车企业认证通过，8：包车企业认证不通过
    //提醒类型：8:运输方拒绝订单后，给包车方的提醒 9:下单支付后提醒  10:司机已阅查看
    private Integer status;//状态：0：未读 1：已读

    //订单管理-订单提醒用
    private String startPoint;//起始点
    private String endPoint;//目的地
    private String orderCode;//订单编码
    private Date placeTime;//下单时间
    private String companyCode; //公司编码

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getPlaceTime() {
		return placeTime;
	}

	public void setPlaceTime(Date placeTime) {
		this.placeTime = placeTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
