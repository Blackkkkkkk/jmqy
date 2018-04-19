package com.dhxx.facade.entity.rule;

import com.dhxx.facade.entity.SysBaseEntity;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author jhy
 * @date 2017/9/25
 * @description
 * 平台系数设置
 */
public class Rule extends SysBaseEntity{

    private Long id;//表id
    //16空接单单价
    private Long ruleType;//类型   1起点  2 里程单价公里数计费  9 优惠率   10终点   11退款设置  12途经里程计费比率 13包车天数系数 14局部设置包车天数系数  15 途经里程额单价 16 空接单单价公里数计费 17 退款手续费大小限额  18 退款设置里面的时间系数
    private String ruleValue;//名称
    private String defaultRange;//	默认区间
    private String toRange;//区间结束，999999表示以上
    private Double coefficient;//系数
    private Long parentId;//父级id
    private String companyCode;//企业编码
    private String endPlance;//目的地


    //辅助查询字段
    private String ruleTypeList; //用于查询多个ruleType
    private String companyName;//企业名称
    private Long type;//企业类型：0平台方 1个人包主 2包车企业 3运输企业

    private Long timeDifference; //退款时间差

    public Long getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(Long timeDifference) {
        this.timeDifference = timeDifference;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getEndPlance() {
        return endPlance;
    }

    public void setEndPlance(String endPlance) {
        this.endPlance = endPlance;
    }

    public List<String> getList() {
        if(StringUtils.isEmpty(ruleTypeList))
            return null;
        return Arrays.asList(ruleTypeList.split(","));
    }


    public String getRuleTypeList() {
        return ruleTypeList;
    }

    public void setRuleTypeList(String ruleTypeList) {
        this.ruleTypeList = ruleTypeList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRuleType() {
        return ruleType;
    }

    public void setRuleType(Long ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public String getDefaultRange() {
        return defaultRange;
    }

    public void setDefaultRange(String defaultRange) {
        this.defaultRange = defaultRange;
    }

    public String getToRange() {
        return toRange;
    }

    public void setToRange(String toRange) {
        this.toRange = toRange;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}
