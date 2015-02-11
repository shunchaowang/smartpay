package com.lambo.smartpay.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "pub_code_rule")
public class CodeRule {

	@Id
	@GeneratedValue(generator = "CodeRuleSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "CodeRuleSeq", sequenceName = "code_rule_seq", initialValue = 1, allocationSize = 1)
	private Integer id;
	@Column(name = "rule_type", length = 20)
	private String ruleType; // 规则类型
	@Column(name = "prefix", length = 20, nullable = true)
	private String prefix; // 前缀
	@Column(name = "fill", length = 10, nullable = true)
	private String fill; // 中间填充值
	@Column(name = "seq", nullable = false)
	private Integer seq; // 序列号
	@Version
	private Integer version;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
