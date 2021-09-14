package com.wallet.lemon.users;

public interface IUserService {
    User create(User user);
    User getByAlias(String alias);
    User getById(Integer id);
}
