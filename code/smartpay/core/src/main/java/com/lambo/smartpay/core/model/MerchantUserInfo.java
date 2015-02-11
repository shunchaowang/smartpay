package com.lambo.smartpay.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 商户用户信息
 * 
 * @author swang
 *
 */
@Entity
@Table(name = "MER_USER_INF")
public class MerchantUserInfo implements Serializable {
	private static final long serialVersionUID = 952580806967072610L;
	@Id
	@Column(name = "MUF_USERNO")
	private String userNo; // 用户号
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MUF_CSTNO")
	private MerchantCustomer merchantCustomer;// 客户号
	@Column(name = "MUF_VERSION")
	private Long version; // 版本号
	@Column(name = "MUF_USERNAME")
	private String userName; // 姓名
	@Column(name = "MUF_AUTHTYPE")
	private String authType; // 用户权限
	@Column(name = "MUF_EMAIL")
	private String email; // 邮箱
	@Column(name = "MUF_MOBILE")
	private String mobile; // 电话
	@Column(name = "MUF_CREATETIME")
	private String createTime; // 创建时间
	@Column(name = "MUF_DEPARTMENT")
	private String department; // 所属部门
	@Column(name = "MUF_PASSWORD")
	private String password; // 用户登录密码
	@Column(name = "MUF_PAYPWD")
	private String paypwd; // 支付密码
	@Column(name = "MUF_ANSWER")
	private String answer; // 私密问题答案
	@Column(name = "MUF_QUESTIONT")
	private String question; // 私密问题
	@Column(name = "MUF_STATUS")
	private String status; // 用户状态
	@Column(name = "MUF_LOGONNAME")
	private String logonName; // 登录名
	@Column(name = "MUF_CREATOR_ID")
	private Long creatorId; // 创建者
	@Column(name = "MUF_ADDRESS")
	private String address; // 地址
	@Column(name = "MUF_QQ")
	private String qq; // QQ
	@Column(name = "MUF_MSN")
	private String msn; // MSN
	@Column(name = "MUF_OTHERCONTACT")
	private String otherContact; // 其他联系方式
	@Column(name = "MUF_POSTADDRESS")
	private String postAddress; // 邮政编码
	@Column(name = "MUF_PHONE")
	private String phone; // 联系座机
	@Column(name = "MUF_PRETENTINFO")
	private String pretentInfo; // 用户预留信息
	@Column(name = "MUF_INITPWD")
	private String initPwd; // 登录密码状态
	@Column(name = "MUF_INITPAYPWD")
	private String initPaypwd; // 支付密码状态
	@Column(name = "MUF_LASTPWDCGDATE")
	private String lastPwdCgdate; // 最后一次修改密码日期
	@Column(name = "MUF_SENDMAILFLAG")
	private String sendMailFlag; // 发送邮件标志位
	@Transient
	private String childmenu;// 菜单权限列表(如：1000,1001)
	@Transient
	private String beginDate; // 开始时间
	@Transient
	private String endDate; // 结束时间
	@Transient
	private String lastLoginDate;// 最后登录成功时间
	@Transient
	private String merCstName;// 商户名称
	@Transient
	private String authCode;// 验证码

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPaypwd() {
		return paypwd;
	}

	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getOtherContact() {
		return otherContact;
	}

	public void setOtherContact(String otherContact) {
		this.otherContact = otherContact;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPretentInfo() {
		return pretentInfo;
	}

	public void setPretentInfo(String pretentInfo) {
		this.pretentInfo = pretentInfo;
	}

	public String getInitPwd() {
		return initPwd;
	}

	public void setInitPwd(String initPwd) {
		this.initPwd = initPwd;
	}

	public String getInitPaypwd() {
		return initPaypwd;
	}

	public void setInitPaypwd(String initPaypwd) {
		this.initPaypwd = initPaypwd;
	}

	public String getLastPwdCgdate() {
		return lastPwdCgdate;
	}

	public void setLastPwdCgdate(String lastPwdCgdate) {
		this.lastPwdCgdate = lastPwdCgdate;
	}

	public String getSendMailFlag() {
		return sendMailFlag;
	}

	public void setSendMailFlag(String sendMailFlag) {
		this.sendMailFlag = sendMailFlag;
	}

	public String getChildmenu() {
		return childmenu;
	}

	public void setChildmenu(String childmenu) {
		this.childmenu = childmenu;
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

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getMerCstName() {
		return merCstName;
	}

	public void setMerCstName(String merCstName) {
		this.merCstName = merCstName;
	}

	public MerchantCustomer getMerchantCustomer() {
		return merchantCustomer;
	}

	public void setMerchantCustomer(MerchantCustomer merchantCustomer) {
		this.merchantCustomer = merchantCustomer;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
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
