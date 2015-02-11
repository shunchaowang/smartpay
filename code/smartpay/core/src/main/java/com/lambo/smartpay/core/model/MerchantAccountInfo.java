package com.lambo.smartpay.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商户账户信息
 * 
 * @author swang
 *
 */
@Entity
@Table(name = "MER_ACC_INF")
public class MerchantAccountInfo implements Serializable {
	private static final long serialVersionUID = -6066587405293764681L;
	@Id
	@Column(name = "MAF_ACCNO")
	private String accountNo; // 账号
	@Column(name = "MAF_CSTNO")
	private String customerNo; // 客户编号
	@Column(name = "MAF_VERSION")
	private Long version; // 版本号
	@Column(name = "MAF_CREATETIME")
	private String createdTime; // 创建时间
	@Column(name = "MAF_STATUS")
	private String status; // 账户状态(0:正常，1：冻结)
	@Column(name = "MAF_BALANCE", precision = 2)
	private Double balance; // 余额
	@Column(name = "MAF_AVABALANCE", precision = 2)
	private Double availableBalance; // 可用余额
	@Column(name = "MAF_ACCTYPE")
	private String accountType; // 账户类型
	@Column(name = "MAF_CRYTYPE")
	private String currencyType; // 币种

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
}
