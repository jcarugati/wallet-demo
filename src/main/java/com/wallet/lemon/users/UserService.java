package com.wallet.lemon.users;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.wallet.lemon.wallets.IWalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IWalletService walletService;

    @Transactional
    public User create(User user) {
        user.setWallets(walletService.generateInitialWallets(user));
        userRepository.save(user);
        return user;
    }

    public User getByAlias(String alias) {
        var user = userRepository.findByAlias(alias);
        var wallets = user.getWallets()
                        .stream()
                        .map(wallet -> walletService.injectScale(wallet))
                        .collect(Collectors.toList());
        user.setWallets(wallets);
        return user;
    }

    public User getById(Integer id) {
        return userRepository.findById(id).get();
    }
}
