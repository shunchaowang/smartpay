package com.lambo.smartpay.core.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "workflow_history")
public class WorkflowHistory {
	public static final String STATUS_START = "start";
	public static final String STATUS_COMPLETE = "complete";
	public static final String STATUS_REJECT = "reject";
	public static final String STATUS_END = "end";

	@Id
	@GeneratedValue(generator = "WorkflowHistorySeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "WorkflowHistorySeq", sequenceName = "workflow_history_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	@Column(name = "proc_def_id", length = 80)
	private String procDefId;

	@Column(name = "proc_inst_id", length = 80)
	private String procInstId;

	@Column(name = "task_id", length = 100)
	private String taskId;

	@Column(name = "task_name")
	private String taskName;

	@Column(name = "wf_doc_code", length = 100)
	private String wfDocCode;

	@Column(name = "wf_doc_name", length = 200)
	private String wfDocName;

	@Column(name = "proxy_code", length = 50)
	private String proxyCode;

	@Column(name = "merchant_code", length = 50)
	private String merchantCode;

	@Column(name = "operator", length = 50)
	private String operator;

	@Column(name = "time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date time;

	@Column(name = "status", length = 10)
	private String status;

	@Column
	private String remark;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcDefId() {
		return this.procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getProcInstId() {
		return this.procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProxyCode() {
		return this.proxyCode;
	}

	public void setProxyCode(String proxyCode) {
		this.proxyCode = proxyCode;
	}

	public String getMerchantCode() {
		return this.merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getWfDocName() {
		return this.wfDocName;
	}

	public void setWfDocName(String wfDocName) {
		this.wfDocName = wfDocName;
	}

	public String getWfDocCode() {
		return this.wfDocCode;
	}

	public void setWfDocCode(String wfDocCode) {
		this.wfDocCode = wfDocCode;
	}
}
