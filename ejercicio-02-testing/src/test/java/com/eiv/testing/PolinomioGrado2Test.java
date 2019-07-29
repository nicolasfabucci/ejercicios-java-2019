package com.eiv.testing;

import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PolinomioGrado2Test {

    @Parameter(0) public BigDecimal variable = null;
    @Parameter(1) public BigDecimal esperado = null;

    @Parameters(name = "{index}: VARIABLE: {0}, RESULTADO: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { 
            { BigDecimal.ONE, BigDecimal.valueOf(3) },
            { BigDecimal.valueOf(2), BigDecimal.valueOf(7) },
            { BigDecimal.valueOf(3), BigDecimal.valueOf(13) }
        });
    }

    @Test
    public void givenP2_whenCoefsTodosUno_thenEvaluarPolinomio() {
        
        // x**2 + x + 1
        
        Polinomio polinomio = new Polinomio();
        
        BigDecimal resultado = polinomio
                .coeficientes(
                        BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE)
                .evaluar(variable);
        
        assertThat(resultado.compareTo(esperado), Is.is(0));        
    }
}
