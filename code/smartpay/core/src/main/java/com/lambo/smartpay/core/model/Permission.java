package com.lambo.smartpay.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bbp_permission")
public class Permission {
	public static final String SEPARATOR = "::";

	@Id
	private Integer id;

	@Column(name = "code", length = 50, unique = true)
	private String code;

	@Column(name = "name", length = 50)
	private String name;

	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menu;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
