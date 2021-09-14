package com.wallet.lemon.wallets;

import com.wallet.lemon.wallets.Constants.Currency;

import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, Integer> {
    Wallet findByUserIdAndType(Integer userId, Currency type);
}
