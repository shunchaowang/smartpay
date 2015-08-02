package com.lambo.smartpay.core.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "PAYMENTS")
public class Payment implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PYMT_ID")
    private Long id;

    @Column(name = "PYMT_AMOUNT", nullable = false)
    private Float amount;

    @Column(name = "PYMT_FEE", nullable = false)
    private Float fee;

    @Column(name = "PYMT_CLIENT_IP", nullable = false)
    private String clientIp;

    @Column(name = "PYMT_CREATED_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "PYMT_UPDATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Column(name = "PYMT_SUCCESS_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date successTime;

    @Column(name = "PYMT_REMARK", length = 255)
    private String remark;

    @Column(name = "PYMT_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "PYMT_BANK_NAME", length = 128, nullable = true)
    private String bankName;

    @Column(name = "PYMT_BANK_ACCOUNT_NUMBER", length = 128, nullable = false)
    private String bankAccountNumber;

    @Column(name = "PYMT_BANK_ACCOUNT_EXP_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date bankAccountExpDate;

    @Column(name = "PYMT_BANK_TRANSACTION_NUMBER", length = 128, nullable = false)
    private String bankTransactionNumber;

    @Column(name = "PYMT_BANK_RETURN_CODE", length = 128, nullable = false)
    private String bankReturnCode;

    @Column(name = "PYMT_BILL_FIRST_NAME", length = 32, nullable = false)
    private String billFirstName;

    @Column(name = "PYMT_BILL_LAST_NAME", length = 32, nullable = false)
    private String billLastName;

    @Column(name = "PYMT_BILL_ADDRESS_1", length = 128, nullable = false)
    private String billAddress1;

    @Column(name = "PYMT_BILL_ADDRESS_2", length = 128, nullable = true)
    private String billAddress2;

    @Column(name = "PYMT_BILL_CITY", length = 128, nullable = false)
    private String billCity;

    @Column(name = "PYMT_BILL_STATE", length = 128, nullable = false)
    private String billState;

    @Column(name = "PYMT_BILL_ZIP_CODE", length = 128, nullable = false)
    private String billZipCode;

    @Column(name = "PYMT_BILL_COUNTRY", length = 128, nullable = false)
    private String billCountry;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            optional = false)
    @JoinColumn(name = "PYMT_PYST_ID", nullable = false)
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            optional = false)
    @JoinColumn(name = "PYMT_PYTP_ID", nullable = false)
    private PaymentType paymentType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            optional = false)
    @JoinColumn(name = "PYMT_ORDR_ID", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            optional = false)
    @JoinColumn(name = "PYMT_CRCY_ID", nullable = false)
    private Currency currency;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            optional = true)
    @JoinColumn(name = "PYMT_WDRL_ID", nullable = true)
    private Withdrawal withdrawal;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "payment",
            orphanRemoval = true)
    private Set<Claim> claims;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public Date getBankAccountExpDate() {
        return bankAccountExpDate;
    }

    public void setBankAccountExpDate(Date bankAccountExpDate) {
        this.bankAccountExpDate = bankAccountExpDate;
    }

    public String getBankTransactionNumber() {
        return bankTransactionNumber;
    }

    public void setBankTransactionNumber(String bankTransactionNumber) {
        this.bankTransactionNumber = bankTransactionNumber;
    }

    public String getBankReturnCode() {
        return bankReturnCode;
    }

    public void setBankReturnCode(String bankReturnCode) {
        this.bankReturnCode = bankReturnCode;
    }

    public String getBillFirstName() {
        return billFirstName;
    }

    public void setBillFirstName(String billFirstName) {
        this.billFirstName = billFirstName;
    }

    public String getBillLastName() {
        return billLastName;
    }

    public void setBillLastName(String billLastName) {
        this.billLastName = billLastName;
    }

    public String getBillAddress1() {
        return billAddress1;
    }

    public void setBillAddress1(String billAddress1) {
        this.billAddress1 = billAddress1;
    }

    public String getBillAddress2() {
        return billAddress2;
    }

    public void setBillAddress2(String billAddress2) {
        this.billAddress2 = billAddress2;
    }

    public String getBillCity() {
        return billCity;
    }

    public void setBillCity(String billCity) {
        this.billCity = billCity;
    }

    public String getBillState() {
        return billState;
    }

    public void setBillState(String billState) {
        this.billState = billState;
    }

    public String getBillZipCode() {
        return billZipCode;
    }

    public void setBillZipCode(String billZipCode) {
        this.billZipCode = billZipCode;
    }

    public String getBillCountry() {
        return billCountry;
    }

    public void setBillCountry(String billCountry) {
        this.billCountry = billCountry;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Withdrawal getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(Withdrawal withdrawal) {
        this.withdrawal = withdrawal;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Claim> getClaims() {
        return claims;
    }

    public void setClaims(Set<Claim> claims) {
        this.claims = claims;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
