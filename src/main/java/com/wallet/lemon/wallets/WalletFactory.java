package com.wallet.lemon.wallets;

import com.wallet.lemon.users.User;
import com.wallet.lemon.wallets.Constants.Currency;

public class WalletFactory {
    
    public Wallet getWallet(Currency currency, User user) {
        if (currency.equals(Currency.ARS)) {
            return new ArsWallet(user);
        } else if (currency.equals(Currency.BTC)) {
            return new BtcWallet(user);
        } else if (currency.equals(Currency.USDT)) {
            return new UsdtWallet(user);
        }
        return null;
    }
}
