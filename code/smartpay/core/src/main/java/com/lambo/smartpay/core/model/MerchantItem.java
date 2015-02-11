package com.lambo.smartpay.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商户系统交易定义表
 * 
 * @author swang
 *
 */
@Entity
@Table(name = "MER_ITEM")
public class MerchantItem implements Serializable {
	private static final long serialVersionUID = -1267639507465328987L;
	@Id
	@Column(name = "BDF_BSNCODE")
	private String bsnCode; // 菜单ID
	@Column(name = "BDF_MENUNO")
	private String menuNo; // 交易代码
	@Column(name = "BDF_NAME")
	private String name; // 交易名称
	@Column(name = "BDF_ALIAS")
	private String alias; // 交易别名
	@Column(name = "BDF_DESC")
	private String description; // 交易描述
	@Column(name = "BDF_TYPE")
	private String type; // 交易类型（0:查询；1:支付；2：管理）
	@Column(name = "BDF_BSNLV")
	private String level; // 交易级别（0：默认开通交易；1：需申请交易）
	@Column(name = "BDF_GROUP")
	private String group; // 是否为集团客户专有交易（0：普通交易；1：集团交易）
	@Column(name = "BDF_USERLV")
	private String userLevel; // 用户级别（0：密码用户专有；1：证书用户专有；2：公有）
	@Column(name = "BDF_STT")
	private String status; // 交易状态（0：正常；1：冻结）
	@Column(name = "BDF_URL")
	private String url; // 交易菜单

    public String getBsnCode() {
        return bsnCode;
    }

    public void setBsnCodeCode(String bsnCode) {
        this.bsnCode = bsnCode;
    }

    public String getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
