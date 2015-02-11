package com.lambo.smartpay.core.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "unit_contract")
public class UnitContract {

	@Id
	@GeneratedValue(generator = "PlatformContractSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "PlatformContractSeq", sequenceName = "platform_contract_seq", initialValue = 1, allocationSize = 1)
	private Integer id;

	@Column(name = "contract_code")
	private String contractCode;

	@Column(name = "contract_name")
	private String contractName;

	@Column(name = "unit_code")
	private String unitCode;

	@Column(name = "low_sign_rate")
	private Integer lowSignRate;

	@Column(name = "share_fee_rate")
	private Integer shareFeeRate;

	@Column(name = "share_ratio")
	private Integer shareRatio;

	@Column(name = "operator_id")
	private Integer operatorId;

	@Column(name = "appliant")
	private String appliant;

	@Column(name = "apply_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date applyDate;

	@Column(name = "effect_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date effectDate;

	@Column(name = "invalid_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date invalidDate;

	@Column(name = "status")
	private String status;
	public static final String STATUS_INV = "INV";
	public static final String STATUS_EFF = "EFF";
	public static final String STATUS_EXT = "EXT";

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContractCode() {
		return this.contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Integer getLowSignRate() {
		return this.lowSignRate;
	}

	public void setLowSignRate(Integer lowSignRate) {
		this.lowSignRate = lowSignRate;
	}

	public Integer getShareFeeRate() {
		return this.shareFeeRate;
	}

	public void setShareFeeRate(Integer shareFeeRate) {
		this.shareFeeRate = shareFeeRate;
	}

	public Integer getShareRatio() {
		return this.shareRatio;
	}

	public void setShareRatio(Integer shareRatio) {
		this.shareRatio = shareRatio;
	}

	public Integer getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
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

	public Date getInvalidDate() {
		return this.invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
