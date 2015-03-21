package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.persistence.entity.Merchant;

import java.text.SimpleDateFormat;

/**
 * Created by swang on 3/20/2015.
 */
public class DataTablesTransaction {

    private Long id;
    private String name;
    private String address;
    private String contact;
    private String tel;
    private String email;
    private String createdTime;
    private String merchantStatus;

    public DataTablesTransaction(Merchant merchant) {
        id = merchant.getId();
        name = merchant.getName();
        address = merchant.getAddress();
        contact = merchant.getContact();
        tel = merchant.getTel();
        email = merchant.getEmail();
        SimpleDateFormat format = new SimpleDateFormat("MMM/dd/yyyy");
        createdTime = format.format(merchant.getCreatedTime());
        merchantStatus = merchant.getMerchantStatus().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getMerchantStatus() {
        return merchantStatus;
    }

    public void setMerchantStatus(String merchantStatus) {
        this.merchantStatus = merchantStatus;
    }
}
