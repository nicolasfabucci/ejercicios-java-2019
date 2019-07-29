package com.eiv.testing;

import java.math.BigDecimal;

public class DatosIniciales {

    private BigDecimal capital;
    private BigDecimal valorCuota;
    private Integer cuotas;
    
    public DatosIniciales() {
    }

    public DatosIniciales(BigDecimal capital, BigDecimal valorCuota, Integer cuotas) {
        super();
        this.capital = capital;
        this.valorCuota = valorCuota;
        this.cuotas = cuotas;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(BigDecimal valorCuota) {
        this.valorCuota = valorCuota;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    @Override
    public String toString() {
        return "DatosIniciales [capital=" + capital + ", valorCuota=" + valorCuota 
                + ", cuotas=" + cuotas + "]";
    }

    public static DatosIniciales of(BigDecimal capital, BigDecimal valorCuota, Integer cuotas) {
        return new DatosIniciales(capital, valorCuota, cuotas);
    }
    
    public static DatosIniciales of(double capital, double valorCuota, int cuotas) {
        return new DatosIniciales(
                BigDecimal.valueOf(capital), 
                BigDecimal.valueOf(valorCuota), 
                cuotas);
    }
}
