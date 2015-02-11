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
@Table(name = "bbp_freeze")
public class Freeze {

	@Id
	@GeneratedValue(generator = "FreezeSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "FreezeSeq", sequenceName = "bbp_freeze_seq", initialValue = 1, allocationSize = 1)
	private Integer id;

	@Column(name = "freeze_code")
	private String freezeCode;

	@Column(name = "freeze_type")
	private String freezeType;

	@Column(name = "operator_date")
	private Date operatorDate;

	@Column(name = "operator_id")
	private Integer operatorId;

	@Column(name = "operator_type")
	private String operatorType;

	@Column(name = "reason")
	private String reason;

	@Column(name = "status")
	private String status;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFreezeCode() {
		return this.freezeCode;
	}

	public void setFreezeCode(String freezeCode) {
		this.freezeCode = freezeCode;
	}

	public String getFreezeType() {
		return this.freezeType;
	}

	public void setFreezeType(String freezeType) {
		this.freezeType = freezeType;
	}

	public Date getOperatorDate() {
		return this.operatorDate;
	}

	public void setOperatorDate(Date operatorDate) {
		this.operatorDate = operatorDate;
	}

	public Integer getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorType() {
		return this.operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
