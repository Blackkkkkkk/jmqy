package com.dhxx.facade.entity;

import com.dhxx.common.page.Page;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * Created by hanrs on 2017/9/12
 */
public class SysBaseEntity extends Page implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1470105925637006675L;
   
    private String createUser;//创建人账号
    private String updateUser;//更新人账号
    private Date createDate;//创建时间
    private Date updateDate;//更新时间
    private String createCompany;//创建企业编码
    private Integer recordStatus;//记录状态 0:正常 1:无效

    private String ids;//分页多选中的记录数据表id
    private String order;//排序：asc,desc
    private String sortType;//分页多选中的记录数据表id
    
    public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateCompany() {
		return createCompany;
	}

	public void setCreateCompany(String createCompany) {
		this.createCompany = createCompany;
	}

	public Integer getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}


	public String getSortType() {
		return sortType;
	}


	public void setSortType(String sortType) {
		this.sortType = sortType;
	}


	public List<String> getIdList() {
    	if(StringUtils.isEmpty(ids))
    		return null;
		return Arrays.asList(ids.split(","));
	}




}
