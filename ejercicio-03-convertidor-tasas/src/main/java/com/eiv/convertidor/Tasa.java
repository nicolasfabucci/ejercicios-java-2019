package com.eiv.convertidor;

import java.math.BigDecimal;

public class Tasa {

    private BigDecimal valor;
    private TipoTasaEnum tipo;
    private Long modulo;
    
    public Tasa() {
    }

    public Tasa(BigDecimal valor, TipoTasaEnum tipo, Long modulo) {
        super();
        this.valor = valor;
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoTasaEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoTasaEnum tipo) {
        this.tipo = tipo;
    }

    public Long getModulo() {
        return modulo;
    }

    public void setModulo(Long modulo) {
        this.modulo = modulo;
    }

    @Override
    public String toString() {
        return "Tasa [valor=" + valor + ", tipo=" + tipo + ", modulo=" + modulo + "]";
    }
}
