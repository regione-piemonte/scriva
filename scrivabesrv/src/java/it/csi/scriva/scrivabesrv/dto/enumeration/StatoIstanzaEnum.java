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
 * The enum Stato istanza enum.
 *
 * @author CSI PIEMONTE
 */
public enum StatoIstanzaEnum {
    /**
     * The Bozza.
     */
    BOZZA("Istanza in stato Bozza"),
    /**
     * The Presentata.
     */
    PRESENTATA("Istanza Presentata");

    private final String descrizione;

    StatoIstanzaEnum(String descrizione) {
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
     * Find by descr stato istanza enum.
     *
     * @param descrizione descrizione
     * @return StatoIstanzaEnum stato istanza enum
     */
    public static StatoIstanzaEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }
}