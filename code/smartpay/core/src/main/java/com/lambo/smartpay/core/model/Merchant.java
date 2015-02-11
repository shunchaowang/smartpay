package com.lambo.smartpay.core.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "merchant")
public class Merchant implements Serializable {
	private static final long serialVersionUID = 4165360397615776418L;

	@Id
	@GeneratedValue(generator = "MerchantSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "MerchantSeq", sequenceName = "merchant_seq", initialValue = 1, allocationSize = 1)
	private Integer id;

	@Column(name = "merchant_name", length = 200)
	private String merchantName;

	@Column(name = "merchant_code", length = 28)
	private String merchantCode;

	@Column(name = "own_type", length = 11)
	private String ownType;

	@Column(name = "status", length = 3)
	private String status;

	@Column(name = "appliant", length = 60)
	private String appliant;

	@Column(name = "apply_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyDate;

	@Column(name = "province_name")
	private String provinceName;

	@Column(name = "city_name")
	private String cityName;

	@Column(name = "address", length = 120)
	private String address;

	@Column(name = "register_name", length = 200)
	private String registerName;

	@Column(name = "scope", length = 200)
	private String scope;

	@Column(name = "re_name", length = 60)
	private String reName;

	@Column(name = "re_identity", length = 18)
	private String reIdentity;

	@Column(name = "identity_type", length = 1)
	private String identityType;

	@Column(name = "post", length = 6)
	private String post;

	@Column(name = "merchant_tel", length = 20)
	private String merchantTel;

	@Column(name = "merchnat_fax", length = 20)
	private String merchantFax;

	@Column(name = "ic_name", length = 200)
	private String icName;

	@Column(name = "org_no", length = 30)
	private String orgNo;

	@Column(name = "licence", length = 20)
	private String licence;

	@Column(name = "tax_no", length = 20)
	private String taxNo;

	@Column(name = "acc_no", length = 21)
	private String accNo;

	@Column(name = "acc_name", length = 90)
	private String accName;

	@Column(name = "open_bank", length = 120)
	private String openBank;

	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "bus_name", length = 60)
	private String busName;

	@Column(name = "bus_phone", length = 20)
	private String busPhone;

	@Column(name = "bus_email", length = 60)
	private String busEmail;

	@Column(name = "bus_qqmsn", length = 60)
	private String busQQMSN;

	@Column(name = "operator_id", length = 11)
	private Integer operatorId;

	@Column(name = "merchant_type", length = 11)
	private String merchantType;

	@Column(name = "manage_year", length = 1)
	private String manageYear;

	@Column(name = "merchant_nature", length = 1)
	private String merchantNature;

	@Column(name = "capital", length = 16)
	private String capital;

	@Column(name = "business_place", length = 1)
	private String businessPlace;

	@ManyToOne
	@JoinColumn(name = "proxy_id")
	private Proxy proxy;

	@Column(name = "unit_code")
	private String unitCode;

	@Column(name = "identity_path")
	private String identityPath;

	@Column(name = "licence_path")
	private String licencePath;

	@Column(name = "place_path")
	private String placePath;

	@Column(name = "effect_date")
	private Date effectDate;

	@Column(name = "mcc_Code", length = 50)
	private String mccCode;

	@Column(name = "single_max_consume", length = 20)
	private String singleMaxConsume;

	@Column(name = "day_max_consume", length = 20)
	private String dayMaxConsume;

	@Column(name = "mid", length = 15)
	private String mid;

	@Column(name = "trade_auth", length = 65)
	private String tradeAuth;
	public static final String NATURE_INDI = "0";
	public static final String NATURE_OTH = "1";
	public static final String NATURE_STA = "2";
	public static final String STATUS_INV = "INV";
	public static final String STATUS_EFF = "EFF";
	public static final String STATUS_LOC = "LOC";
	public static final String STATUS_EXT = "EXT";
	public static final String IDTYPE_MAINLAND = "0";
	public static final String IDTYPE_OTHER = "1";
	public static final String TYPE_MER = "MER";
	public static final String TYPE_PROMER = "PROMER";
	public static final String YEAR_MON = "0";
	public static final String YEAR_BET = "1";
	public static final String YEAR_FIV = "2";
	public static final String PROFIT_N = "0";
	public static final String PROFIT_Y = "1";
	public static final String OWN_CAUSE = "0";
	public static final String OWN_ENTE = "1";
	public static final String OWN_INDI = "2";
	public static final String PLACE_LEASE = "0";
	public static final String PLACE_OWN = "1";

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMerchantName() {
		return this.merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantCode() {
		return this.merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantTel() {
		return this.merchantTel;
	}

	public void setMerchantTel(String merchantTel) {
		this.merchantTel = merchantTel;
	}

	public String getMerchantFax() {
		return this.merchantFax;
	}

	public void setMerchantFax(String merchantFax) {
		this.merchantFax = merchantFax;
	}

	public String getMerchantNature() {
		return this.merchantNature;
	}

	public void setMerchantNature(String merchantNature) {
		this.merchantNature = merchantNature;
	}

	public String getOwnType() {
		return this.ownType;
	}

	public void setOwnType(String ownType) {
		this.ownType = ownType;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegisterName() {
		return this.registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getReName() {
		return this.reName;
	}

	public void setReName(String reName) {
		this.reName = reName;
	}

	public String getReIdentity() {
		return this.reIdentity;
	}

	public void setReIdentity(String reIdentity) {
		this.reIdentity = reIdentity;
	}

	public String getIdentityType() {
		return this.identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
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

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getLicence() {
		return this.licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getTaxNo() {
		return this.taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
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

	public Integer getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getMerchantType() {
		return this.merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getManageYear() {
		return this.manageYear;
	}

	public void setManageYear(String manageYear) {
		this.manageYear = manageYear;
	}

	public String getCapital() {
		return this.capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getBusinessPlace() {
		return this.businessPlace;
	}

	public void setBusinessPlace(String businessPlace) {
		this.businessPlace = businessPlace;
	}

	public Proxy getProxy() {
		return this.proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getIdentityPath() {
		return this.identityPath;
	}

	public void setIdentityPath(String identityPath) {
		this.identityPath = identityPath;
	}

	public String getLicencePath() {
		return this.licencePath;
	}

	public void setLicencePath(String licencePath) {
		this.licencePath = licencePath;
	}

	public String getPlacePath() {
		return this.placePath;
	}

	public void setPlacePath(String placePath) {
		this.placePath = placePath;
	}

	public Date getEffectDate() {
		return this.effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public String getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getMccCode() {
		return this.mccCode;
	}

	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}

	public String getSingleMaxConsume() {
		return this.singleMaxConsume;
	}

	public void setSingleMaxConsume(String singleMaxConsume) {
		this.singleMaxConsume = singleMaxConsume;
	}

	public String getDayMaxConsume() {
		return this.dayMaxConsume;
	}

	public void setDayMaxConsume(String dayMaxConsume) {
		this.dayMaxConsume = dayMaxConsume;
	}

	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTradeAuth() {
		return this.tradeAuth;
	}

	public void setTradeAuth(String tradeAuth) {
		this.tradeAuth = tradeAuth;
	}
}
