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
 * The enum Gestione oggetto istanza enum.
 *
 * @author CSI PIEMONTE
 */
public enum GestioneOggettoIstanzaEnum {

    /**
     * The Check ogg ist.
     */
    CHECK_OGG_IST("controllare se per l’istanza di interesse sia già presente un oggetto istanza"),
    /**
     * The No check ogg ist.
     */
    NO_CHECK_OGG_IST("Nessun controllo sulla presenza dell’oggetto-istanza");

    private final String descrizione;

    GestioneOggettoIstanzaEnum(String descrizione) {
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
    public static GestioneOggettoIstanzaEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}