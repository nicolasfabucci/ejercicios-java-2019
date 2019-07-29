package com.eiv.testing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Polinomio {

    private static final int DEFAULT_SCALE = 6;
    
    private List<BigDecimal> coeficientes;
    private int scale = DEFAULT_SCALE;
    
    public Polinomio() {
        this.coeficientes = new ArrayList<BigDecimal>();
    }
    
    public Polinomio coeficientes(BigDecimal... coeficientes) {
        for (int i = 0; i < coeficientes.length; i++) {
            this.coeficientes.add(coeficientes[i]);
        }
        return this;
    }
    
    public Polinomio coeficientes(BigDecimal coeficiente) {
        this.coeficientes.add(coeficiente);
        return this;
    }
    
    public Polinomio coeficientes(BigDecimal coeficiente, int n) {
        for (int i = 0; i < n + 1; i++) {
            this.coeficientes.add(coeficiente);
        }
        return this;
    }
    
    public Polinomio setScale(int scale) {
        this.scale = scale;
        return this;
    }

    public List<BigDecimal> getCoeficientes() {
        return coeficientes;
    }
    
    public BigDecimal evaluar(final BigDecimal valor) {
        
        BigDecimal resultado = BigDecimal.ZERO;
        int exponente = 0;
        
        for (BigDecimal coeficiente : coeficientes) {
            if (coeficiente.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal potencia = valor.pow(exponente);
                BigDecimal multiplicacion = coeficiente.multiply(potencia);
                resultado = resultado.add(multiplicacion);
            }
            exponente += 1;
        }
        
        return resultado.setScale(scale, RoundingMode.HALF_UP);
    }
}
