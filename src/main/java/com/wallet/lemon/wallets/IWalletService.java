package com.wallet.lemon.wallets;

import java.util.List;

import com.wallet.lemon.users.User;
import com.wallet.lemon.wallets.Constants.Currency;

public interface IWalletService {
    
    List<Wallet> generateInitialWallets(User user);
    Wallet getByUserAndType(Integer userId, Currency type);
    Wallet injectScale(Wallet wallet);
}
