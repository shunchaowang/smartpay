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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "bbp_role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String TYPE_UNIT = "U";
	public static final String TYPE_PROXY = "A";
	public static final String TYPE_MERCHANT = "M";

	@Id
	@GeneratedValue(generator = "RoleSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "RoleSeq", sequenceName = "bbp_role_seq", initialValue = 1, allocationSize = 1)
	private Integer id;

	@Column(name = "name", length = 30)
	private String name;

	@Column(name = "role_type", length = 2)
	private String type;

	@Column(name = "is_tmp")
	@Type(type = "yes_no")
	private Boolean isTemplate;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "from_unit")
	private String fromUnit;

	@ManyToMany(mappedBy = "roles", targetEntity = Member.class)
	private Set<Member> members = new HashSet<Member>();

	@ManyToMany(targetEntity = Menu.class)
	@JoinTable(name = "bbp_role_menu", joinColumns = { @javax.persistence.JoinColumn(name = "role_id") }, inverseJoinColumns = { @javax.persistence.JoinColumn(name = "menu_id") })
	private Set<Menu> menus = new HashSet<Menu>();

	@ManyToMany(targetEntity = Permission.class)
	@JoinTable(name = "bbp_role_permission", joinColumns = { @javax.persistence.JoinColumn(name = "role_id") }, inverseJoinColumns = { @javax.persistence.JoinColumn(name = "permission_id") })
	private Set<Permission> permissions = new HashSet<Permission>();

	public boolean isUnit() {
		return "U".equals(this.type);
	}

	public boolean isProxy() {
		return "A".equals(this.type);
	}

	public boolean isMerchant() {
		return "M".equals(this.type);
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Set<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public Set<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsTemplate() {
		return this.isTemplate;
	}

	public void setIsTemplate(Boolean isTemplate) {
		this.isTemplate = isTemplate;
	}

	public String getFromUnit() {
		return this.fromUnit;
	}

	public void setFromUnit(String fromUnit) {
		this.fromUnit = fromUnit;
	}

	public Set<Member> getMembers() {
		return this.members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}
}
