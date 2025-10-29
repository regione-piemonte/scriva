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

public enum StatoOsservazioneEnum {

	BOZZA("In compilazione", 1L),
    INVIATA("Inviata", 2L),
    PUBBLICATA("Pubblicata", 3L);
    

    private final String descrizione;
    private final Long idStatoOsservazione;


    StatoOsservazioneEnum(String descrizione, Long idStatoOsservazione) {
        this.descrizione = descrizione;
        this.idStatoOsservazione = idStatoOsservazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Long getIdStatoOsservazione() {
        return idStatoOsservazione;
    }

    public static StatoOsservazioneEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }
}