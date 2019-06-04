package com.eiv.prestamo;

import java.math.BigDecimal;

import com.eiv.prestamo.utiles.Asserts;

public class PrestamoDatos {

    private BigDecimal capital;
    private Integer cuotas;
    private BigDecimal tna;
    
    public PrestamoDatos() {
    }

    public PrestamoDatos(BigDecimal capital, Integer cuotas, BigDecimal tna) {
        super();
        this.capital = capital;
        this.cuotas = cuotas;
        this.tna = tna;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    public BigDecimal getTna() {
        return tna;
    }

    public void setTna(BigDecimal tna) {
        this.tna = tna;
    }

    @Override
    public String toString() {
        return "PrestamoDatos [capital=" + capital + ", cuotas=" + cuotas + ", tna=" + tna + "]";
    }
    
    public static void esValido(PrestamoDatos prestamoDatos) {
        
        Asserts.notNull(prestamoDatos, "No hay datos para validar!");
        
        Asserts.notNull(prestamoDatos.getCapital(), "El CAPITAL no puede ser nulo!");
        Asserts.notNull(prestamoDatos.getCuotas(), "La cantidad de CUOTAS no puede ser nula!");
        Asserts.notNull(prestamoDatos.getTna(), "La TASA no puede ser nula!");
        
        Asserts.isPositive(prestamoDatos.getCapital(), "El CAPITAL debe ser un numero positivo!");
        Asserts.isPositive(prestamoDatos.getCuotas(), "Debe haber al menos una cuota!");
        Asserts.isPositive(prestamoDatos.getTna(), "La TASA debe ser un numero positivo!");
    }
}
