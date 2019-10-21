package com.eiv.convertidor;

import java.math.BigDecimal;

public interface CalculadoraTasa {

    public BigDecimal calcular(BigDecimal razon, long modulo, long dias);
}
