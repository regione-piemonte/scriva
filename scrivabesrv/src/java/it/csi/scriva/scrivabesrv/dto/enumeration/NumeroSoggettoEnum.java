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
 * The enum Numero soggetto enum.
 *
 * @author CSI PIEMONTE
 */
public enum NumeroSoggettoEnum {
    /**
     * S numero soggetto enum.
     */
    S("Singolo"),
    /**
     * M numero soggetto enum.
     */
    M("Multiplo");

    private final String descrizione;

    NumeroSoggettoEnum(String descrizione) {
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
     * Find by descr numero soggetto enum.
     *
     * @param descrizione descrizione
     * @return NumeroSoggettoEnum numero soggetto enum
     */
    public static NumeroSoggettoEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

    /**
     * Gets value.
     *
     * @param name name
     * @return int value
     */
    public static int getValue(final String name) {
        if (name.equals("S")) {
            return 1;
        }
        return 999999;
    }
}