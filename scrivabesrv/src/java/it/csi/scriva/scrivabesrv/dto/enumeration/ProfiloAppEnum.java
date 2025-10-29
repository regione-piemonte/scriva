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
 * The enum Profilo app enum.
 *
 * @author CSI PIEMONTE
 */
public enum ProfiloAppEnum {
    /**
     * Compilante profilo app enum.
     */
    COMPILANTE("COMPILANTE"),
    /**
     * Richiedente profilo app enum.
     */
    RICHIEDENTE("RICHIEDENTE"),
    /**
     * Abilitato consulta profilo app enum.
     */
    ABILITATO_CONSULTA("ABILITATO_CONSULTA"),
    /**
     * Abilitato gestione profilo app enum.
     */
    ABILITATO_GESTIONE("ABILITATO_GESTIONE"),
    /**
     * Admin profilo app enum.
     */
    ADMIN("ADMIN"),
    /**
     * Read all profilo app enum.
     */
    READ_ALL("READ_ALL"),
    /**
     * Via rw profilo app enum.
     */
    VIA_RW("VIA_RW"),
    /**
     * Via read profilo app enum.
     */
    VIA_READ("VIA_READ"),
    /**
     * Aua rw profilo app enum.
     */
    AUA_RW("AUA_RW"),
    /**
     * Aua read profilo app enum.
     */
    AUA_READ("AUA_READ");

    private final String descrizione;

    ProfiloAppEnum(String descrizione) {
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
     * Find by descr profilo app enum.
     *
     * @param descrizione descrizione
     * @return ProfiloAppEnum profilo app enum
     */
    public static ProfiloAppEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}