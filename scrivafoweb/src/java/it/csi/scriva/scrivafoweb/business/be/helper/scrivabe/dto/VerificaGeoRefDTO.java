/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import java.io.Serializable;

public class VerificaGeoRefDTO implements Serializable {
    Boolean geoRefOk;

    public VerificaGeoRefDTO() {
        super();
    }

    public VerificaGeoRefDTO(Boolean geoRefOk) {
        super();
        this.geoRefOk = geoRefOk;
    }

    public Boolean getGeoRefOk() {
        return geoRefOk;
    }

    public void setGeoRefOk(Boolean geoRefOk) {
        this.geoRefOk = geoRefOk;
    }
}