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
public class PolinomioGrado13Test {

    @Parameter(0) public BigDecimal variable = null;
    @Parameter(1) public BigDecimal esperado = null;

    @Parameters(name = "{index}: VARIABLE: {0}, RESULTADO: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { 
            { BigDecimal.valueOf(1.039944), BigDecimal.valueOf(0.000000268) }
        });
    }

    @Test
    public void givenP1_whenCoefs_thenEvaluarPolinomio() {

        // x^13 - 1.106518 * x^12 + 0.106518
        
        Polinomio polinomio = new Polinomio()
                .setScale(9)
                .coeficientes(BigDecimal.valueOf(0.106518))
                .coeficientes(BigDecimal.ZERO, 10)
                .coeficientes(BigDecimal.valueOf(1.106518).negate())
                .coeficientes(BigDecimal.ONE);
        
        BigDecimal resultado = polinomio.evaluar(variable);
        
        assertThat(resultado.abs(), Is.is(esperado));
    }
}
