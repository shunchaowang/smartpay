package com.lambo.smartpay.ecs.web.vo;

/**
 * Created by swang on 3/16/2015.
 */
public class UserCommand {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String remark;
    private Boolean active;
    private String createdTime;
    private String updatedTime;
    // relationships
    private Long merchant;
    private String merchantName;
    private Long userStatus;
    private String userStatusName;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public Long getMerchant() {
        return merchant;
    }

    public void setMerchant(Long merchant) {
        this.merchant = merchant;
    }

    public Long getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Long userStatus) {
        this.userStatus = userStatus;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getUserStatusName() {
        return userStatusName;
    }

    public void setUserStatusName(String userStatusName) {
        this.userStatusName = userStatusName;
    }

    public enum Role {
        Admin("100"),
        MerchantAdmin("200"),
        MerchantOperator("201");

        private String code;

        Role(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
