package com.lambo.smartpay.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 行政区代码bean
 * 
 * @author Daniel.G
 *
 */
@Entity
@Table(name = "pub_region")
public class Region {

	@Id
	@GeneratedValue
	private String code; // 行政区代码
	@Column(name = "region")
	private String region; // 行政区
	@Column(name = "parent_code")
	private String parentCode; // 父级编码

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
}
