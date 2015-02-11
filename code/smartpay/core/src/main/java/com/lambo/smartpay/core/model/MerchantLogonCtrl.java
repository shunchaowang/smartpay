package com.lambo.smartpay.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户登陆控制
 * 
 * @author swang
 *
 */
@Entity
@Table(name = "MER_LOGON_CTRL")
public class MerchantLogonCtrl implements java.io.Serializable {
	private static final long serialVersionUID = -315507593559522072L;
	@Id
	@Column(name = "LCL_USERID")
	private String userId;// 用户标识：客户号+用户编号 主键
	@Column(name = "LCL_FIRSTLOGON")
	private String firstLogon;// 首次登陆时间，格式为YYYYMMDDHHMISS
	@Column(name = "LCL_LASTLOGON")
	private String lastLogon;// 上次成功登陆时间，格式为YYYYMMDDHHMISS
	@Column(name = "LCL_LASTFAIL")
	private String lastFail;// 上次登陆失败日期，格式为YYYYMMDD
	@Column(name = "LCL_FAILTODAY")
	private Long failToday;// 当日连续登陆失败次数默认值：0
	@Column(name = "LCL_COUNT")
	private Long count;// 总成功登陆次数默认值：0
	@Column(name = "LCL_FREEZEDATE")
	private String frozenDate;// 冻结日期，格式为YYYYMMDD
	@Column(name = "LCL_NEEDCAPTCHA")
	private String needCaptcha;// 是否需要校验验证码，默认值：0( 0：不需要 1:需要)

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstLogon() {
        return firstLogon;
    }

    public void setFirstLogon(String firstLogon) {
        this.firstLogon = firstLogon;
    }

    public String getLastLogon() {
        return lastLogon;
    }

    public void setLastLogon(String lastLogon) {
        this.lastLogon = lastLogon;
    }

    public String getLastFail() {
        return lastFail;
    }

    public void setLastFail(String lastFail) {
        this.lastFail = lastFail;
    }

    public Long getFailToday() {
        return failToday;
    }

    public void setFailToday(Long failToday) {
        this.failToday = failToday;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getFrozenDate() {
        return frozenDate;
    }

    public void setFrozenDate(String frozenDate) {
        this.frozenDate = frozenDate;
    }

    public String getNeedCaptcha() {
        return needCaptcha;
    }

    public void setNeedCaptcha(String needCaptcha) {
        this.needCaptcha = needCaptcha;
    }
}
