/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Soggetto gruppo dto.
 *
 * @author CSI PIEMONTE
 */
public class SoggettoGruppoExtendedDTO extends SoggettoGruppoDTO implements Serializable {

    @JsonProperty("gruppo_soggetto")
    private GruppoSoggettoDTO gruppoSoggetto;

    /**
     * Gets gruppo soggetto.
     *
     * @return the gruppo soggetto
     */
    public GruppoSoggettoDTO getGruppoSoggetto() {
        return gruppoSoggetto;
    }

    /**
     * Sets gruppo soggetto.
     *
     * @param gruppoSoggetto the gruppo soggetto
     */
    public void setGruppoSoggetto(GruppoSoggettoDTO gruppoSoggetto) {
        this.gruppoSoggetto = gruppoSoggetto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SoggettoGruppoExtendedDTO that = (SoggettoGruppoExtendedDTO) o;
        return Objects.equals(gruppoSoggetto, that.gruppoSoggetto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gruppoSoggetto);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SoggettoGruppoExtendedDTO {");
        sb.append(super.toString());
        sb.append("         gruppoSoggetto:").append(gruppoSoggetto);
        sb.append("}");
        return sb.toString();
    }

    @JsonIgnore
    public SoggettoGruppoDTO getDTO() {
        SoggettoGruppoDTO dto = new SoggettoGruppoDTO();
        dto.setIdSoggettoIstanza(this.getIdSoggettoIstanza());
        if (this.gruppoSoggetto != null) {
            dto.setIdGruppoSoggetto(this.gruppoSoggetto.getIdGruppoSoggetto());
        }
        dto.setFlgCapogruppo(this.getFlgCapogruppo());
        return dto;
    }
}