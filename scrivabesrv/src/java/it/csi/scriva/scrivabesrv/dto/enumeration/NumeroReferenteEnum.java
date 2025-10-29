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
 * The enum Numero referente enum.
 *
 * @author CSI PIEMONTE
 */
public enum NumeroReferenteEnum {
    O("Opzionale"),
    S("Singolo"),
    M("Multiplo"),
    N("Nessuno");

    private final String descrizione;

    NumeroReferenteEnum(String descrizione) {
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
     * Find by descr numero referente enum.
     *
     * @param descrizione descrizione
     * @return NumeroReferenteEnum numero referente enum
     */
    public static NumeroReferenteEnum findByDescr(final String descrizione) {
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
            case "O":
                returnValue = 0;
                break;
            case "S":
                returnValue = 1;
                break;
            case "M":
                returnValue = 999999;
                break;
        }
        return returnValue;
    }
}