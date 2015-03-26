package com.lambo.smartpay.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "RETURN_STATUSES")
public class ReturnStatus implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RTST_ID")
    private Long id;

    @Column(name = "RTST_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "RTST_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "RTST_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "RTST_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "returnStatus")
    private Set<Return> returns;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Return> getReturns() {
        return returns;
    }

    public void setReturns(Set<Return> returns) {
        this.returns = returns;
    }
}
