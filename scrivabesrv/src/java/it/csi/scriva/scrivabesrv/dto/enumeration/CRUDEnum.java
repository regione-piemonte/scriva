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
 * The enum Crud enum.
 *
 * @author CSI PIEMONTE
 */
public enum CRUDEnum {

    /**
     * Create crud enum.
     */
    CREATE("CREATE"),

    /**
     * Read crud enum.
     */
    READ("READ"),

    /**
     * Update crud enum.
     */
    UPDATE("UPDATE"),

    /**
     * Delete crud enum.
     */
    DELETE("DELETE");

    private final String descrizione;

    CRUDEnum(String descrizione) {
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
    public static CRUDEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}