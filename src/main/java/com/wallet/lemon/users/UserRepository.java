package com.wallet.lemon.users;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByAlias(String alias);
    
}
