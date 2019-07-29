package com.eiv.testing;

import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.eiv.testing.PolinomioTest.BigDecimalEquals;

@RunWith(Parameterized.class)
public class PolinomioGrado3Test {

    @Parameter(0) public BigDecimal variable = null;
    @Parameter(1) public BigDecimal esperado = null;

    @Parameters(name = "{index}: VARIABLE: {0}, RESULTADO: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { 
            { BigDecimal.ONE, BigDecimal.valueOf(4) },
            { BigDecimal.valueOf(2), BigDecimal.valueOf(15) },
            { BigDecimal.valueOf(3), BigDecimal.valueOf(40) }
        });
    }

    @Test
    public void givenP2_whenCoefsTodosUno_thenEvaluarPolinomio() {
        
        // x**3 + x**2 + x + 1
        
        Polinomio polinomio = new Polinomio();
        
        BigDecimal resultado = polinomio
                .coeficientes(
                        BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE)
                .evaluar(variable);
        
        assertThat(resultado, BigDecimalEquals.areEquals(esperado));        
    }
    
}
