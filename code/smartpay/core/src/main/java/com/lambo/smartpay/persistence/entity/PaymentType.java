package com.lambo.smartpay.persistence.entity;

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
 * Domain class for the type of the payment.
 * <p/>
 * 100: Credit Card
 * 101: Debit Card
 * <p/>
 * Created by swang on 2/17/2015.
 */
@Entity
@Table(name = "PAYMENT_TYPES")
public class PaymentType implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PYTP_ID")
    private Long id;

    @Column(name = "PYTP_NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "PYTP_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "PYTP_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "PYTP_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "paymentType")
    private Set<Payment> payments;

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

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
}
