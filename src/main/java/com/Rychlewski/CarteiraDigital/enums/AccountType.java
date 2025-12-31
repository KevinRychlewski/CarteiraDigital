package com.Rychlewski.CarteiraDigital.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AccountType {

    CORRENTE,
    POUPANCA;

    @JsonCreator
    public static AccountType fromString(String value) {
        try {
            return AccountType.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Tipo de conta inválido. Use CORRENTE ou POUPANCA.");
        }
    }
}
