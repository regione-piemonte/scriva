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
 * The enum Numero opera enum.
 *
 * @author CSI PIEMONTE
 */
public enum NumeroOperaEnum {
    /**
     * S numero opera enum.
     */
    S("Singola"),
    /**
     * M numero opera enum.
     */
    M("Multipla"),
    /**
     * N numero opera enum.
     */
    N("Nessuna"),
    /**
     * The O.
     */
    O("Singola opzionale");

    private final String descrizione;

    NumeroOperaEnum(String descrizione) {
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
     * Find by descr numero opera enum.
     *
     * @param descrizione descrizione
     * @return NumeroOperaEnum numero opera enum
     */
    public static NumeroOperaEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

    /**
     * Gets value.
     *
     * @param name name
     * @return int value
     */
    public static int getValue(final String name) {
        int returnValue = -1;
        switch (name) {
            case "N":
                returnValue = 0;
                break;
            case "S":
            case "O":
                returnValue = 1;
                break;
            case "M":
                returnValue = 999999;
                break;
        }
        return returnValue;
    }
}