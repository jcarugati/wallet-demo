package com.wallet.lemon.wallets;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.wallet.lemon.users.User;
import com.wallet.lemon.wallets.Constants.Currency;

@Entity
public class BtcWallet extends Wallet{
    public BtcWallet(User user) {
        super(Currency.BTC, new BigDecimal(0), user, 8);
    }

    public BtcWallet() {}
}
