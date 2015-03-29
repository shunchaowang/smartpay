package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Merchant;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by swang on 3/20/2015.
 */
public class DataTablesMerchant {

    private Long id;
    private String identity;
    private String name;
    private String address;
    private String contact;
    private String tel;
    private String email;
    private String createdTime;
    private String merchantStatus;

    public DataTablesMerchant(Merchant merchant) {
        id = merchant.getId();
        identity = merchant.getIdentity();
        name = merchant.getName();
        address = merchant.getAddress() == null ? "" : merchant.getAddress();
        contact = merchant.getContact() == null ? "" : merchant.getContact();
        tel = merchant.getTel() == null ? "" : merchant.getTel();
        email = merchant.getEmail() == null ? "" : merchant.getEmail();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        createdTime = dateFormat.format(merchant.getCreatedTime());
        merchantStatus = merchant.getMerchantStatus().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String id) {
        this.identity = identity;
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
