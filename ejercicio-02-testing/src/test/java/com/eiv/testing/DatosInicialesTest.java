package com.eiv.testing;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

public class DatosInicialesTest {

    @Test
    public void test01() {
        
        DatosIniciales datosIniciales = new DatosIniciales();
        assertNotNull(datosIniciales);
    }
    
    @Test
    public void test02() {
        
        DatosIniciales datosIniciales = DatosIniciales.of(
                BigDecimal.ONE, BigDecimal.ONE, Integer.valueOf(0));
        assertNotNull(datosIniciales);
    }
}
