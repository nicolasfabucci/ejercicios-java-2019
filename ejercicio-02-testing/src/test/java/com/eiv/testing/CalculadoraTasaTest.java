package com.eiv.testing;

import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.Test;

public class CalculadoraTasaTest {

    @Test
    public void whenDatosIniciales_thenTea() {
        
        CalculadoraTasa calculadoraTasa = new CalculadoraTasa()
                .datos(100000, 10651.76, 12)
                .calcular();
        
        BigDecimal teaResultado = calculadoraTasa.getTea();
        BigDecimal teaEsperada = BigDecimal.valueOf(60);

        assertThat(teaResultado, BigDecimalCloseTo.closeTo(
                teaEsperada, BigDecimal.valueOf(0.1)));
    }
    
    @Test
    public void whenDatosIniciales_thenTna() {
        
        CalculadoraTasa calculadoraTasa = new CalculadoraTasa()
                .datos(100000, 10651.76, 12)
                .calcular();
        
        BigDecimal tnaResultado = calculadoraTasa.getTna();
        BigDecimal tnaEsperada = BigDecimal.valueOf(47.93);

        assertThat(tnaResultado, BigDecimalCloseTo.closeTo(
                tnaEsperada, BigDecimal.valueOf(0.1)));
    }
}
