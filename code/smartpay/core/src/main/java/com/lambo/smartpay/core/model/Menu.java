package com.lambo.smartpay.core.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "bbp_menu")
public class Menu implements Serializable {
	private static final long serialVersionUID = -3628032997880730788L;
	public static final String TYPE_ROOT = "0";
	public static final String TYPE_MIDDLE = "1";
	public static final String TYPE_END = "2";

	@Id
	private Integer id;

	@Column(name = "title", length = 30)
	private String title;

	@Column(name = "parent_id")
	private Integer parentId;

	@Column(name = "sort")
	private Integer sort = 0;

	@Column(name = "type", length = 1)
	private String type;

	@Column(name = "path", length = 100)
	private String path;

	@Column(name = "icon", length = 20)
	private String icon;

	@Transient
	private String permsCode;

	@Transient
	private String permsName;

	@ManyToMany(mappedBy = "menus", targetEntity = Role.class)
	private Set<Role> roles = new HashSet<Role>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPermsCode() {
		return this.permsCode;
	}

	public void setPermsCode(String permsCode) {
		this.permsCode = permsCode;
	}

	public String getPermsName() {
		return this.permsName;
	}

	public void setPermsName(String permsName) {
		this.permsName = permsName;
	}

	public String toString() {
		return String.format("{id:%s, title:%s, sort:%s}", new Object[] { this.id, this.title, this.sort });
	}
}
