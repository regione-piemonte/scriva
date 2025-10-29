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
 * The enum Tipo abilitazione enum.
 *
 * @author CSI PIEMONTE
 */
public enum TipoAbilitazioneEnum {
    /**
     * Cons tipo abilitazione enum.
     */
    CONS("CONS"),
    /**
     * Gest tipo abilitazione enum.
     */
    GEST("GEST");

    private final String descrizione;

    TipoAbilitazioneEnum(String descrizione) {
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
    public static TipoAbilitazioneEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}