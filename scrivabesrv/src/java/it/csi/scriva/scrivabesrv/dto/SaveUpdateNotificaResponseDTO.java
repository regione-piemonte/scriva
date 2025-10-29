/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto;

public class SaveUpdateNotificaResponseDTO {

    private final Long id;
    private final String uid;
    private final String oggettoNotifica;

    public SaveUpdateNotificaResponseDTO(Long id, String uid, String oggettoNotifica) {
        this.id = id;
        this.uid = uid;
        this.oggettoNotifica = oggettoNotifica;
    }

    public Long getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getOggettoNotifica() {
        return oggettoNotifica;
    }
    
}
