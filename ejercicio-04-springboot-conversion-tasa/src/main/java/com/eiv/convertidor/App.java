package com.eiv.convertidor;

import java.math.BigDecimal;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.eiv.convertidor.utiles.Asserts;

@SpringBootApplication
public class App implements CommandLineRunner {
    
    private static final Logger LOG = LogManager.getLogger(App.class);
    private static final Scanner SCANNER = new Scanner(System.in);
    
    @Autowired private Conversor conversor;
    
    private TipoTasaEnum tipoTasaDestino;
    private Long moduloDestino;
    private Long dias;
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
                
        // CARGA DE DATOS INCIALES ...
        datosIniciales();

        // CARGA DE DATOS PARA CONVERTIR LA TASA ...
        datosSalida();
        
        // REALIZAR LOS CALCULOS ...
        calcular();
    }
    
    private void calcular() {
        
        conversor
                .calcular(tipoTasaDestino, moduloDestino, dias)
                .resultado()
                .ifPresent(tasa -> {
                    System.out.println("TASA DESTINO: " + tasa);
                });
        
    }
    
    private void datosSalida() {

        System.out.println("Tipo de tasa a convertir "
                + "[1 -> EFECTIVA | 2 -> NOMINAL ADELANTADA - | 3 -> NOMINAL VENCIDA]: ");
        int i = SCANNER.nextInt();
        final TipoTasaEnum tipoTasaEnum = TipoTasaEnum.of(i);
        
        System.out.println("Modulo de expresion de la tasa: ");
        final Long modulo = SCANNER.nextLong();
        Asserts.isPositive(
                modulo, "El modulo de la tasa no puede ser nulo!");
        
        System.out.println("Dias del periodo: ");
        final Long dias = SCANNER.nextLong();
        Asserts.isPositive(
                dias, "Los dias del periodo de amortizacion debe ser un numero positivo!");

        this.tipoTasaDestino = tipoTasaEnum;
        this.moduloDestino = modulo;
        this.dias = dias;
    }
    
    private void datosIniciales() {

        System.out.println("Ingrese el valor porcentual de la tasa: ");
        BigDecimal valor = SCANNER.nextBigDecimal();
        
        System.out.println("Tipo de tasa ingresada "
                + "[1 -> EFECTIVA | 2 -> NOMINAL ADELANTADA | 3 -> NOMINAL VENCIDA]: ");
        TipoTasaEnum tipoTasaEnum = TipoTasaEnum.of(SCANNER.nextInt());
        
        System.out.println("Modulo de expresion de la tasa: ");
        Long modulo = SCANNER.nextLong();

        conversor.datosIniciales(tasa -> {
            tasa.setModulo(modulo);
            tasa.setTipo(tipoTasaEnum);
            tasa.setValor(valor);
        });
        
        LOG.info("Tasa cargada: {}", conversor.getTasaOrigen());
    }
}
