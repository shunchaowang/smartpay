package com.lambo.smartpay.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户关联账号
 * @author swang
 *
 */
@Entity
@Table(name = "MER_USER_ACC")
public class MerchantUserAccount implements java.io.Serializable {
	private static final long serialVersionUID = -2963630011918125197L;
	@Id
	@Column(name="MUC_ACCNO")
	private String accountNo;	//账号 主键
	@Column(name="MUC_USERID")
	private String userId;	//用户标识：客户号+用户编号 主键

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
