package com.orobind.core.entity;

// Generated Mar 30, 2014 10:59:42 AM by Hibernate Tools 3.3.0.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PaymentModeSubtypeMapping generated by hbm2java
 */
@Entity
@Table(name = "payment_mode_subtype_mapping", catalog = "orobind")
public class PaymentModeSubtypeMapping implements java.io.Serializable {

    private static final long serialVersionUID = 1412826132034037258L;

    private Integer            id;
    private PaymentModeSubtype paymentModeSubtype;
    private PaymentGateway     paymentGateway;
    private String             pgValue;

    public PaymentModeSubtypeMapping() {
    }

    public PaymentModeSubtypeMapping(PaymentModeSubtype paymentModeSubtype, PaymentGateway paymentGateway, String pgValue) {
        this.paymentModeSubtype = paymentModeSubtype;
        this.paymentGateway = paymentGateway;
        this.pgValue = pgValue;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_mode_subtype_id", nullable = false)
    public PaymentModeSubtype getPaymentModeSubtype() {
        return this.paymentModeSubtype;
    }

    public void setPaymentModeSubtype(PaymentModeSubtype paymentModeSubtype) {
        this.paymentModeSubtype = paymentModeSubtype;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_gateway_id", nullable = false)
    public PaymentGateway getPaymentGateway() {
        return this.paymentGateway;
    }

    public void setPaymentGateway(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Column(name = "pg_value", nullable = false, length = 48)
    public String getPgValue() {
        return this.pgValue;
    }

    public void setPgValue(String pgValue) {
        this.pgValue = pgValue;
    }

}
