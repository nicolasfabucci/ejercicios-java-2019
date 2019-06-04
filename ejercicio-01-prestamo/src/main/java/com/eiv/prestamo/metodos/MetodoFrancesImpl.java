package com.eiv.prestamo.metodos;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.eiv.prestamo.App;
import com.eiv.prestamo.PrestamoDatos;

public class MetodoFrancesImpl implements Metodo {
    
    @Override
    public BigDecimal calculoValorCuota(PrestamoDatos prestamoDatos) {

        BigDecimal razon = prestamoDatos.getTna()
                .multiply(App.DIAS)
                .divide(App.CIEN.multiply(App.DIAS_ANIO), 6, RoundingMode.HALF_UP);
        
        BigDecimal f = razon.add(BigDecimal.ONE).pow(prestamoDatos.getCuotas());
        
        BigDecimal valorCuota = prestamoDatos.getCapital()
                .multiply(razon)
                .multiply(f)
                .divide(f.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
        
        return valorCuota;
    }

}