package com.wallet.lemon.movements;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wallet.lemon.movements.Constants.MovementTypes;
import com.wallet.lemon.users.User;
import com.wallet.lemon.wallets.Wallet;
import com.wallet.lemon.wallets.Constants.Currency;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "movements")
@JsonIgnoreProperties(value = { "user", "wallet" })
public class Movement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private MovementTypes type;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(precision = 19, scale = 10)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false, updatable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Wallet wallet;

    @CreationTimestamp
    @Column
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime created;

    public Movement(MovementTypes type, Currency currency, BigDecimal amount, User user, Wallet wallet) {
        this.type = type;
        this.currency = currency;
        this.amount = amount;
        this.user = user;
        this.wallet = wallet;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public User getUser() {
        return user;
    }

    public MovementTypes getMovementType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    
    public Currency getCurrency() {
        return currency;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Movement() {}
}
