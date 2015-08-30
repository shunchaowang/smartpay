package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.CurrencyExchange;
import com.lambo.smartpay.core.persistence.entity.Site;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by chensf on 8/29/2015.
 */
public class DataTablesCurrencyEx implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String createdTime;
    private String updatedTime;
    private String crexRemark;
    private Double crexAmountFrom;
    private Double crexAmountTo;
    private String crexCurrencyFrom;
    private String crexCurrencyTo;
    private boolean active;

    public DataTablesCurrencyEx(CurrencyExchange currencyExchange) {

        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        id = currencyExchange.getId();
        if (currencyExchange.getCreatedTime() != null) {
            createdTime = dateFormat.format(currencyExchange.getCreatedTime());
        }
        if(currencyExchange.getUpdatedTime() !=null ){
            updatedTime = dateFormat.format(currencyExchange.getUpdatedTime());
        }
        crexRemark = currencyExchange.getRemark();
        crexAmountFrom = currencyExchange.getAmountFrom();
        crexAmountTo = currencyExchange.getAmountTo();
        crexCurrencyFrom = currencyExchange.getCurrencyFrom().getName();
        crexCurrencyTo = currencyExchange.getCurrencyTo().getName();
        active = currencyExchange.getActive();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCrexRemark() {
        return crexRemark;
    }

    public void setCrexRemark(String crexRemark) {
        this.crexRemark = crexRemark;
    }

    public Double getCrexAmountFrom() {
        return crexAmountFrom;
    }

    public void setCrexAmountFrom(Double crexAmountFrom) {
        this.crexAmountFrom = crexAmountFrom;
    }

    public Double getCrexAmountTo() {
        return crexAmountTo;
    }

    public void setCrexAmountTo(Double crexAmountTo) {
        this.crexAmountTo = crexAmountTo;
    }

    public String getCrexCurrencyFrom() {
        return crexCurrencyFrom;
    }

    public void setCrexCurrencyFrom(String crexCurrencyFrom) {
        this.crexCurrencyFrom = crexCurrencyFrom;
    }

    public String getCrexCurrencyTo() {
        return crexCurrencyTo;
    }

    public void setCrexCurrencyTo(String crexCurrencyTo) {
        this.crexCurrencyTo = crexCurrencyTo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
