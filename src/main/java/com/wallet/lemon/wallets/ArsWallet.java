package com.wallet.lemon.wallets;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.wallet.lemon.users.User;
import com.wallet.lemon.wallets.Constants.Currency;

@Entity
public class ArsWallet extends Wallet {

    public ArsWallet(User user) {
        super(Currency.ARS, new BigDecimal(0), user, 2);
    }

    public ArsWallet() {}
}
