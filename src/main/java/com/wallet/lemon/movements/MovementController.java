package com.wallet.lemon.movements;

import java.util.Objects;

import com.wallet.lemon.movements.payload.CreateMovementPayload;
import com.wallet.lemon.users.IUserService;
import com.wallet.lemon.wallets.IWalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/movements")
public class MovementController {
    
    @Autowired
    private IMovementeService movementService;

    @Autowired
    private IWalletService walletService;

    @Autowired
    private IUserService userService;

    @PostMapping(path = "")
    public ResponseEntity<Movement> create(@RequestBody CreateMovementPayload payload) {
        var user = userService.getByAlias(payload.getUserAlias());
        var wallet = walletService.getByUserAndType(user.getId(), payload.getCurrency());
        var movement = new Movement(payload.getType(), payload.getCurrency(), payload.getAmount(), user, wallet);
        movement = movementService.execute(movement);
        if (Objects.isNull(movement)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Movement>(movement, HttpStatus.OK);
    }
}
