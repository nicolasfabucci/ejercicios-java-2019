package com.eiv.testing;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraTasa {

    private DatosIniciales datosIniciales;
    
    private BigDecimal razon;
    private BigDecimal tea;
    private BigDecimal tna;
    
    public CalculadoraTasa() {
    }
    
    public CalculadoraTasa datos(double capital, double valorCuota, int cuotas) {
        this.datosIniciales = DatosIniciales.of(capital, valorCuota, cuotas);
        return this;
    }
    
    public CalculadoraTasa calcular() {
        
        BigDecimal termIndep = datosIniciales.getValorCuota().divide(
                datosIniciales.getCapital(), 6, RoundingMode.HALF_UP);
        
        Polinomio polinomio = new Polinomio()
                .setScale(9)
                .coeficientes(termIndep)
                .coeficientes(BigDecimal.ZERO, datosIniciales.getCuotas() - 2)
                .coeficientes(termIndep.add(BigDecimal.ONE).negate())
                .coeficientes(BigDecimal.ONE);
        
        Raiz raiz = new Raiz(polinomio);
        BigDecimal resultado = raiz.calcular(
                BigDecimal.ONE.add(BigDecimal.valueOf(0.0001)), 
                BigDecimal.ONE.add(BigDecimal.ONE));
        
        this.razon = resultado.subtract(BigDecimal.ONE);
        
        return this;
    }

    public BigDecimal getTea() {
        
        int n = datosIniciales.getCuotas();
        BigDecimal tea = razon.add(BigDecimal.ONE).pow(n).subtract(BigDecimal.ONE).multiply(
                BigDecimal.TEN.multiply(BigDecimal.TEN));
        
        this.tea = tea.setScale(2, RoundingMode.HALF_UP);
        
        return this.tea;
    }

    public BigDecimal getTna() {
        this.tna = razon.multiply(
                BigDecimal.valueOf(36000).divide(
                        BigDecimal.valueOf(30),  2, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);
        
        return tna;
    }
    
}
