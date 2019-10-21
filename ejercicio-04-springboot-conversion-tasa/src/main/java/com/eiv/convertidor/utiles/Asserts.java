package com.eiv.convertidor.utiles;

import java.math.BigDecimal;

public abstract class Asserts {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isPositive(BigDecimal number, String message) {
        if (number == null || number.compareTo(BigDecimal.ZERO) != 1) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isPositive(Long number, String message) {
        if (number == null || number.compareTo(Long.valueOf(0)) < 1) {
            throw new IllegalArgumentException(message);
        }
    }
}
