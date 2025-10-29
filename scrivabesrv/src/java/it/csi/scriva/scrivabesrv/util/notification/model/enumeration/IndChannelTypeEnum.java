/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.notification.model.enumeration;

import java.util.Arrays;

/**
 * The enum Ind channel type enum.
 *
 * @author CSI PIEMONTE
 */
public enum IndChannelTypeEnum {

    A("Canali applicativi"),

    E("Canali esterni");

    private final String descrizione;

    IndChannelTypeEnum(String descrizione) {
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
     * Find by descr channel type enum.
     *
     * @param descrizione the descrizione
     * @return the channel type enum
     */
    public static IndChannelTypeEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}