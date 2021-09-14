package com.wallet.lemon.movements;

import java.util.Arrays;
import java.util.List;

public class Constants {
    
    public enum MovementTypes {
        DEPOSIT,
        EXTRACTION
    }

    final static public List<MovementTypes> MOVEMENT_TYPES = Arrays.asList(MovementTypes.DEPOSIT, MovementTypes.EXTRACTION);
}
