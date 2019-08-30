package com.eiv.convertidor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;

public class Conversor {

    private Tasa tasaOrigen;
    private Tasa tasaDestino;
    
    public Conversor() {
        this.tasaOrigen = new Tasa();
        this.tasaDestino = new Tasa();
    }

    public Conversor(Tasa tasaOrigen) {
        this();
        this.tasaOrigen = tasaOrigen;
    }

    public Tasa getTasaOrigen() {
        return tasaOrigen;
    }

    public Conversor datosIniciales(Consumer<Tasa> consumer) {
        consumer.accept(tasaOrigen);
        return this;
    }
    
    public Conversor calcular(TipoTasaEnum tipoTasaDestino, Long moduloDestino, Long dias) {
        
        // PASO 1 - CALCULAR LA RAZON
        BigDecimal razon = CalculadoraRazonFactory
                .create(tasaOrigen.getTipo())
                .calcular(tasaOrigen, dias);

        // PASO 1 - CALCULAR LA TASA DESTINO
        BigDecimal tasa = CalculadoraTasaFactory
                .create(tipoTasaDestino)
                .calcular(razon, moduloDestino, dias);
        
        this.tasaDestino.setValor(tasa);
        this.tasaDestino.setModulo(moduloDestino);
        this.tasaDestino.setTipo(tipoTasaDestino);
        
        return this;
    }
    
    public Optional<Tasa> resultado() {
        return tasaDestino == null ? Optional.empty() : Optional.of(tasaDestino);
    }
}
