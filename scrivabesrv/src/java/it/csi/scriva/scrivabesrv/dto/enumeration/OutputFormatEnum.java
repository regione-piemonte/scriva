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
 * The enum Output format enum.
 *
 * @author CSI PIEMONTE
 */
public enum OutputFormatEnum {
    /**
     * Csv output format enum.
     */
    CSV("CSV"),
    /**
     * Pdf output format enum.
     */
    PDF("PDF"),
    /**
     * Doc output format enum.
     */
    DOC("DOC"),
    /**
     * Zip output format enum.
     */
    ZIP("ZIP");

    private final String descrizione;

    OutputFormatEnum(String descrizione) {
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
     * Find by descr output format enum.
     *
     * @param descrizione the descrizione
     * @return the output format enum
     */
    public static OutputFormatEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}