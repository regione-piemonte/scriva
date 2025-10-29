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
 * The enum Azioni base
 *
 * @author CSI PIEMONTE
 */
public enum AzioniBaseEnum {
    /**
     * Read only azioni base enum.
     */
    READ_ONLY("READ_ONLY"),
    /**
     * Write azioni base enum.
     */
    WRITE("WRITE"),
    /**
     * Write lock azioni base enum.
     */
    WRITE_LOCK("WRITE_LOCK");


 

    private final String descrizione;

    AzioniBaseEnum(String descrizione) {
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
     * Find by descr azioni base enum.
     *
     * @param descrizione descrizione
     * @return ProfiloAppEnum azioni base enum
     */
    public static AzioniBaseEnum findByDescr(final String descrizione) {
        
        if (descrizione == null) {
            return null; // O restituisci un valore predefinito, ad esempio READ_ONLY?!?!?
        }

        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}