package com.lambo.smartpay.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 结算查询
 * 
 * @author swang
 *
 */
@Entity
@Table(name = "MER_SET_RECORD")
public class SetRecord implements Serializable {
	private static final long serialVersionUID = 6216155294459865378L;
	@Id
	@Column(name = "MSR_BATCHNO")
	private String barchNo; // 批次号
	@Column(name = "MSR_VERSION")
	private Long version; // 版本号
	@Column(name = "MSR_BEGINSETDATE")
	private String beginSetDate; // 结算起始时间
	@Column(name = "MSR_ENDSETDATE")
	private String endSetDate; // 结算结束时间
	@Column(name = "MSR_REFUNDCOUNT")
	private String refundCount; // 退款总数
	@Column(name = "MSR_SUMREFUNDAMOUNT", precision = 2)
	private Double sumrefundAmount; // 退款金额
	@Column(name = "MSR_TRXCOUNT")
	private String trxCount; // 交易笔数
	@Column(name = "MSR_SUMTRXAMOUNT", precision = 2)
	private Double sumTrxAmount; // 交易金额
	@Column(name = "MSR_SUMTRXFEEACOUNT")
	private String sumTrxfeeAmount; // 手续费笔数
	@Column(name = "MSR_SUMTRXFEEAMOUNT", precision = 2)
	private Double sumTrxFeeAmount; // 手续费金额
	@Column(name = "MSR_SUMREALAMOUNT", precision = 2)
	private Double sumRealAmount; // 实际打款金额
	@Column(name = "MSR_SETTLEFEE", precision = 2)
	private Double setTlefee; // 结算手续费
	@Column(name = "MSR_STATUS")
	private String mstatus; // 状态(0.未审核，1已审核，2审核拒绝3：已生成打款文件4：打款成功，5：打款失败,6:初始状态)
	@Column(name = "MSR_SETTLEMENTDATE")
	private String setTlementDate; // 结算日期
	@Column(name = "MSR_SET_INF_NO")
	private String setInfNo; // 结算规则编号
	@Column(name = "MSR_SETTLEMENTTYPE")
	private String setTlementType; // 结算类型（0：手动结算；1：自动结算）
	@Column(name = "MSR_CUSTOMER_ID")
	private String customerId; // 客户号
	@Column(name = "MSR_USER_ID")
	private String userId; // 用户号
	@Column(name = "MSR_SETACC")
	private String setAcc; // 结算账户
	@Column(name = "MSR_TYPE")
	private String mtype; // 交易类型（60：提现；70：结算）
	@Column(name = "MSR_CFM_USER")
	private String cfmUser; // 确认人
	@Column(name = "MSR_CFM_DATE")
	private String cfmDate; // 确认日期
	@Column(name = "MSR_PAY_USER")
	private String payUser; // 打款确认人
	@Column(name = "MSR_PAY_CFM_DATE")
	private String payCfmDate; // 打款确认日期
	@Column(name = "MSR_BANK")
	private String bank; // 打款银行
	@Column(name = "MSR_ACC")
	private String acc; // 打款账号
	@Column(name = "MSR_CYCLETYPE")
	private String cycleType; // 结算周期类型(1:每日;2:每周;3:每月)
	@Column(name = "MSR_CYCLEDATA")
	private String cycleDate; // 结算周期
	@Transient
	private String beginDate; // 开始时间
	@Transient
	private String endDate; // 结束时间
	@Transient
	private Long minRealAmout; // 最小交易额
	@Transient
	private Long maxRealAmout; // 最大交易额

	public String getBarchNo() {
		return barchNo;
	}

	public void setBarchNo(String barchNo) {
		this.barchNo = barchNo;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getBeginSetDate() {
		return beginSetDate;
	}

	public void setBeginSetDate(String beginSetDate) {
		this.beginSetDate = beginSetDate;
	}

	public String getEndSetDate() {
		return endSetDate;
	}

	public void setEndSetDate(String endSetDate) {
		this.endSetDate = endSetDate;
	}

	public String getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(String refundCount) {
		this.refundCount = refundCount;
	}

	public String getTrxCount() {
		return trxCount;
	}

	public void setTrxCount(String trxCount) {
		this.trxCount = trxCount;
	}

	public String getSumTrxfeeAmount() {
		return sumTrxfeeAmount;
	}

	public void setSumTrxfeeAmount(String sumTrxfeeAmount) {
		this.sumTrxfeeAmount = sumTrxfeeAmount;
	}

	public Double getSumrefundAmount() {
		return sumrefundAmount;
	}

	public void setSumrefundAmount(Double sumrefundAmount) {
		this.sumrefundAmount = sumrefundAmount;
	}

	public Double getSumTrxAmount() {
		return sumTrxAmount;
	}

	public void setSumTrxAmount(Double sumTrxAmount) {
		this.sumTrxAmount = sumTrxAmount;
	}

	public Double getSumTrxFeeAmount() {
		return sumTrxFeeAmount;
	}

	public void setSumTrxFeeAmount(Double sumTrxFeeAmount) {
		this.sumTrxFeeAmount = sumTrxFeeAmount;
	}

	public Double getSumRealAmount() {
		return sumRealAmount;
	}

	public void setSumRealAmount(Double sumRealAmount) {
		this.sumRealAmount = sumRealAmount;
	}

	public Double getSetTlefee() {
		return setTlefee;
	}

	public void setSetTlefee(Double setTlefee) {
		this.setTlefee = setTlefee;
	}

	public String getMstatus() {
		return mstatus;
	}

	public void setMstatus(String mstatus) {
		this.mstatus = mstatus;
	}

	public String getSetTlementDate() {
		return setTlementDate;
	}

	public void setSetTlementDate(String setTlementDate) {
		this.setTlementDate = setTlementDate;
	}

	public String getSetInfNo() {
		return setInfNo;
	}

	public void setSetInfNo(String setInfNo) {
		this.setInfNo = setInfNo;
	}

	public String getSetTlementType() {
		return setTlementType;
	}

	public void setSetTlementType(String setTlementType) {
		this.setTlementType = setTlementType;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSetAcc() {
		return setAcc;
	}

	public void setSetAcc(String setAcc) {
		this.setAcc = setAcc;
	}

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getCfmUser() {
		return cfmUser;
	}

	public void setCfmUser(String cfmUser) {
		this.cfmUser = cfmUser;
	}

	public String getCfmDate() {
		return cfmDate;
	}

	public void setCfmDate(String cfmDate) {
		this.cfmDate = cfmDate;
	}

	public String getPayUser() {
		return payUser;
	}

	public void setPayUser(String payUser) {
		this.payUser = payUser;
	}

	public String getPayCfmDate() {
		return payCfmDate;
	}

	public void setPayCfmDate(String payCfmDate) {
		this.payCfmDate = payCfmDate;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public String getCycleType() {
		return cycleType;
	}

	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}

	public String getCycleDate() {
		return cycleDate;
	}

	public void setCycleDate(String cycleDate) {
		this.cycleDate = cycleDate;
	}

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

	public Long getMinRealAmout() {
		return minRealAmout;
	}

	public void setMinRealAmout(Long minRealAmout) {
		this.minRealAmout = minRealAmout;
	}

	public Long getMaxRealAmout() {
		return maxRealAmout;
	}

	public void setMaxRealAmout(Long maxRealAmout) {
		this.maxRealAmout = maxRealAmout;
	}
}
