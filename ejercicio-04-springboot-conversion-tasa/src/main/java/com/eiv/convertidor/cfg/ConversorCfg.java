package com.eiv.convertidor.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eiv.convertidor.Conversor;

@Configuration
public class ConversorCfg {

    @Bean
    public Conversor getConversor() {
        return new Conversor();
    }
}
