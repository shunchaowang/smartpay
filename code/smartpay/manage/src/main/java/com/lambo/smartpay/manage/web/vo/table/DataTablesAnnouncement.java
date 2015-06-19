package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.core.persistence.entity.Announcement;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by chensf on 5/9/2015.
 */
public class DataTablesAnnouncement implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String createdTime;


    public DataTablesAnnouncement(Announcement anouncement) {
        id = anouncement.getId();
        title = anouncement.getTitle();
        Locale locale = LocaleContextHolder.getLocale();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        createdTime = dateFormat.format(anouncement.getCreatedTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
