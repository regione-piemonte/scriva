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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Allegato istanza extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegatoIstanzaCosmoDTO implements Serializable {

    private String uuidCosmo;

    private AllegatoIstanzaExtendedDTO allegatoIstanza;

    /**
     * Gets uuid cosmo.
     *
     * @return the uuid cosmo
     */
    public String getUuidCosmo() {
        return uuidCosmo;
    }

    /**
     * Sets uuid cosmo.
     *
     * @param uuidCosmo the uuid cosmo
     */
    public void setUuidCosmo(String uuidCosmo) {
        this.uuidCosmo = uuidCosmo;
    }

    /**
     * Gets allegato istanza.
     *
     * @return the allegato istanza
     */
    public AllegatoIstanzaExtendedDTO getAllegatoIstanza() {
        return allegatoIstanza;
    }

    /**
     * Sets allegato istanza.
     *
     * @param allegatoIstanza the allegato istanza
     */
    public void setAllegatoIstanza(AllegatoIstanzaExtendedDTO allegatoIstanza) {
        this.allegatoIstanza = allegatoIstanza;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllegatoIstanzaCosmoDTO that = (AllegatoIstanzaCosmoDTO) o;
        return Objects.equals(uuidCosmo, that.uuidCosmo) && Objects.equals(allegatoIstanza, that.allegatoIstanza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuidCosmo, allegatoIstanza);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AllegatoIstanzaCosmoDTO {\n");
        sb.append("         uuidCosmo:'").append(uuidCosmo).append("'");
        sb.append(",\n         allegatoIstanza:").append(allegatoIstanza);
        sb.append("}\n");
        return sb.toString();
    }
}