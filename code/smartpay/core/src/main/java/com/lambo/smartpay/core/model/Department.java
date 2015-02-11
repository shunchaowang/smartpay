package com.lambo.smartpay.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 部门管理bean
 * 
 * @author Daniel.G
 */
@Entity
@Table(name = "bbp_department")
public class Department {

	@Id
	@GeneratedValue(generator = "DepartmentSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "DepartmentSeq", sequenceName = "department_seq", initialValue = 1, allocationSize = 1)
	private Integer id;
	@Column(name = "department_code")
	private String departmentCode; // 部门编号
	@Column(name = "department_name")
	private String departmentName; // 部门名称
	@Column(name = "unit_code")
	private String unitCode; // 分公司编号

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
}
