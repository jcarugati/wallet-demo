package com.wallet.lemon.movements.payload;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallet.lemon.movements.Constants.MovementTypes;
import com.wallet.lemon.wallets.Constants.Currency;

public class CreateMovementPayload {

    @Valid
    @JsonProperty("type")
    private MovementTypes type;

    @Valid
    @JsonProperty("currency")
    private Currency currency;

    @Valid
    @JsonProperty("amount")
    private BigDecimal amount;

    @NotBlank
    @JsonProperty("user_alias")
    private String userAlias;

    @NotBlank
    @JsonProperty("wallet_id")
    private Integer walletId;

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public MovementTypes getType() {
        return type;
    }

    public Integer getWalletId() {
        return walletId;
    }

}
