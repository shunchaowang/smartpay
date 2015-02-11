package com.lambo.smartpay.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 明细查询
 *
 * @author swang
 *
 */
@Entity
@Table(name = "MER_ACC_DETAIL")
public class MerchantAccountDetail implements Serializable {
	private static final long serialVersionUID = -1644952180455317647L;
	@Id
	@Column(name = "MAD_NO")
	private String merchantNo; // 流水号 日期8位+12位流水
	@Column(name = "MAD_ACCNO")
	private String accountNo; // 账号 2位类别码(个人:PT 企业CT)+账号类别( 00 普通账户 01信用卡账户 02预付卡账户
							// 03内部手续费账户 )+16位序列号
	@Column(name = "MAD_VERSION")
	private Long version; // 版本号
	@Column(name = "MAD_CREATETIME")
	private String createdTime; // 创建时间
	@Column(name = "MAD_ACC_STATUS")
	private String status; // 账户状态(0:正常，1：冻结)
	@Column(name = "MAD_BALANCE", precision = 2)
	private Double balance; // 余额
	@Column(name = "MAD_AVABALANCE", precision = 2)
	private Double availableBalance; // 可用余额
	@Column(name = "MAD_TRANTYPE")
	private String transactionType; // 交易类型(00:充值10:账户支付11:卡支付30:退款到账户31:线上退款到卡32:线下退款到卡60:提现70结算80手续费81分润90调账)
	@Column(name = "MAD_CRYTYPE")
	private String currencyType; // 币种
	@Column(name = "MAD_TRXAMOUT", precision = 2)
	private Double transactionAmout; // 发生额
	@Column(name = "MAD_PAYFLOWNO")
	private String payFlowNo; // 支付流水号
	@Transient
	private String customerNo; // 商户编号
	@Transient
	private String beginDate; // 开始时间
	@Transient
	private String endDate; // 结束时间

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
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


	public String getPayFlowNo() {
		return payFlowNo;
	}

	public void setPayFlowNo(String payFlowNo) {
		this.payFlowNo = payFlowNo;
	}

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Double getTransactionAmout() {
        return transactionAmout;
    }

    public void setTransactionAmout(Double transactionAmout) {
        this.transactionAmout = transactionAmout;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}
