package com.eiv.convertidor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraRazonDesdeTnv implements CalculadoraRazon {

    @Override
    public BigDecimal calcular(Tasa tasa, long dias) {
        
        BigDecimal razon = tasa.getValor().multiply(BigDecimal.valueOf(dias))
                .divide(BigDecimal.TEN.multiply(BigDecimal.TEN))
                .divide(BigDecimal.valueOf(tasa.getModulo()), 8, RoundingMode.HALF_UP);
        
        return razon;
    }

}
