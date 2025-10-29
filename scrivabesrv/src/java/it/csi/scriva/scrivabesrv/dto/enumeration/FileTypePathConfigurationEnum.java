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

public enum FileTypePathConfigurationEnum {


    VINCOLI("Vincoli Autorizzazioni", "SCRIVA_PATH_FILE_VINCOLI_NORM");

    private final String descrizione;
    private final String chiaveConfigurazione;


    FileTypePathConfigurationEnum(String descrizione, String chiaveConfigurazione) {
        this.descrizione = descrizione;
        this.chiaveConfigurazione = chiaveConfigurazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getChiaveConfigurazione() {
        return chiaveConfigurazione;
    }

    public static FileTypePathConfigurationEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }
}