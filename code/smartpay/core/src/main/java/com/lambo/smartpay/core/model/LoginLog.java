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
@Table(name = "bbp_login_log")
public class LoginLog {

	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_FAIL = "fail";

	@Id
	@GeneratedValue(generator = "LoginLogSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "LoginLogSeq", sequenceName = "login_log_seq", initialValue = 1, allocationSize = 1)
	private Long id;
	@Column(name = "username", length = 30)
	private String username;
	@Column(name = "status", length = 10)
	private String status;
	@Column(name = "ip", length = 40)
	private String ip;
	@Column(name = "login_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginDate;
	@Column(name = "agent", length = 255)
	private String agent;

	public LoginLog() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

}
