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
 * The enum Result enum.
 *
 * @author CSI PIEMONTE
 */
public enum ResultEnum {
    /**
     * The Inviata.
     */
    INVIATA("Notifica inviata"),
    /**
     * The Successo.
     */
    SUCCESSO("Notificata con successo"),
    /**
     * The Fallita.
     */
    FALLITA("Notifica fallita");

    private final String descrizione;

    ResultEnum(String descrizione) {
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
     * Find by descr result enum.
     *
     * @param descrizione the descrizione
     * @return the result enum
     */
    public static ResultEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}