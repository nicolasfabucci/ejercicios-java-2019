package com.eiv.convertidor;

public class CalculadoraRazonFactory {

    public static CalculadoraRazon create(TipoTasaEnum tipoTasaEnum) {
        switch (tipoTasaEnum) {
            case TE:
                return new CalculadoraRazonDesdeTe();
            case TNV:
                return new CalculadoraRazonDesdeTnv();
            case TNA:
                return new CalculadoraRazonDesdeTna();
            default:
                throw new IllegalArgumentException(
                        "La calculadora para el tipo de tasa solicitada no existe!!");
        }
    }
}
