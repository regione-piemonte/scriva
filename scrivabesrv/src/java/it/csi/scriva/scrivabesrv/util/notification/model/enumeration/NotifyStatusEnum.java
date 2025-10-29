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
 * The enum Notify status enum.
 *
 * @author CSI PIEMONTE
 */
public enum NotifyStatusEnum {

    /**
     * Creata notify status enum.
     */
    CREATA("Creata", 10L),
    /**
     * Inviata notify status enum.
     */
    INVIATA("Inviata", 20L),
    /**
     * Successo notify status enum.
     */
    SUCCESSO("Successo", 30L),
    /**
     * Fallita notify status enum.
     */
    FALLITA("Fallita", 40L),
    /**
     * Trace notify status enum.
     */
    TRACE("Trace", 50L);

    private final String descrizione;
    private final Long id;

    NotifyStatusEnum(String descrizione, Long id) {
        this.descrizione = descrizione;
        this.id = id;
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
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Find by descr channel type enum.
     *
     * @param descrizione the descrizione
     * @return the channel type enum
     */
    public static NotifyStatusEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}