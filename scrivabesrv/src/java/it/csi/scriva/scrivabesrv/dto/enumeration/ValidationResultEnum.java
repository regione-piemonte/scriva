/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto.enumeration;

import java.util.Arrays;

/**
 * The enum Validation result enum.
 *
 * @author CSI PIEMONTE
 */
public enum ValidationResultEnum {

    /**
     * Valid validation result enum.
     */
    VALID("Valido"),

    /**
     * The Invalid.
     */
    INVALID("Non valido"),

    /**
     * The Mandatory.
     */
    MANDATORY("Campo obbligatorio"),

    /**
     * The Invalid length.
     */
    INVALID_LENGTH("Lunghezza non valida"),

    /**
     * The Invalid chars.
     */
    INVALID_CHARS("Caratteri non validi"),

     /**
     * The Invalid control code.
     */
    INVALID_CF("Codice fiscale non valido"),

    /**
     * The Invalid control code.
     */
    INVALID_CONTROL_CODE("Codice di controllo non valido"),

    /**
     * The Invalid cf cognome.
     */
    INVALID_CF_COGNOME("Cognome incoerente con codice fiscale"),

    /**
     * The Invalid cf nome.
     */
    INVALID_CF_NOME("Nome incoerente con codice fiscale"),

    /**
     * The Invalid alpha string.
     */
    INVALID_ALPHA_STRING("Può contenere solo caratteri alfabetici"),

    /**
     * The Invalid alpha numeric string.
     */
    INVALID_ALPHA_NUMERIC_STRING("Può contenere solo caratteri alfabetici e numeri"),

    /**
     * The Invalid number.
     */
    INVALID_NUMBER("Non è un numero valido"),

    /**
     * The Invalid date.
     */
    INVALID_DATE("Data non valida"),

    /**
     * The expired observation date.
     */
    EXPIRED_OBSERVATION_DATE("Data osservazione scaduta"),

    /**
     * The Invalid email.
     */
    INVALID_EMAIL("Email non valida"),

    /**
     * The Duplicate.
     */
    DUPLICATE("Contenuto duplicato");

    private final String description;

    ValidationResultEnum(String description) {
        this.description = description;
    }

    /**
     * Gets description.
     *
     * @return string description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Find by descr validation result enum.
     *
     * @param descrizione descrizione
     * @return ValidationResultEnum validation result enum
     */
    public static ValidationResultEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescription().equals(descrizione)).findFirst().orElse(null);
    }

}