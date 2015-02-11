package com.lambo.smartpay.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "bbp_member")
public class Member implements Serializable {
	private static final long serialVersionUID = 4165360397615776418L;
	public static final String STATUS_LOCK = "0";
	public static final String STATUS_ACT = "1";
	public static final String STATUS_DEL = "99";
	public static final String MEMBER_TYPE_UNIT = "U";
	public static final String MEMBER_TYPE_MERCHANT = "M";
	public static final String MEMBER_TYPE_PROXY = "A";
	public static final String MEMBER_TYPE_PROXY_MERCHANT = "AM";
	public static final String MEMBER_TYPE_CUSTOMERMANAGER = "C";

	@Id
	@GeneratedValue(generator = "MemberSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "MemberSeq", sequenceName = "bbp_member_seq", initialValue = 1, allocationSize = 1)
	private Integer id;

	@Column(name = "username", length = 30)
	private String username;

	@Column(name = "email", length = 100, nullable = true)
	private String email;

	@Column(name = "tel", length = 20, nullable = true)
	private String tel;

	@Column(name = "real_name", length = 20)
	private String realName;

	@Column(name = "status", length = 2)
	private String status;

	@ManyToOne
	@JoinColumn(name = "mer_id", nullable = true)
	private Merchant merchant;

	@ManyToOne
	@JoinColumn(name = "proxy_id", nullable = true)
	private Proxy proxy;

	@Column(name = "unit_code")
	private String unitCode;

	@Column(name = "m_type", length = 2)
	private String memberType;

	@Column(name = "department_code", length = 30)
	private String departmentCode;

	@Column(name = "opt_id", nullable = true)
	private Integer operatorId;

	@Column(name = "created_date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@ManyToMany(targetEntity = Role.class)
	@JoinTable(name = "bbp_member_role", joinColumns = { @JoinColumn(name = "member_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<Role>();

	public boolean isUnit() {
		return "U".equals(this.memberType);
	}

	public boolean isProxy() {
		return "A".equals(this.memberType);
	}

	public boolean isMerchant() {
		return "M".equals(this.memberType);
	}

	public boolean isProxyMerchant() {
		return "AM".equals(this.memberType);
	}

	public boolean isCustomerManager() {
		return "C".equals(this.memberType);
	}

	public boolean isActive() {
		return "1".equals(this.status);
	}

	public boolean isLock() {
		return "0".equals(this.status);
	}

	public boolean isDelete() {
		return "99".equals(this.status);
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Merchant getMerchant() {
		return this.merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public String getMemberType() {
		return this.memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public Proxy getProxy() {
		return this.proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public String getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getDepartmentCode() {
		return this.departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
}

