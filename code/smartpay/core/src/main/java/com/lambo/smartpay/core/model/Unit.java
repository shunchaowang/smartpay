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
@Table(name = "unit")
public class Unit {

	@Id
	@GeneratedValue(generator = "UnitSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "UnitSeq", sequenceName = "unit_seq", initialValue = 1, allocationSize = 1)
	private Integer id;

	@Column(name = "unit_name")
	private String unitName;

	@Column(name = "unit_code")
	private String unitCode;

	@Column(name = "address", length = 120)
	private String address;

	@Column(name = "province_name")
	private String provinceName;

	@Column(name = "city_name")
	private String cityName;

	@Column(name = "county_name")
	private String countyName;

	@Column(name = "scope", length = 200)
	private String scope;

	@Column(name = "post", length = 6)
	private String post;

	@Column(name = "unit_tel", length = 20)
	private String unitTel;

	@Column(name = "unit_fax", length = 20)
	private String unitFax;

	@Column(name = "ic_name", length = 200)
	private String icName;

	@Column(name = "unit_type", length = 3)
	private String unitType;

	@Column(name = "unit_model")
	private String unitModel;

	@Column(name = "acc_no", length = 21)
	private String accNo;

	@Column(name = "acc_name", length = 90)
	private String accName;

	@Column(name = "open_bank", length = 120)
	private String openBank;

	@Column(name = "bus_name", length = 60)
	private String busName;

	@Column(name = "bus_phone", length = 20)
	private String busPhone;

	@Column(name = "bus_email", length = 60)
	private String busEmail;

	@Column(name = "bus_qqmsn", length = 60)
	private String busQQMSN;

	@Column(name = "parent_code")
	private String parentCode;

	@Column(name = "operator_id")
	private Integer operatorId;

	@Column(name = "appliant", length = 60)
	private String appliant;

	@Column(name = "apply_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyDate;

	@Column(name = "unit_level")
	private Integer unitLevel;

	@Column(name = "effect_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectDate;

	@Column(name = "status")
	private String status;
	public static final String TYPE_DEP = "DEP";
	public static final String TYPE_PLA = "PLA";
	public static final String TYPE_PRO = "PRO";
	public static final String STATUS_INV = "INV";
	public static final String STATUS_EFF = "EFF";
	public static final String STATUS_OPEN = "OPN";
	public static final String STATUS_CLOSE = "CLS";

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitTel() {
		return this.unitTel;
	}

	public void setUnitTel(String unitTel) {
		this.unitTel = unitTel;
	}

	public String getUnitFax() {
		return this.unitFax;
	}

	public void setUnitFax(String unitFax) {
		this.unitFax = unitFax;
	}

	public String getUnitType() {
		return this.unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getUnitModel() {
		return this.unitModel;
	}

	public void setUnitModel(String unitModel) {
		this.unitModel = unitModel;
	}

	public Integer getUnitLevel() {
		return this.unitLevel;
	}

	public void setUnitLevel(Integer unitLevel) {
		this.unitLevel = unitLevel;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return this.countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getIcName() {
		return this.icName;
	}

	public void setIcName(String icName) {
		this.icName = icName;
	}

	public String getAccNo() {
		return this.accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccName() {
		return this.accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getOpenBank() {
		return this.openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getBusName() {
		return this.busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getBusPhone() {
		return this.busPhone;
	}

	public void setBusPhone(String busPhone) {
		this.busPhone = busPhone;
	}

	public String getBusEmail() {
		return this.busEmail;
	}

	public void setBusEmail(String busEmail) {
		this.busEmail = busEmail;
	}

	public String getBusQQMSN() {
		return this.busQQMSN;
	}

	public void setBusQQMSN(String busQQMSN) {
		this.busQQMSN = busQQMSN;
	}

	public String getAppliant() {
		return this.appliant;
	}

	public void setAppliant(String appliant) {
		this.appliant = appliant;
	}

	public Integer getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
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
