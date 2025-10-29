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
 * The enum Fase pubblicazione enum.
 *
 * @author CSI PIEMONTE
 */
public enum FasePubblicazioneEnum {
    /**
     * Consulta fase pubblicazione enum.
     */
    CONSULTA("IN CONSULTAZIONE PUBBLICA"),
    /**
     * In corso fase pubblicazione enum.
     */
    IN_CORSO("IN CORSO DI ISTRUTTORIA"),
    /**
     * Conclusa fase pubblicazione enum.
     */
    CONCLUSA("CONCLUSI"),
	
	TUTTE("TUTTI");

    private final String descrizione;

    FasePubblicazioneEnum(String descrizione) {
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
     * Find by descr componente app enum.
     *
     * @param descrizione descrizione
     * @return ComponenteAppEnum componente app enum
     */
    public static FasePubblicazioneEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}