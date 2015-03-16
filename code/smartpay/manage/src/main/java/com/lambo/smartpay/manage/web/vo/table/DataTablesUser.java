package com.lambo.smartpay.manage.web.vo.table;

import com.lambo.smartpay.persistence.entity.User;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by swang on 3/15/2015.
 */
public class DataTablesUser implements Serializable {

    private static final long serialVersionUID = 1L;

    //private String id;
    private String username;
//    private String firstName;
//    private String lastName;
//    private String email;
//    private String createdTime;
//    private String userStatus;

    public DataTablesUser(User user) {
        //id = user.getId().toString();
        username = user.getUsername();
//        firstName = user.getFirstName();
//        lastName = user.getLastName();
//        email = user.getEmail();
//        createdTime = user.getCreatedTime().toString();
//        userStatus = user.getUserStatus().getName();
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getCreatedTime() {
//        return createdTime;
//    }
//
//    public void setCreatedTime(String createdTime) {
//        this.createdTime = createdTime;
//    }
//
//    public String getUserStatus() {
//        return userStatus;
//    }
//
//    public void setUserStatus(String userStatus) {
//        this.userStatus = userStatus;
//    }
}
