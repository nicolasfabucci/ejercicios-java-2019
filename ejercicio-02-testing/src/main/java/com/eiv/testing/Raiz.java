package com.eiv.testing;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Raiz {

    private static final BigDecimal ERROR_ACEPTABLE;
    
    private Polinomio polinomio;
    private BigDecimal mejorEstimacion = null;
    
    static {
        ERROR_ACEPTABLE = BigDecimal.valueOf(0.0000001);
    }
    
    public Raiz(Polinomio polinomio) {
        super();
        this.polinomio = polinomio.setScale(ERROR_ACEPTABLE.scale());
    }
    
    public BigDecimal calcular(
            final BigDecimal cotaInferior, final BigDecimal cotaSuperior) {
        
        BigDecimal inferior = cotaInferior;
        BigDecimal superior = cotaSuperior;
        BigDecimal medio = puntoMedio(inferior, superior);
        
        BigDecimal polInf = polinomio.evaluar(inferior);
        BigDecimal polSup = polinomio.evaluar(superior);
        BigDecimal polMed = polinomio.evaluar(medio);
        
        if (polMed.abs().compareTo(ERROR_ACEPTABLE) != 1) {
            this.mejorEstimacion = medio;
            return medio;
        }
        
        boolean esCandidato = polInf.multiply(polMed).compareTo(BigDecimal.ZERO) == -1;
        
        if (esCandidato) {
            calcular(inferior, medio);
        } else {
            esCandidato = polMed.multiply(polSup).compareTo(BigDecimal.ZERO) == -1;
            
            if (esCandidato) {
                calcular(medio, superior);
            }
        }
        
        return mejorEstimacion == null ? medio : mejorEstimacion;
    }
    
    private BigDecimal puntoMedio(BigDecimal punto1, BigDecimal punto2) {
        return punto2
                .subtract(punto1)
                .divide(
                        BigDecimal.ONE.add(BigDecimal.ONE), 
                        ERROR_ACEPTABLE.scale(), 
                        RoundingMode.HALF_UP)
                .abs()
                .add(punto1);
    }
}
