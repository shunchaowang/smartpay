package com.lambo.smartpay.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 退款信息
 * 
 * @author swang
 *
 */
@Entity
@Table(name = "PAY_REFUND_INF")
public class PayRefundInf implements Serializable {
	private static final long serialVersionUID = 8324494120334253019L;
	@Id
	@Column(name = "RIF_TRANFLOW")
	private String tranFlow; // 流水号,返回给商城的平台流水号
	@Column(name = "RIF_OLDORDERNO")
	private String oldOrderNo; // 原订单号(退款时才使用)
	@Column(name = "RIF_OLDTRANFLOW")
	private String oldTranFlow; // 原水号(退款时才使用)
	@Column(name = "RIF_VERSION")
	private Long version; // 版本号
	@Column(name = "RIF_CREATETIME")
	private String createTime; // 创建时间
	@Column(name = "RIF_STATUS")
	private String status; // 订单状态(31:退款成功;32：退款失败;33：退款状态可疑;34：待审核;35：审核通过;36:审核拒绝,37：打款成功，38：打款失败，40：待审核(对应担保支付)41：商户审核通过，42：商户审核拒绝，43，内管审核通过，44：内管审核拒绝)
	@Column(name = "RIF_TRXAMOUT", precision = 2)
	private Double trxAmount; // 交易额
	@Column(name = "RIF_CRYTYPE")
	private String cryType; // 币种
	@Column(name = "RIF_SUCCTIME")
	private String succTime; // 成功时间
	@Column(name = "RIF_USERID")
	private String userId; //
	@Column(name = "RIF_ORDERNO")
	private String orderNo; // 商城请求订单号
	@Column(name = "RIF_PAYCST")
	private String payCst; // 付款客户
	@Column(name = "RIF_PAYACC")
	private String payAcc; // 付款账户
	@Column(name = "RIF_RCVCST")
	private String rcvCst; // 收款客户
	@Column(name = "RIF_RCVACC")
	private String rcvAcc; // 收款账户
	@Column(name = "RIF_ORDERTYPE")
	private String orderType; // 订单类型
	@Column(name = "RIF_MALLID")
	private String mallId; // 商城ID
	@Column(name = "RIF_NOTIFYURL")
	private String notifyUral; // 通知URL
	@Column(name = "RIF_FEEAMOUNT", precision = 2)
	private Double feeAmout; // 手续费金额
	@Column(name = "RIF_REQIP")
	private String reqIp; // REQIP
	@Column(name = "RIF_DESCRIPTION")
	private String description; // 描述
	@Column(name = "RIF_PAY_ACCNAME")
	private String accName; // 商户付款账户
	@Column(name = "RIF_PAY_ACCNO")
	private String payAccNo; // 商户付款账号
	@Column(name = "RIF_PAY_STATUS")
	private String payStatus; // 商户付款状态(1 已付款、2 未付款)
	@Column(name = "RIF_PAY_AMOUNT", precision = 2)
	private Double payAmount; // 付款金额
	@Column(name = "RIF_CFM_USER")
	private String cfmUser; // 确认人
	@Column(name = "RIF_CFM_DATE")
	private String cfmDate; // 确认日期
	@Column(name = "RIF_CSTTYPE")
	private String cstType; // 客户类型(P 个人、C 企业客户、M 商户、S 平台)
	@Column(name = "RIF_SETTLE_STATUS")
	private String settleStatus; // 结算状态(01 未结算、02 结算中、03 结算已确认)
	@Column(name = "RIF_SETTLE_NO")
	private String settleNo; // 结算批次号
	@Column(name = "RIF_MER_REFUNDAMT", precision = 2)
	private Double merRefundAmt; // 商户应退金额
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

	public String getTranFlow() {
		return tranFlow;
	}

	public void setTranFlow(String tranFlow) {
		this.tranFlow = tranFlow;
	}

	public String getOldOrderNo() {
		return oldOrderNo;
	}

	public void setOldOrderNo(String oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}

	public String getOldTranFlow() {
		return oldTranFlow;
	}

	public void setOldTranFlow(String oldTranFlow) {
		this.oldTranFlow = oldTranFlow;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCryType() {
		return cryType;
	}

	public void setCryType(String cryType) {
		this.cryType = cryType;
	}

	public String getSuccTime() {
		return succTime;
	}

	public void setSuccTime(String succTime) {
		this.succTime = succTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayCst() {
		return payCst;
	}

	public void setPayCst(String payCst) {
		this.payCst = payCst;
	}

	public String getPayAcc() {
		return payAcc;
	}

	public void setPayAcc(String payAcc) {
		this.payAcc = payAcc;
	}

	public String getRcvCst() {
		return rcvCst;
	}

	public void setRcvCst(String rcvCst) {
		this.rcvCst = rcvCst;
	}

	public String getRcvAcc() {
		return rcvAcc;
	}

	public void setRcvAcc(String rcvAcc) {
		this.rcvAcc = rcvAcc;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

	public String getNotifyUral() {
		return notifyUral;
	}

	public void setNotifyUral(String notifyUral) {
		this.notifyUral = notifyUral;
	}

	public String getReqIp() {
		return reqIp;
	}

	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getPayAccNo() {
		return payAccNo;
	}

	public void setPayAccNo(String payAccNo) {
		this.payAccNo = payAccNo;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
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

	public String getCstType() {
		return cstType;
	}

	public void setCstType(String cstType) {
		this.cstType = cstType;
	}

	public String getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	public String getSettleNo() {
		return settleNo;
	}

	public void setSettleNo(String settleNo) {
		this.settleNo = settleNo;
	}

	public Double getTrxAmount() {
		return trxAmount;
	}

	public void setTrxAmount(Double trxAmount) {
		this.trxAmount = trxAmount;
	}

	public Double getFeeAmout() {
		return feeAmout;
	}

	public void setFeeAmout(Double feeAmout) {
		this.feeAmout = feeAmout;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getMerRefundAmt() {
		return merRefundAmt;
	}

	public void setMerRefundAmt(Double merRefundAmt) {
		this.merRefundAmt = merRefundAmt;
	}
}
