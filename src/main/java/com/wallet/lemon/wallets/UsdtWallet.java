package com.wallet.lemon.wallets;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.wallet.lemon.users.User;
import com.wallet.lemon.wallets.Constants.Currency;

@Entity
public class UsdtWallet extends Wallet{
    public UsdtWallet(User user) {
        super(Currency.USDT, new BigDecimal(0), user, 2);
    }

    public UsdtWallet() {}
}
