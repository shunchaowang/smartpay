package com.lambo.smartpay.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "PUB_APPPAR")
public class PubApppar implements Serializable {
	private static final long serialVersionUID = 4165360397615776418L;
	@Id
	@Column(name = "APR_NAME")
	private String aprName;// 参数名称
	@Id
	@Column(name = "APR_CODE")
	private String aprCode; // 参数代码
	@Id
	@Column(name = "APR_VALUE")
	private String aprValue; // 参数实际值
	@Id
	@Column(name = "APR_LANGUAGE")
	private String aprLanguage; // 参数语种
	@Column(name = "APR_SHOWMSG")
	private String aprShowMsg;// 参数显示信息
	@Column(name = "APR_CLASS")
	private String aprClass; // 参数类别，暂不使用
	@Column(name = "APR_MFLAG")
	private String aprMflag; // 是否适用于移动端，0-不适用，1-适用

	public String getAprName() {
		return aprName;
	}

	public void setAprName(String aprName) {
		this.aprName = aprName;
	}

	public String getAprCode() {
		return aprCode;
	}

	public void setAprCode(String aprCode) {
		this.aprCode = aprCode;
	}

	public String getAprValue() {
		return aprValue;
	}

	public void setAprValue(String aprValue) {
		this.aprValue = aprValue;
	}

	public String getAprLanguage() {
		return aprLanguage;
	}

	public void setAprLanguage(String aprLanguage) {
		this.aprLanguage = aprLanguage;
	}

	public String getAprShowMsg() {
		return aprShowMsg;
	}

	public void setAprShowMsg(String aprShowMsg) {
		this.aprShowMsg = aprShowMsg;
	}

	public String getAprClass() {
		return aprClass;
	}

	public void setAprClass(String aprClass) {
		this.aprClass = aprClass;
	}

	public String getAprMflag() {
		return aprMflag;
	}

	public void setAprMflag(String aprMflag) {
		this.aprMflag = aprMflag;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
