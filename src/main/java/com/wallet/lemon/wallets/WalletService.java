package com.wallet.lemon.wallets;

import java.util.ArrayList;
import java.util.List;

import com.wallet.lemon.users.User;
import com.wallet.lemon.wallets.Constants.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService  implements IWalletService {

    @Autowired
    private WalletRepository walletRepository;
    
    public List<Wallet> generateInitialWallets(User user) {
        var wallets = new ArrayList<Wallet>();
        wallets.add(new ArsWallet(user));
        wallets.add(new UsdtWallet(user));
        wallets.add(new BtcWallet(user));
        return wallets;
    }

    public Wallet getByUserAndType(Integer userId, Currency type) {
        return walletRepository.findByUserIdAndType(userId, type);
    }

    public Wallet injectScale(Wallet wallet) {
        var type = wallet.getType();
        if (type.equals(Currency.ARS.toString())) {
            wallet.setScale(2);
            return wallet;
        }
        if (type.equals(Currency.BTC.toString())) {
            wallet.setScale(8);
            return wallet;
        } else {
            wallet.setScale(2);
            return wallet;
        }
    }
}
