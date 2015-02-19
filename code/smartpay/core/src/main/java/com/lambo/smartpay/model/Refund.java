package com.lambo.smartpay.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "REFUND")
public class Refund implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUMBER", length = 32, nullable = false)
    private String number;

    @Column(name = "AMOUNT", nullable = false)
    private Float amount;

    @Column(name = "CREATED_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "UPDATED_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Column(name = "SUCCESS_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date successTime;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "BANK_NAME", length = 256, nullable = true)
    private String bankName;

    @Column(name = "BANK_ACCOUNT_NUMBER", length = 256, nullable = false)
    private String bankAccountNumber;

    @Column(name = "BANK_ACCOUNT_EXP_DATE", length = 256, nullable = false)
    private String bankAccountExpDate;

    @Column(name = "BANK_TRANSACTION_NUMBER", length = 256, nullable = false)
    private String bankTransactionNumber;

    @Column(name = "BANK_RETURN_CODE", length = 256, nullable = false)
    private String bankReturnCode;

    @Column(name = "BILL_FIRST_NAME", length = 32, nullable = false)
    private String billFirstName;

    @Column(name = "BILL_LAST_NAME", length = 32, nullable = false)
    private String billLastName;

    @Column(name = "BILL_ADDRESS_1", length = 128, nullable = false)
    private String billAddress1;

    @Column(name = "BILL_ADDRESS_2", length = 128, nullable = true)
    private String billAddress2;

    @Column(name = "BILL_CITY", length = 128, nullable = false)
    private String billCity;

    @Column(name = "BILL_STATE", length = 128, nullable = false)
    private String billState;

    @Column(name = "BILL_ZIP_CODE", length = 128, nullable = false)
    private String billZipCode;

    @Column(name = "BILL_COUNTRY", length = 128, nullable = false)
    private String billCountry;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "REFUND_STATUS_ID", nullable = false)
    private RefundStatus refundStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "CURRENCY_ID", nullable = false)
    private Currency currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getBankAccountExpDate() {
        return bankAccountExpDate;
    }

    public void setBankAccountExpDate(String bankAccountExpDate) {
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

    public RefundStatus getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
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
}
