package com.lambo.smartpay.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商户客户信息
 * 
 * @author swang
 *
 */
@Entity
@Table(name = "MER_CST_INF")
public class MerchantCustomer implements Serializable {
	private static final long serialVersionUID = -164476210290311809L;
	@Id
	@Column(name = "MCF_CSTNO")
	private String customerNo; // 客户编号
	@Column(name = "MCF_CSTNAME")
	private String name; // 商户客户名称
	@Column(name = "MCF_VERSION")
	private Integer version; // 版本号
	@Column(name = "MCF_STATUS")
	private String status; // 商户状态
	@Column(name = "MCF_ADDRESS")
	private String address; // 商户地址
	@Column(name = "MCF_MOBILE")
	private String mobile; // 绑定电话
	@Column(name = "MCF_CREATEDATE")
	private String createdDate; // 创建时间
	@Column(name = "MCF_CREDITLEVEL")
	private Integer creditLevel; // 商户信誉级别
	@Column(name = "MCF_CSTTYPE")
	private String customerType; // 商户客户类型(B2C;B2B)
	@Column(name = "MCF_EMAIL")
	private String email; // 商户注册邮箱
	@Column(name = "MCF_EMAILVALIDATE")
	private Integer emailValidateDate; // 邮箱确认
	@Column(name = "MCF_ACTIVEDATE")
	private String activateDate; // 激活时间
	@Column(name = "MCF_ENCRYPTKEY")
	private String encryptKey; // 加密KEY
	@Column(name = "MCF_CTFNO")
	private String certificateNo; // 证件号
	@Column(name = "MCF_CTFTYPE")
	private String certificateType; // 证件类型
	@Column(name = "MCF_EXPIREDATE")
	private String certificateExpireDate; // 证件失效日期
	@Column(name = "MCF_ORDERTYPEFILTER")
	private String orderTypeFilter; // 订单类型过滤
	@Column(name = "MCF_PAYCHANNELFILTER")
	private String payChannelFilter; // 支付渠道,(00: 全部 01：账户支付 02 预付卡支付 03 网银支付)
	@Column(name = "MCF_PAYTYPE")
	private String payType; // 支持的支付方式
	@Column(name = "MCF_AUTHDATE")
	private String authorizationDate; // 实名认证时间
	@Column(name = "MCF_AUTHSTATUS")
	private String authorizationStatus; // 实名认证状态
	@Column(name = "MCF_SHORTNAME")
	private String shortName; // 客户简称
	@Column(name = "MCF_MALLADDRESS", length = 750)
	private String mallAddress; // 接入商城网
	@Column(name = "MCF_BUSINESSSCOPE")
	private String businessCode; // 经营范围
	@Column(name = "MCF_POSTADDRESS")
	private String postal; // 邮政编码
	@Column(name = "MCF_PHONE")
	private String phone; // 联系座机
	@Column(name = "MCF_FAX")
	private String fax; // 传真
	@Column(name = "MCF_MSN")
	private String msn; // MSN
	@Column(name = "MCF_QQ")
	private String qq; // QQ
	@Column(name = "MCF_OTHERCONTACT")
	private String otherContact; // 其他联系方式
	@Column(name = "MCF_CONTACTOR")
	private String contact; // 联系人
	@Column(name = "MCF_IMAGEID")
	private String imageId; // 客户图片的ID
	@Column(name = "MCF_IMAGEPATH")
	private String imagePath; // 客户logo图片的路径和名称
	@Column(name = "MCF_HOSTNO")
	private String hostNo; // 金蝶代码
	@Column(name = "MCF_MANAGER")
	private String manager; // 客户经理
	@Column(name = "MCF_AREA")
	private String area; // 地区
	@Column(name = "MCF_ENCRYPTTYPE")
	private String encryptionType; // 消息摘要方式，目前支持两种（MD5、CERT）
	@Column(name = "MCF_RISKLEVEL")
	private Integer riskLevel; // 风险等级（0:零风险;1:低风险;2:较低风险;3:中风险;4:较高风险;5:高风险; ）
	@Column(name = "MCF_ISRETURNFEE")
	private String isReturnFee; // 退款时，平台是否退还原交易手续费（0：退还，1：不退还）

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(Integer creditLevel) {
        this.creditLevel = creditLevel;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEmailValidateDate() {
        return emailValidateDate;
    }

    public void setEmailValidateDate(Integer emailValidateDate) {
        this.emailValidateDate = emailValidateDate;
    }

    public String getActivateDate() {
        return activateDate;
    }

    public void setActivateDate(String activateDate) {
        this.activateDate = activateDate;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateExpireDate() {
        return certificateExpireDate;
    }

    public void setCertificateExpireDate(String certificateExpireDate) {
        this.certificateExpireDate = certificateExpireDate;
    }

    public String getOrderTypeFilter() {
        return orderTypeFilter;
    }

    public void setOrderTypeFilter(String orderTypeFilter) {
        this.orderTypeFilter = orderTypeFilter;
    }

    public String getPayChannelFilter() {
        return payChannelFilter;
    }

    public void setPayChannelFilter(String payChannelFilter) {
        this.payChannelFilter = payChannelFilter;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAuthorizationDate() {
        return authorizationDate;
    }

    public void setAuthorizationDate(String authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public String getAuthorizationStatus() {
        return authorizationStatus;
    }

    public void setAuthorizationStatus(String authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMallAddress() {
        return mallAddress;
    }

    public void setMallAddress(String mallAddress) {
        this.mallAddress = mallAddress;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getOtherContact() {
        return otherContact;
    }

    public void setOtherContact(String otherContact) {
        this.otherContact = otherContact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getHostNo() {
        return hostNo;
    }

    public void setHostNo(String hostNo) {
        this.hostNo = hostNo;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getIsReturnFee() {
        return isReturnFee;
    }

    public void setIsReturnFee(String isReturnFee) {
        this.isReturnFee = isReturnFee;
    }
}
