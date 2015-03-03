package com.lambo.smartpay.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "USERS")
public class User implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_USERNAME", length = 32, nullable = false)
    private String username;

    @Column(name = "USER_PASSWORD", length = 32, nullable = false)
    private String password;

    @Column(name = "USER_FIRST_NAME", length = 32, nullable = false)
    private String firstName;

    @Column(name = "USER_LAST_NAME", length = 32, nullable = false)
    private String lastName;

    @Column(name = "USER_EMAIL", length = 32, nullable = false)
    private String email;

    @Column(name = "USER_CREATED_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "USER_UPDATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Column(name = "USER_REMARK", length = 255)
    private String remark;

    @Column(name = "USER_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "PROFILE_IMAGE")
    @Lob
    private byte[] profileImage;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            optional = false)
    @JoinColumn(name = "USER_MCHT_ID", nullable = false)
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            optional = false)
    @JoinColumn(name = "USER_USST_ID", nullable = false)
    private UserStatus userStatus;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "USER_ROLE_MAPPINGS",
            joinColumns = {@JoinColumn(name = "URMP_USER_ID", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "URMP_ROLE_ID", nullable = false, updatable
                    = false)}
    )
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
