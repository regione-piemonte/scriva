/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.dto.enumeration;

public enum ValidationResultEnum {

    VALID("Valido"),

    INVALID("Non valido"),

    INVALID_LENGTH("Lunghezza non valida"),

    INVALID_CHARS("Caratteri non validi"),

    INVALID_CONTROL_CODE("Codice di controllo non valido"),

    INVALID_ALPHA_STRING("Può contenere solo caratteri alfabetici"),

    INVALID_ALPHA_NUMERIC_STRING("Può contenere solo caratteri alfabetici e numeri"),

    INVALID_NUMBER("Non è un numero valido"),

    INVALID_DATE("Data non valida"),

    INVALID_EMAIL("Email non valida");

    private String description;

    ValidationResultEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}