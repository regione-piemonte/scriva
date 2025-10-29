/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.placeholder.enumeration;

import java.util.Arrays;

/**
 * The enum Place holder table enum.
 *
 * @author CSI PIEMONTE
 */
public enum PlaceHolderTableEnum {

    SCRIVA_D_CONFIGURAZIONE("SCRIVA_D_CONFIGURAZIONE"),
    SCRIVA_D_STATO_NOTIFICA("SCRIVA_D_STATO_NOTIFICA"),
    SCRIVA_D_TIPO_ARE_PROTETTA("SCRIVA_D_TIPO_ARE_PROTETTA"),
    SCRIVA_D_TIPO_COMPETENZA("SCRIVA_D_TIPO_COMPETENZA"),
    SCRIVA_D_TIPO_NATURA_2000("SCRIVA_D_TIPO_NATURA_2000"),
    SCRIVA_R_ADEMPI_COMPETENZA("SCRIVA_R_ADEMPI_COMPETENZA"),
    SCRIVA_R_NOTIFICA_APPLICATIVA("SCRIVA_R_NOTIFICA_APPLICATIVA"),
    SCRIVA_R_OGG_AREA_PROTETTA("SCRIVA_R_OGG_AREA_PROTETTA"),
    SCRIVA_R_OGG_NATURA_2000("SCRIVA_R_OGG_NATURA_2000"),
    SCRIVA_R_RICHIESTA_ACCREDITO("SCRIVA_R_RICHIESTA_ACCREDITO"),
    SCRIVA_R_UBICA_OGGETTO_ISTANZA("SCRIVA_R_UBICA_OGGETTO_ISTANZA"),
    SCRIVA_T_COMPETENZA_TERRITORIO("SCRIVA_T_COMPETENZA_TERRITORIO"),
    SCRIVA_T_COMPILANTE("SCRIVA_T_COMPILANTE"),
    SCRIVA_T_ISTANZA("SCRIVA_T_ISTANZA"),
    SCRIVA_T_NOTIFICA("SCRIVA_T_NOTIFICA"),
    SCRIVA_T_SOGGETTO_ISTANZA("SCRIVA_T_SOGGETTO_ISTANZA"),
    SCRIVA_T_OGGETTO_ISTANZA("SCRIVA_T_OGGETTO_ISTANZA");

    private final String descrizione;

    PlaceHolderTableEnum(String descrizione) {
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
     * Find by descr azioni base enum.
     *
     * @param descrizione descrizione
     * @return ProfiloAppEnum azioni base enum
     */
    public static PlaceHolderTableEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}