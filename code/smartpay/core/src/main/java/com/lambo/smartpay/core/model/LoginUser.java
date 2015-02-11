package com.lambo.smartpay.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "bbp_login_user")
public class LoginUser {

	@Id
	@GeneratedValue(generator = "LoginUserSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "LoginUserSeq", sequenceName = "login_user_seq", initialValue = 1, allocationSize = 1)
	private Integer id;
	@Column(name = "username", length = 30)
	private String username; // 用户名
	@Column(name = "password", length = 50)
	private String password; // 密码
	@Transient
	private String confirmPassword;
	@Transient
	private String authCode; // 验证码
	@Column(name = "email", length = 100, nullable = true)
	private String email; // 联系Email

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

}
