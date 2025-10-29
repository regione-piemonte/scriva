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
 * The enum Tipo evento enum.
 *
 * @author CSI PIEMONTE
 */
public enum TipoEventoEnum {

    /**
     * The Ins istanza.
     */
    INS_ISTANZA("Istanza inserita"),

    /**
     * The Ins pratica bo.
     */
    INS_PRATICA_BO("Inserita pratica da BO"),

    /**
     * The Da firmare.
     */
    DA_FIRMARE("Istanza scaricata da firmare"),
    /**
     * The Firma mod.
     */
    FIRMA_MOD("Modulo istanza firmato"),
    /**
     * The Presenta mod.
     */
    PRESENTA_MOD("Modulo istanza presentato"),
    /**
     * The Correggi.
     */
    CORREGGI("Correggi Istanza"),
    /**
     * The In carico.
     */
    IN_CARICO("Istanza presa in carico"),
    /**
     * The Avviata.
     */
    AVVIATA("Avviata pratica"),
    /**
     * The Rich alleg.
     */
    RICH_ALLEG("Richiesta integrazione allegati"),
    /**
     * The Int all.
     */
    INT_ALL("Integrati allegati richiesti"),
    /**
     * The Rifiuta.
     */
    RIFIUTA("Pratica rifiutata");

    private final String descrizione;


    TipoEventoEnum(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Gets descrizione.
     *
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Find by descr tipo evento enum.
     *
     * @param descrizione the descrizione
     * @return the tipo evento enum
     */
    public static TipoEventoEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}