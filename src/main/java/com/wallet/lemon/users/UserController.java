package com.wallet.lemon.users;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import com.wallet.lemon.movements.IMovementeService;
import com.wallet.lemon.movements.Movement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMovementeService movementService;
    
    @PostMapping(path = "")
    public User create(@RequestBody User u) {
        var user = userService.create(u);
        return user;
    }
    
    @GetMapping(path = { "/{id}", ""})
    public User index(@PathVariable("id") Optional<Integer> userId, @RequestParam Optional<String> alias) {
        if (userId.isEmpty() && alias.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "neither id or alias were provided");
        }
        User user;
        try {
            if (userId.isPresent()) {
            user = userService.getById(userId.get());
            } else {
                user = userService.getByAlias(alias.get());
            }
            return user; 
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "could not find user");
        }
    }

    @GetMapping(path = "/{id}/movements")
    public ResponseEntity<Page<Movement>> movements (
            @PathVariable("id") Integer userId,
            // @RequestParam(defaultValue = "100") Integer limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "500") int size,
            @RequestParam(name = "currency", defaultValue = "ARS,BTC,USDT") String currency,
            @RequestParam(name = "type", defaultValue = "DEPOSIT,EXTRACTION") String typeString) {
        List<String> types = Arrays.asList(typeString.toUpperCase().split(","));
        List<String> currencies = Arrays.asList(currency.toUpperCase().split(","));
        var movements = movementService.getMovementsForUser(types, userId, currencies, PageRequest.of(offset, size));
        return new ResponseEntity<Page<Movement>>(movements, HttpStatus.OK);
    }
}
