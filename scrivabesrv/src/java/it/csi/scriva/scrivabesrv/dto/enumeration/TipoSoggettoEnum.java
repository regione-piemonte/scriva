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
 * The enum Tipo soggetto enum.
 *
 * @author CSI PIEMONTE
 */
public enum TipoSoggettoEnum {
    /**
     * The Pf.
     */
    PF("Persona Fisica"),

    /**
     * The Pg.
     */
    PG("Persona Giuridica"),

    /**
     * The Gf.
     */
    GF("Entrambe Fisica/Giuridica"),

    /**
     * The Pg pf.
     */
    PG_PF("Entrambe Fisica/Giuridica"),

    /**
     * The Pf pg.
     */
    PF_PG("Entrambe Fisica/Giuridica"),

    /**
     * The Pb.
     */
    PB("Persona Giuridica Pubblica");



    private final String descrizione;

    TipoSoggettoEnum(String descrizione) {
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
     * Find by descr tipo soggetto enum.
     *
     * @param descrizione descrizione
     * @return TipoSoggettoEnum tipo soggetto enum
     */
    public static TipoSoggettoEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }
}