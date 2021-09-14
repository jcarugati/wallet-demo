package com.wallet.lemon.wallets;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wallet.lemon.users.User;
import com.wallet.lemon.wallets.Constants.Currency;

@Entity
@Table(name = "wallets")
@JsonIgnoreProperties( value = { "scale" })
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Currency type;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(precision = 19, scale = 10)
    private BigDecimal balance;

    @Transient
    private int scale;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(Currency type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance.setScale(this.scale, RoundingMode.HALF_EVEN);
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance.setScale(this.scale, RoundingMode.HALF_EVEN);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setScale(int scale) {
        this.scale = scale;
        balance.setScale(this.scale, RoundingMode.HALF_EVEN);
    }

    public int getScale() {
        return scale;
    }

    public Wallet(Currency type, BigDecimal balance, User user) {
        this.type = type;
        this.user = user;
        this.scale = 2;
        this.balance = balance.setScale(this.scale, RoundingMode.HALF_EVEN);
    }

    public Wallet(Currency type, BigDecimal balance, User user, int scale) {
        this.type = type;
        this.user = user;
        this.scale = scale;
        this.balance = balance.setScale(this.scale, RoundingMode.HALF_EVEN);
    }

    public Wallet() {}
}
