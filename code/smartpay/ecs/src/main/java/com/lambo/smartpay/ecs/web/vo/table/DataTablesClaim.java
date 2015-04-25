package com.lambo.smartpay.ecs.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Claim;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by swang on 3/27/2015.
 */
public class DataTablesClaim {

    private Long id;
    private String remark;
    private String createdTime;
    Boolean hasAttachment;

    public DataTablesClaim(Claim claim) {
        id = claim.getId();
        remark = claim.getRemark();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        createdTime = dateFormat.format(claim.getCreatedTime());
        hasAttachment = false;
        if (claim.getAttachment() != null && claim.getAttachment().length > 0) {
            hasAttachment = true;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Boolean getHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(Boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }
}
