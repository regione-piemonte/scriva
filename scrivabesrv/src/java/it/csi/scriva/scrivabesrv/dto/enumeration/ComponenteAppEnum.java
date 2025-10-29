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
 * The enum Componente app enum.
 *
 * @author CSI PIEMONTE
 */
public enum ComponenteAppEnum {
    /**
     * Fo componente app enum.
     */
    FO("FO", 1L),
    /**
     * Bo componente app enum.
     */
    BO("BO", 2L),
    /**
     * Esterna componente app enum.
     */
    ESTERNA("ESTERNA", 3L),
    /**
     * Pubweb componente app enum.
     */
    PUBWEB("PUBWEB", 4L),

    /**
     * Risca componente app enum.
     */
    RISCA("RISCA", 5L),

    /**
     * Cosmo componente app enum.
     */
    COSMO("COSMO", 6L),

    /**
     * Decsira componente app enum.
     */
    DECSIRA("DECSIRA", 7L);

    private final String descrizione;
    private final Long idComponenteApp;

    ComponenteAppEnum(String descrizione, Long idComponenteApp) {
        this.descrizione = descrizione;
        this.idComponenteApp = idComponenteApp;
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
     * Gets id componente app.
     *
     * @return the id componente app
     */
    public Long getIdComponenteApp() {
        return idComponenteApp;
    }

    /**
     * Find by descr componente app enum.
     *
     * @param descrizione descrizione
     * @return ComponenteAppEnum componente app enum
     */
    public static ComponenteAppEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}