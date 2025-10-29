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
 * The enum Ruolo compilante enum.
 *
 * @author CSI PIEMONTE
 */
public enum RuoloCompilanteEnum {
    /**
     * Tit ruolo compilante enum.
     */
    TIT("Titolare"),
    /**
     * The Leg.
     */
    LEG("Legale Rappresentante"),
    /**
     * The Pro.
     */
    PRO("Procuratore ai sensi del d.p.r. 160/2010"),
    /**
     * The Del.
     */
    DEL("Delegato Legale Rappresentante senza potere firma"),
    /**
     * The Del firma.
     */
    DEL_FIRMA("Delegato Legale Rappresentante con potere firma"),;

    private final String descrizione;

    RuoloCompilanteEnum(String descrizione) {
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
     * Find by descr ruolo compilante enum.
     *
     * @param descrizione descrizione
     * @return ComponenteAppEnum ruolo compilante enum
     */
    public static RuoloCompilanteEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}