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
 * The enum Tipo oggetto app enum.
 */
public enum TipoOggettoAppEnum {
    /**
     * The Az base.
     */
    AZ_BASE("Azione Base"),
    /**
     * Pulsante tipo oggetto app enum.
     */
    PULSANTE("Pulsante"),
    /**
     * The Voce menu.
     */
    VOCE_MENU("Voce di Menu"),
    /**
     * Servizio tipo oggetto app enum.
     */
    SERVIZIO("Servizio"),
    /**
     * The Tipo quadro.
     */
    TIPO_QUADRO("Tipo Quadro Applicativo"),
    /**
     * The Az avanzata.
     */
    AZ_AVANZATA("Azione Avanzata");

    private final String descrizione;

    TipoOggettoAppEnum(String descrizione) {
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
     * Find by descr tipo oggetto app enum.
     *
     * @param descrizione the descrizione
     * @return the tipo oggetto app enum
     */
    public static TipoOggettoAppEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}