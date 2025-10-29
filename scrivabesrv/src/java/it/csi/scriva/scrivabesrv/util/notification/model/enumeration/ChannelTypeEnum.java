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
 * The enum Channel type enum.
 *
 * @author CSI PIEMONTE
 */
public enum ChannelTypeEnum {
    /**
     * The Scriva fo.
     */
    SCRIVA_FO("Notifica applicativa FO"),
    /**
     * The Scriva bo.
     */
    SCRIVA_BO("Notifica applicativa BO"),
    /**
     * Email channel type enum.
     */
    EMAIL("Email"),
    /**
     * Pec channel type enum.
     */
    PEC("Pec"),
    /**
     * Sms channel type enum.
     */
    SMS("Sms"),
    /**
     * The App io.
     */
    APP_IO("App IO"),
    /**
     * Web channel type enum.
     */
    WEB("Frontend");

    private final String descrizione;

    ChannelTypeEnum(String descrizione) {
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
    public static ChannelTypeEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}