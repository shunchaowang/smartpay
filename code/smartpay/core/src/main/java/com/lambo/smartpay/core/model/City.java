package com.lambo.smartpay.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pub_city")
public class City {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "city_index")
	private Integer cityIndex;

	@Column(name = "province_id")
	private Integer provinceId;

	@Column(name = "name", length = 50)
	private String name;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCityIndex() {
		return this.cityIndex;
	}

	public void setCityIndex(Integer cityIndex) {
		this.cityIndex = cityIndex;
	}

	public Integer getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
