package com.eiv.testing;

import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.Test;

public class RaizTest {

    @Test
    public void test1() {
        
        // x**2 + x - 1 = 0 -> x = 0.6180
        
        Polinomio polinomio = new Polinomio()
                .coeficientes(
                        BigDecimal.ONE.negate(), BigDecimal.ONE, BigDecimal.ONE);
        
        Raiz raiz = new Raiz(polinomio);
        BigDecimal resultado = raiz.calcular(BigDecimal.ZERO, BigDecimal.ONE);
        
        assertThat(resultado, BigDecimalCloseTo.closeTo(
                BigDecimal.valueOf(0.6180), BigDecimal.valueOf(0.001)));
    }
    
    @Test
    public void test2() {
        
        // 2 * x^3 - x^2 + x - 1 = 0 -> x = 0.7390
        
        Polinomio polinomio = new Polinomio()
                .coeficientes(
                        BigDecimal.ONE.negate(), 
                        BigDecimal.ONE, 
                        BigDecimal.ONE.negate(),
                        BigDecimal.ONE.add(BigDecimal.ONE));
        
        Raiz raiz = new Raiz(polinomio);
        BigDecimal resultado = raiz.calcular(BigDecimal.ZERO, BigDecimal.ONE);
        
        assertThat(resultado, BigDecimalCloseTo.closeTo(
                BigDecimal.valueOf(0.7390), BigDecimal.valueOf(0.001)));
    }
    
    @Test
    public void test3() {
        
        // x^13 - 1.106518 * x^12 + 0.106518 = 0 -> x = 1.0399
        
        Polinomio polinomio = new Polinomio()
                .coeficientes(BigDecimal.valueOf(0.106518))
                .coeficientes(BigDecimal.ZERO, 10)
                .coeficientes(BigDecimal.valueOf(1.106518).negate())
                .coeficientes(BigDecimal.ONE);
        
        Raiz raiz = new Raiz(polinomio);
        BigDecimal resultado = raiz.calcular(
                BigDecimal.ONE, 
                BigDecimal.ONE.add(BigDecimal.ONE));
                
        assertThat(resultado, BigDecimalCloseTo.closeTo(
                BigDecimal.valueOf(1.0399), BigDecimal.valueOf(0.001)));
    }
}
