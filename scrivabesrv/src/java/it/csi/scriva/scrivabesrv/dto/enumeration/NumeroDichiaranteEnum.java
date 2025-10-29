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
 * The enum Numero dichiarante enum.
 *
 * @author CSI PIEMONTE
 */
public enum NumeroDichiaranteEnum {
    /**
     * S numero dichiarante enum.
     */
    S("Singolo"),
    /**
     * M numero dichiarante enum.
     */
    M("Multiplo"),
    /**
     * N numero dichiarante enum.
     */
    N("Nessuno");

    private final String descrizione;

    NumeroDichiaranteEnum(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Gets descrizione.
     *
     * @return string descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Find by descr numero dichiarante enum.
     *
     * @param descrizione descrizione
     * @return NumeroDichiaranteEnum numero dichiarante enum
     */
    public static NumeroDichiaranteEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }
}