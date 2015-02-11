package com.lambo.smartpay.core.model;

import java.io.Serializable;
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

//代理商
@Entity
@Table(name = "proxy")
public class Proxy implements Serializable {

	private static final long serialVersionUID = 4165360397615776418L;

	@Id
	@GeneratedValue(generator = "ProxySeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ProxySeq", sequenceName = "proxy_seq", initialValue = 1, allocationSize = 1)
	private Integer id; // id
	@Column(name = "proxy_code", length = 28)
	private String proxyCode; // 代理商编码
	@Column(name = "proxy_name", length = 200)
	private String proxyName; // 代理商姓名
	@Column(name = "proxy_alias", length = 100)
	private String proxyAlias; // 代理商别名
	@Column(name = "status")
	private String status; // 代理商状态 INV锁定/EFF生效/EXT失效
	@Column(name = "operator_id")
	private Integer operatorId; // 操作员ID
	@Column(name = "appliant", length = 60)
	private String appliant; // 申请人
	@Column(name = "apply_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyDate; // 申请日期
	@Column(name = "address", length = 120)
	private String address; // 代理商地址
	@Column(name = "province_name")
	private String provinceName; // 省
	@Column(name = "city_name")
	private String cityName; // 市
	@Column(name = "post", length = 6)
	private String post; // 邮编
	@Column(name = "proxy_tel", length = 20)
	private String proxyTel; // 电话
	@Column(name = "proxy_fax", length = 20)
	private String proxyFax; // 传真
	@Column(name = "ic_name", length = 200)
	private String icName; // 工商注册名
	@Column(name = "org_no", length = 30)
	private String orgNo; // 组织机构代码
	@Column(name = "licence", length = 20)
	private String licence; // 营业执照号
	@Column(name = "tax_no", length = 20)
	private String taxNo; // 税务等号
	@Column(name = "capital", length = 60)
	private String capital; // 注册资金
	@Column(name = "scope", length = 100)
	private String scope; // 经营范围
	@Column(name = "acc_no", length = 21)
	private String accNo; // 开户账号
	@Column(name = "acc_name", length = 90)
	private String accName; // 开户名
	@Column(name = "open_bank", length = 120)
	private String openBank; // 开户行
	@Column(name = "bus_name", length = 60)
	private String busName; // 业务联系人
	@Column(name = "bus_phone", length = 20)
	private String busPhone; // 业务联系电话
	@Column(name = "bus_qqmsn", length = 60)
	private String busQQMSN; // 业务联系QQ
	@Column(name = "bus_email", length = 60)
	private String busEmail; // 业务EMAIL
	@Column(name = "profit_flag", length = 1)
	private String profitFlag; // 分润标识 0不分润/1分润
	@Column(name = "proxy_level")
	private Integer proxyLevel; // 代理商等级
	@Column(name = "parent_id", nullable = true)
	private Integer parentId; // 父级代理商ID 空位 一级代理商
	@Column(name = "customer_id")
	private Integer customerId; // 所属客户经理
	@Column(name = "unit_code")
	private String unitCode; // 所属平台
	@Column(name = "identity_path")
	private String identityPath; // 身份证上传路径
	@Column(name = "licence_path")
	private String licencePath; // 营业执照上传路径
	@Column(name = "place_path")
	private String placePath; // 营业场所上传路径
	@Column(name = "effect_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectDate; // 审核生效日期

	public static final String STATUS_INV = "INV"; // 锁定
	public static final String STATUS_EFF = "EFF"; // 启用
	public static final String STATUS_EXT = "EXT"; // 删除
	public static final String PROFIT_Y = "1"; // 分润
	public static final String PROFIT_N = "0"; // 不分润

	public static final String DEFAULT_PROXY_CODE = "P001"; // 运营平台编号

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProxyCode() {
		return proxyCode;
	}

	public void setProxyCode(String proxyCode) {
		this.proxyCode = proxyCode;
	}

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public String getProxyAlias() {
		return proxyAlias;
	}

	public void setProxyAlias(String proxyAlias) {
		this.proxyAlias = proxyAlias;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getAppliant() {
		return appliant;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public void setAppliant(String appliant) {
		this.appliant = appliant;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getProxyTel() {
		return proxyTel;
	}

	public void setProxyTel(String proxyTel) {
		this.proxyTel = proxyTel;
	}

	public String getProxyFax() {
		return proxyFax;
	}

	public void setProxyFax(String proxyFax) {
		this.proxyFax = proxyFax;
	}

	public String getIcName() {
		return icName;
	}

	public void setIcName(String icName) {
		this.icName = icName;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getBusPhone() {
		return busPhone;
	}

	public void setBusPhone(String busPhone) {
		this.busPhone = busPhone;
	}

	public String getBusQQMSN() {
		return busQQMSN;
	}

	public void setBusQQMSN(String busQQMSN) {
		this.busQQMSN = busQQMSN;
	}

	public String getBusEmail() {
		return busEmail;
	}

	public void setBusEmail(String busEmail) {
		this.busEmail = busEmail;
	}

	public String getProfitFlag() {
		return profitFlag;
	}

	public void setProfitFlag(String profitFlag) {
		this.profitFlag = profitFlag;
	}

	public Integer getProxyLevel() {
		return proxyLevel;
	}

	public void setProxyLevel(Integer proxyLevel) {
		this.proxyLevel = proxyLevel;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getIdentityPath() {
		return identityPath;
	}

	public void setIdentityPath(String identityPath) {
		this.identityPath = identityPath;
	}

	public String getLicencePath() {
		return licencePath;
	}

	public void setLicencePath(String licencePath) {
		this.licencePath = licencePath;
	}

	public String getPlacePath() {
		return placePath;
	}

	public void setPlacePath(String placePath) {
		this.placePath = placePath;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
}
