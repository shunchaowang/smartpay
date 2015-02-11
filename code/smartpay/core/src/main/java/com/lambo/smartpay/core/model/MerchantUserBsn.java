package com.lambo.smartpay.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商户用户开通交易表
 * 
 * @author swang
 *
 */
@Entity
@Table(name = "MER_USER_BSN")
public class MerchantUserBsn implements Serializable {
	private static final long serialVersionUID = -2078384797557039734L;
	@Id
	@Column(name = "MUN_USERID")
	private String userId; // 用户标识：企业客户号+用户编号
	@Id
	@Column(name = "MUN_BSNCODE")
	private String bsnCode; // 交易代码

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBsnCode() {
		return bsnCode;
	}

	public void setBsnCode(String bsnCode) {
		this.bsnCode = bsnCode;
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
