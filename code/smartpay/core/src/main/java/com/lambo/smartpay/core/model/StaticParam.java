package com.lambo.smartpay.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//参数表
@Entity
@Table(name = "pub_static_param")
public class StaticParam {

	@Id
	private Integer id;

	@Column(name = "param_id", nullable = false, length = 56)
	private String paramId; // 参数Id 标示参数 (例:merchant_status_eff)

	@Column(name = "param_name", nullable = false, length = 32)
	private String paramName; // 参数名称 (例:商户状态)

	@Column(name = "item_code", nullable = false, length = 32)
	private String itemCode; // 参数值代码 (例:EFF)

	@Column(name = "item_value", nullable = false, length = 32)
	private String itemValue; // 参数值 (例:已生效)

	@Column(name = "item_desc", length = 64)
	private String itemDesc; // 参数描述

	@Column(name = "parent_param_id", length = 56)
	private String parentParamId; // 父级参数id ,对应参数id

	@Column(name = "taglib", length = 32)
	private String taglib; // 所属系统名或表名

	// GetSet
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getTaglib() {
		return taglib;
	}

	public void setTaglib(String taglib) {
		this.taglib = taglib;
	}

	public String getParentParamId() {
		return parentParamId;
	}

	public void setParentParamId(String parentParamId) {
		this.parentParamId = parentParamId;
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

}
