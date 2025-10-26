/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class SearchOggettoDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_comune")
    private Long idComune;

    @JsonProperty("id_soggetti")
    private List<Long> idSoggetti;

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public Long getIdComune() {
        return idComune;
    }

    public void setIdComune(Long idComune) {
        this.idComune = idComune;
    }

    public List<Long> getIdSoggetti() {
        return idSoggetti;
    }

    public void setIdSoggetti(List<Long> idSoggetti) {
        this.idSoggetti = idSoggetti;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idComune, idIstanza, idSoggetti);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SearchOggettoDTO other = (SearchOggettoDTO) obj;
        return Objects.equals(idComune, other.idComune) && Objects.equals(idIstanza, other.idIstanza) && Objects.equals(idSoggetti, other.idSoggetti);
    }

}