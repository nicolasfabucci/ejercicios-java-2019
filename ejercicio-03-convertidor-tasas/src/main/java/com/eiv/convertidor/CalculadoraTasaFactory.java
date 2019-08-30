package com.eiv.convertidor;

public class CalculadoraTasaFactory {

    public static CalculadoraTasa create(TipoTasaEnum tipoTasaEnum) {
        switch (tipoTasaEnum) {
            case TE:
                return new CalculadoraTe();
            case TNV:
                return new CalculadoraTnv();
            case TNA:
                return new CalculadoraTna();
            default:
                throw new IllegalArgumentException(
                        "La calculadora para el tipo de tasa solicitada no existe!!");
        }
    }
}
