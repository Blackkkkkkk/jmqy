package com.dhxx.facade.entity.charter.complaint;

import java.util.Date;

import com.dhxx.facade.entity.SysBaseEntity;

/**
 * <p> 类说明 </p>
 * @author dingbin
 * Date: 2017年09月15日
 * @version 1.01
 * 投诉表(t_complaint)
 */
public class Complaint extends SysBaseEntity{

	private Long id;//主键
	private Long userId;//用户ID
	private String orderCode;//订单编号
	private String toComplaintContent;//投诉内容
	private String replyContent;//运输方回复内容
	private Long replyUserId;//运输方回复人表id
	private Date replyTime;//运输方回复时间
	private Date createTime;//投诉时间
	private String remark;//备注
	
	//查询属性
	private String beginTime;//开始投诉时间
	private String endTime;//结束投诉时间
	private String transporterCode;//运输方编码

    //附加属性
    private Long orderId;//订单ID
    private String complaintId;//被投诉人ID
    private String complainant;//投诉人
    private String charterCompany;//包车企业
    private String transportCompany;//运输企业
    private String carNum;//车牌号
	private String driverName;//司机名称
	private String jobNum;//司机工号
    private String param;//搜索参数
    private String searchType;//搜索类型，0：订单号，1：包车企业，2：运输企业，3：评价人，4：评价内容



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

    public String getToComplaintContent() {
        return toComplaintContent;
    }

    public void setToComplaintContent(String toComplaintContent) {
        this.toComplaintContent = toComplaintContent;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getComplainant() {
        return complainant;
    }

    public void setComplainant(String complainant) {
        this.complainant = complainant;
    }

    public String getCharterCompany() {
        return charterCompany;
    }

    public void setCharterCompany(String charterCompany) {
        this.charterCompany = charterCompany;
    }

    public String getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(String transportCompany) {
        this.transportCompany = transportCompany;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

	public String getTransporterCode() {
		return transporterCode;
	}

	public void setTransporterCode(String transporterCode) {
		this.transporterCode = transporterCode;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Long getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(Long replyUserId) {
		this.replyUserId = replyUserId;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}