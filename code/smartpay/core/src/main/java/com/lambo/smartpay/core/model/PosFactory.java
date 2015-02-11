package com.lambo.smartpay.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "pos_factory")
public class PosFactory {

	@Id
	@GeneratedValue(generator = "PosFactorySeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "PosFactorySeq", sequenceName = "pos_factory_seq", initialValue = 1, allocationSize = 1)
	private Integer id;

	@Column(name = "code", length = 30)
	private String code; // 厂商编号

	@Column(name = "name", length = 100)
	private String name; // 厂商全称

	@Column(name = "alias", length = 60)
	private String alias; // 简称

	@Column(name = "c_proxy_code", length = 30)
	private String createdProxyCode; // 创建者的代理商编号

	@Column(name = "c_by")
	private Integer createdBy; // 创建人id

	@Column(name = "c_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate; // 创建日期

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCreatedProxyCode() {
		return createdProxyCode;
	}

	public void setCreatedProxyCode(String createdProxyCode) {
		this.createdProxyCode = createdProxyCode;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
