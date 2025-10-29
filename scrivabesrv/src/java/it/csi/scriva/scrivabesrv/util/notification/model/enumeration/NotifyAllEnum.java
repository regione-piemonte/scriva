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
 * The enum Notify all enum.
 *
 * @author CSI PIEMONTE
 */
public enum NotifyAllEnum {

    /**
     * Si notify all enum.
     */
    SI("SI"),
    /**
     * No notify all enum.
     */
    NO("NO"),
    /**
     * Trace notify all enum.
     */
    TRACE("TRACE"),
    /**
     * Push notify all enum.
     */
    PUSH("PUSH");

    private final String descrizione;

    NotifyAllEnum(String descrizione) {
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
    public static NotifyAllEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}