package com.lambo.smartpay.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单历史信息
 * 
 * @author swang
 *
 */
@Entity
@Table(name = "PAY_ORDER_HISTORY")
public class PayOrderHistory implements Serializable {
	private static final long serialVersionUID = 3436829362885304982L;
	@Id
	@Column(name = "POH_NO")
	private String pohNo; // 流水号 日期8位+12位流水
	@Column(name = "POH_ORDER_TRANFLOWNO")
	private String pohOrderTranflowno; // 关联的订单交易号
	@Column(name = "POH_CSTNO")
	private String pohCstno; // 操作人员客户号
	@Column(name = "POH_CSTTYPE")
	private String pohCsttype; // 操作客户类型（P；个人 M：商户 I：内管 C：企业）
	@Column(name = "POH_BSNCODE")
	private String pohBsncode; // 操作交易码
	@Column(name = "POH_DATETIME")
	private String pohDatetime; // 操作时间
	@Column(name = "POH_DECS")
	private String pohDecs; // 描述或理由

	public String getPohNo() {
		return pohNo;
	}

	public void setPohNo(String pohNo) {
		this.pohNo = pohNo;
	}

	public String getPohOrderTranflowno() {
		return pohOrderTranflowno;
	}

	public void setPohOrderTranflowno(String pohOrderTranflowno) {
		this.pohOrderTranflowno = pohOrderTranflowno;
	}

	public String getPohCstno() {
		return pohCstno;
	}

	public void setPohCstno(String pohCstno) {
		this.pohCstno = pohCstno;
	}

	public String getPohCsttype() {
		return pohCsttype;
	}

	public void setPohCsttype(String pohCsttype) {
		this.pohCsttype = pohCsttype;
	}

	public String getPohBsncode() {
		return pohBsncode;
	}

	public void setPohBsncode(String pohBsncode) {
		this.pohBsncode = pohBsncode;
	}

	public String getPohDatetime() {
		return pohDatetime;
	}

	public void setPohDatetime(String pohDatetime) {
		this.pohDatetime = pohDatetime;
	}

	public String getPohDecs() {
		return pohDecs;
	}

	public void setPohDecs(String pohDecs) {
		this.pohDecs = pohDecs;
	}
}
