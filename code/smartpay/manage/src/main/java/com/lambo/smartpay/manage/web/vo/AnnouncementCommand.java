package com.lambo.smartpay.manage.web.vo;

import java.util.Date;

/**
 * Created by chensf on 5/9/2015.
 */
public class AnnouncementCommand {

    private Long id;
    private String title;
    private String content;
    private Date createdTime;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
