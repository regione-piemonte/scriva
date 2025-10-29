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
 * The enum Tipologia oggetto enum.
 *
 * @author CSI PIEMONTE
 */
public enum TipologiaOggettoEnum {

    /**
     * Default tipologia oggetto enum.
     */
    DEFAULT("DEFAULT"),
    /**
     * Tutti tipologia oggetto enum.
     */
    TUTTI("TUTTI");

    private final String descrizione;

    TipologiaOggettoEnum(String descrizione) {
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
     * Find by descr tipo abilitazione enum.
     *
     * @param descrizione descrizione
     * @return TipoAbilitazioneEnum tipo abilitazione enum
     */
    public static TipologiaOggettoEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}