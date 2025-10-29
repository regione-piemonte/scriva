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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FunzionarioCompetenzaExtendedDTO extends FunzionarioCompetenzaDTO implements Serializable {

    @JsonProperty("competenza_territorio")
    private CompetenzaTerritorioExtendedDTO competenzaTerritorio;

    public CompetenzaTerritorioExtendedDTO getCompetenzaTerritorio() {
        return competenzaTerritorio;
    }

    public void setCompetenzaTerritorio(CompetenzaTerritorioExtendedDTO competenzaTerritorio) {
        this.competenzaTerritorio = competenzaTerritorio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FunzionarioCompetenzaExtendedDTO that = (FunzionarioCompetenzaExtendedDTO) o;
        return Objects.equals(competenzaTerritorio, that.competenzaTerritorio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), competenzaTerritorio);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FunzionarioCompetenzaExtendedDTO {");
        sb.append("         competenzaTerritorio:").append(competenzaTerritorio);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public FunzionarioCompetenzaDTO getDTO() {
        FunzionarioCompetenzaDTO dto = new FunzionarioCompetenzaDTO();
        dto.setIdFunzionario(this.getIdFunzionario());
        if (this.competenzaTerritorio != null) {
            dto.setIdCompetenzaTerritorio(this.competenzaTerritorio.getIdCompetenzaTerritorio());
        }
        dto.setDataInizioValidita(this.getDataInizioValidita());
        dto.setDataFineValidita(this.getDataFineValidita());
        return dto;
    }
}