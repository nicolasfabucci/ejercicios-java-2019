package com.eiv.convertidor;

import java.math.BigDecimal;

public interface CalculadoraRazon {

    public BigDecimal calcular(Tasa tasa, long dias);
}
