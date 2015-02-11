package com.lambo.smartpay.core.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "unit_operation")
public class UnitOperation {

	@Id
	@GeneratedValue(generator = "PlatformOperationSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "PlatformOperationSeq", sequenceName = "platform_operation_seq", initialValue = 1, allocationSize = 1)
	private Integer id;

	@Column(name = "order_code")
	private String orderCode;

	@Column(name = "order_name")
	private String orderName;

	@Column(name = "unit_code")
	private String unitCode;

	@Column(name = "business_type")
	private String businessType;

	@Column(name = "appliant")
	private String appliant;

	@Column(name = "apply_date")
	private Date applyDate;

	@Column(name = "effect_date")
	private Date effectDate;

	@Column(name = "status")
	private String status;
	public static final String STATUS_INV = "INV";
	public static final String STATUS_EFF = "EFF";
	public static final String TYPE_OPEN = "OPN";
	public static final String TYPE_CLOSE = "CLS";

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderCode() {
		return this.orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderName() {
		return this.orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getAppliant() {
		return this.appliant;
	}

	public void setAppliant(String appliant) {
		this.appliant = appliant;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getEffectDate() {
		return this.effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
