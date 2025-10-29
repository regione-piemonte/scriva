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
 * The enum Tipologia allegato enum.
 *
 * @author CSI PIEMONTE
 */
public enum TipologiaAllegatoEnum {
    MOD_IST("MODULO ISTANZA FIRMATA"),
    MOD_DP_PRIVACY("Modulo dati personali e trattamento privacy"),
    RT_PAG("RICEVUTE PAGAMENTI"),
    ELENCO_ALLEGATI("ELENCO ALLEGATI"),
    LETT_TRASM("LETTERA ACCOMPAGNAMENTO INTEGRAZIONE"),
    ELENCO_INTEGR("Elenco Allegati Integrazione"),
    RCV_DOC("RCV_DOC");

    private final String descrizione;

    TipologiaAllegatoEnum(String descrizione) {
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
    public static TipologiaAllegatoEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}