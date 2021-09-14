package com.wallet.lemon.movements;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IMovementeService {
    Movement execute(Movement movement);
    Page<Movement> getMovementsForUser(List<String> types, Integer userId, List<String> currencies, Pageable page);
}
