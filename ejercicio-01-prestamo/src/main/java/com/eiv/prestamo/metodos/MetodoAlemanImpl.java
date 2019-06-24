package com.eiv.prestamo.metodos;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.eiv.prestamo.PrestamoDatos;

public class MetodoAlemanImpl implements Metodo {

    @Override
    public BigDecimal calculoValorCuota(PrestamoDatos prestamoDatos) {

        return prestamoDatos.getCapital().divide(
                BigDecimal.valueOf(prestamoDatos.getCuotas()),2, RoundingMode.HALF_UP);
        
        
    }

}
