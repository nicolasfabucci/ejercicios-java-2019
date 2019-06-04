package com.eiv.prestamo.metodos;

import java.math.BigDecimal;

import com.eiv.prestamo.PrestamoDatos;

public interface Metodo {

    public BigDecimal calculoValorCuota(PrestamoDatos prestamoDatos);
}
