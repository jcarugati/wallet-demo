package com.wallet.lemon.movements;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;
import java.util.List;

import javax.transaction.Transactional;

import com.wallet.lemon.movements.Constants.MovementTypes;
import com.wallet.lemon.wallets.IWalletService;
import com.wallet.lemon.wallets.WalletRepository;
import com.wallet.lemon.wallets.Constants.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovementService implements IMovementeService{

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private IWalletService walletService;

    @Transactional
    public Movement execute(Movement movement) {
        var wallet = walletService.injectScale(movement.getWallet());

        movement = injectScale(movement);

        var updatedBalance = calculateBalance(wallet.getBalance(), movement.getAmount(), movement.getMovementType());
        if (updatedBalance.compareTo(BigDecimal.ZERO) < 0 ) {
            return null;
        }

        wallet.setBalance(updatedBalance);
        walletRepository.save(wallet);

        movementRepository.save(movement);
        return movement;
    }

    private BigDecimal calculateBalance(BigDecimal prevBalance, BigDecimal movementAmount, MovementTypes operation) {
        if (operation.equals(MovementTypes.DEPOSIT)) {
            return prevBalance.add(movementAmount);
        }
        return prevBalance.subtract(movementAmount);
    }

    private Movement injectScale(Movement movement) {
        var type = movement.getCurrency();
        if (type.equals(Currency.ARS)) {
            movement.setAmount(movement.getAmount().setScale(2, RoundingMode.HALF_EVEN));
            return movement;
        }
        if (type.equals(Currency.BTC)) {
            movement.setAmount(movement.getAmount().setScale(8, RoundingMode.HALF_EVEN));
            return movement;
        } else {
            movement.setAmount(movement.getAmount().setScale(2, RoundingMode.HALF_EVEN));
            return movement;
        }
    }

    public Page<Movement> getMovementsForUser(List<String> types, Integer userId, List<String> currencies, Pageable pageable) {
        var movements = movementRepository.findByMovementTypesAndUserId(types, userId, currencies)
                    .stream()
                    .map(mapper -> injectScale(mapper))
                    .collect(Collectors.toList());
        var start = (int) pageable.getOffset();
        var end = Math.min((start + pageable.getPageSize()), movements.size());
        if (start <= end) {
            movements = movements.subList(start, end);
        }
        return new PageImpl<>(movements, pageable, movements.size());
    }
}
