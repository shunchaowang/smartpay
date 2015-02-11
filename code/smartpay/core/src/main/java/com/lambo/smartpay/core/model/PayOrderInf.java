package com.lambo.smartpay.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 订单信息
 * 
 * @author swang
 *
 */
@Entity
@Table(name = "PAY_ORDER_INF")
public class PayOrderInf implements Serializable {
	private static final long serialVersionUID = -4829236448205242497L;
	@Id
	@Column(name = "OIF_TRANFLOW")
	private String tranFlow; // 流水号,返回给商城的平台流水号
	@Column(name = "OIF_ORDERNO")
	private String orderNo; // 商城请求订单号
	@Column(name = "OIF_VERSION")
	private Long version; // 版本号
	@Column(name = "OIF_CREATETIME")
	private String createTime; // 创建时间
	@Column(name = "OIF_STATUS")
	private String status; // 订单状态(0:未支付; 1:交易成功; 2:失败; 3:在途; 4:已关闭; 5:取消;
							// 6:待审核; 7:审核通过; 8:审核拒绝; 9:已退货; 10:可疑; 20:已付款待发货;
							// 30:发货待确认收货; 40：申请延期付款审核中; 50:退款中; 51:退款成功;
							// 52:退款失败;)
	@Column(name = "OIF_TRXAMOUT", precision = 2)
	private Double trxAmout; // 交易额
	@Column(name = "OIF_CRYTYPE")
	private String cryType; // 币种
	@Column(name = "OIF_PAY_SUCCTIME")
	private String paySuccTime; // 支付成功时间
	@Column(name = "OIF_SUCCTIME")
	private String succTime; // 成功时间
	@Column(name = "OIF_USERID")
	private String userId; // 用户ID
	@Column(name = "OIF_PAYCST")
	private String payCst; // 付款客户
	@Column(name = "OIF_PAYACC")
	private String payAcc; // 付款账户
	@Column(name = "OIF_RCVCST")
	private String rcvCst; // 收款客户
	@Column(name = "OIF_RCVACC")
	private String rcvAcc; // 收款账户
	@Column(name = "OIF_ORDERTYPE")
	private String orderType; // 订单类型：00,充值(CZ)、10,支付(ZF)、20,转账(ZZ)、80,缴费(JF)
	@Column(name = "OIF_MALLID")
	private String mallId; // 商城ID
	@Column(name = "OIF_GOODSINF")
	private String goodsInf; // 商品信息
	@Column(name = "OIF_NOTIFYURL")
	private String notifyUrl; // 后台通知的URL
	@Column(name = "OIF_REFUNDTIME")
	private String refundTime; // 退款时间
	@Column(name = "OIF_REFUNDAMT", precision = 2)
	private Double refundAmt; // 退款金额
	@Column(name = "OIF_REFUNDCOUNT")
	private Long refundCount; // 退款次数
	@Column(name = "OIF_FEEAMOUNT", precision = 2)
	private Double feeAmount; // 手续费金额
	@Column(name = "OIF_REQIP")
	private String reqIp; // REQIP
	@Column(name = "OIF_DESCRIPTION")
	private String description; // 描述
	@Column(name = "OIF_REMARKONE")
	private String remarkOne; // 备注
	@Column(name = "OIF_REMARKTWO")
	private String remarkTwo; // 备注
	@Column(name = "OIF_SETTLE_NO")
	private String settleNo; // 结算批次号
	@Column(name = "OIF_SETTLE_DATE")
	private String settleDate; // 结算时间
	@Column(name = "OIF_SETTLE_STATUS")
	private String settleStatus; // 结算状态(01 未结算、02 结算中、03 结算已确认)
	@Column(name = "OIF_CSTTYPE")
	private String cstType; // 客户类型(P 个人、C 企业客户、M 商户、S 平台)
	@Column(name = "OIF_NOTIFYCOUNT")
	private Integer notifyCount; // 商户后台通知累计次数
	@Column(name = "OIF_NOTIFYFLAG")
	private String notifyFlag; // 商户后台通知状态(0:未成功、1:成功)
	@Column(name = "OIF_NOTIFYCONTENT")
	private String notifyContent; // 通知内容
	@Column(name = "OIF_TIMEOUT")
	private String timeOut; // 超时时间
	@Column(name = "OIF_PROFITINF")
	private String profitinf; // 分润信息
	@Column(name = "OIF_GOODSNUM")
	private String goodsNum; // 商品数量
	@Column(name = "OIF_OLDORDERNO")
	private String oldOrderNo; // 原订单号(退款时才使用)
	@Column(name = "OIF_BATCHNO")
	private String batchNo; // 批次号
	@Column(name = "OIF_TRADE_MODE")
	private String tradeMode; // 交易模式（1即时到账、2担保交易）
	@Column(name = "OIF_TRANS_TYPE")
	private String TransType; // 交易类型（1实物交易、2虚拟交易）
	@Column(name = "OIF_LOGISTICS_NAME")
	private String logisticsName; // 物流公司名称
	@Column(name = "OIF_LOGISTICS_ORDERNO")
	private String logisticsOrder; // 物流单号
	@Column(name = "OIF_LOGISTICS_SENDTIME")
	private String logisticsSendtime; // 物流发货时间
	@Column(name = "OIF_LOGISTICS_TYPE")
	private String logisticsType; // 物流类型（POST平邮、EXPRESS快递、EMS）
	@Column(name = "OIF_RETURNURL")
	private String returnUrl; // 前台返回商户的URL
	@Column(name = "OIF_SECURED_DEFAULT_PAYTIME")
	private String defaultPayTime; // 担保交易默认打款时间
	@Column(name = "OIF_DELAY_TIMES")
	private Integer delayTimes; // 订单延期次数
	@Transient
	private String beginDate; // 开始时间
	@Transient
	private String endDate; // 结束时间
	@Transient
	private Long minTrxAmout; // 最小交易额
	@Transient
	private Long maxTrxAmout; // 最大交易额
	@Transient
	private String jstatus; // 仲裁处理状态
	@Transient
	private String jtype; // 仲裁类型

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTranFlow() {
		return tranFlow;
	}

	public void setTranFlow(String tranFlow) {
		this.tranFlow = tranFlow;
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

	public String getPaySuccTime() {
		return paySuccTime;
	}

	public void setPaySuccTime(String paySuccTime) {
		this.paySuccTime = paySuccTime;
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

	public String getGoodsInf() {
		return goodsInf;
	}

	public void setGoodsInf(String goodsInf) {
		this.goodsInf = goodsInf;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public Long getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(Long refundCount) {
		this.refundCount = refundCount;
	}

	public Double getTrxAmout() {
		return trxAmout;
	}

	public void setTrxAmout(Double trxAmout) {
		this.trxAmout = trxAmout;
	}

	public Double getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(Double refundAmt) {
		this.refundAmt = refundAmt;
	}

	public Double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
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

	public String getRemarkOne() {
		return remarkOne;
	}

	public void setRemarkOne(String remarkOne) {
		this.remarkOne = remarkOne;
	}

	public String getRemarkTwo() {
		return remarkTwo;
	}

	public void setRemarkTwo(String remarkTwo) {
		this.remarkTwo = remarkTwo;
	}

	public String getSettleNo() {
		return settleNo;
	}

	public void setSettleNo(String settleNo) {
		this.settleNo = settleNo;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	public String getCstType() {
		return cstType;
	}

	public void setCstType(String cstType) {
		this.cstType = cstType;
	}

	public Integer getNotifyCount() {
		return notifyCount;
	}

	public void setNotifyCount(Integer notifyCount) {
		this.notifyCount = notifyCount;
	}

	public String getNotifyFlag() {
		return notifyFlag;
	}

	public void setNotifyFlag(String notifyFlag) {
		this.notifyFlag = notifyFlag;
	}

	public String getNotifyContent() {
		return notifyContent;
	}

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getProfitinf() {
		return profitinf;
	}

	public void setProfitinf(String profitinf) {
		this.profitinf = profitinf;
	}

	public String getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getOldOrderNo() {
		return oldOrderNo;
	}

	public void setOldOrderNo(String oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getTransType() {
		return TransType;
	}

	public void setTransType(String transType) {
		TransType = transType;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getLogisticsOrder() {
		return logisticsOrder;
	}

	public void setLogisticsOrder(String logisticsOrder) {
		this.logisticsOrder = logisticsOrder;
	}

	public String getLogisticsSendtime() {
		return logisticsSendtime;
	}

	public void setLogisticsSendtime(String logisticsSendtime) {
		this.logisticsSendtime = logisticsSendtime;
	}

	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getDefaultPayTime() {
		return defaultPayTime;
	}

	public void setDefaultPayTime(String defaultPayTime) {
		this.defaultPayTime = defaultPayTime;
	}

	public Integer getDelayTimes() {
		return delayTimes;
	}

	public void setDelayTimes(Integer delayTimes) {
		this.delayTimes = delayTimes;
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

	public Long getMinTrxAmout() {
		return minTrxAmout;
	}

	public void setMinTrxAmout(Long minTrxAmout) {
		this.minTrxAmout = minTrxAmout;
	}

	public Long getMaxTrxAmout() {
		return maxTrxAmout;
	}

	public void setMaxTrxAmout(Long maxTrxAmout) {
		this.maxTrxAmout = maxTrxAmout;
	}

	public String getJstatus() {
		return jstatus;
	}

	public void setJstatus(String jstatus) {
		this.jstatus = jstatus;
	}

	public String getJtype() {
		return jtype;
	}

	public void setJtype(String jtype) {
		this.jtype = jtype;
	}
}
