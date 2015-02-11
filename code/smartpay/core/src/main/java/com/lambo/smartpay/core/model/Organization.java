package com.lambo.smartpay.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "bbp_organization")
public class Organization {
	public static final String TYPE_DEPARTMENT = "DEP";
	public static final String TYPE_PLATFORM = "PLA";
	public static final String TYPE_PROXY = "PRO";

	@Id
	@GeneratedValue(generator = "OrganizationSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "OrganizationSeq", sequenceName = "bbp_organization_seq", initialValue = 1, allocationSize = 1)
	private Integer id;

	@Column(name = "code", length = 50)
	private String code;

	@Column(name = "lft")
	private Integer left;

	@Column(name = "rgt")
	private Integer right;

	@Column(name = "tp", length = 3)
	private String type;

	@Column(name = "temp", length = 3)
	private String temp = "";

	public String toString() {
		return String.format("{code:%s, left:%s, right:%s}", new Object[] { this.code, this.left, this.right });
	}

	public Organization() {
	}

	public Organization(String code, String type) {
		this.code = code;
		this.type = type;
	}

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

	public Integer getLeft() {
		return this.left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getRight() {
		return this.right;
	}

	public void setRight(Integer right) {
		this.right = right;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemp() {
		return this.temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
}
