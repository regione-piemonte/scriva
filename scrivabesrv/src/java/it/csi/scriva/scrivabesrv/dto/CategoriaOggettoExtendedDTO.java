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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Categoria oggetto extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaOggettoExtendedDTO extends CategoriaOggettoDTO implements Serializable {

    @JsonProperty("tipologia_oggetto")
    private TipologiaOggettoExtendedDTO tipologiaOggetto;

    @JsonProperty("tipi_competenza")
    private List<TipoCompetenzaDTO> tipoCompetenzaList;

    /**
     * Gets tipologia oggetto.
     *
     * @return tipologiaOggetto tipologia oggetto
     */
    public TipologiaOggettoExtendedDTO getTipologiaOggetto() {
        return tipologiaOggetto;
    }

    /**
     * Sets tipologia oggetto.
     *
     * @param tipologiaOggetto tipologiaOggetto
     */
    public void setTipologiaOggetto(TipologiaOggettoExtendedDTO tipologiaOggetto) {
        this.tipologiaOggetto = tipologiaOggetto;
    }

    /**
     * Gets tipo competenza list.
     *
     * @return tipoCompetenzaList tipo competenza list
     */
    public List<TipoCompetenzaDTO> getTipoCompetenzaList() {
        return tipoCompetenzaList;
    }

    /**
     * Sets tipo competenza list.
     *
     * @param tipoCompetenzaList tipoCompetenzaList
     */
    public void setTipoCompetenzaList(List<TipoCompetenzaDTO> tipoCompetenzaList) {
        this.tipoCompetenzaList = tipoCompetenzaList;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CategoriaOggettoExtendedDTO that = (CategoriaOggettoExtendedDTO) o;
        return Objects.equals(tipologiaOggetto, that.tipologiaOggetto) && Objects.equals(tipoCompetenzaList, that.tipoCompetenzaList);
    }


    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipologiaOggetto, tipoCompetenzaList);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CategoriaOggettoExtendedDTO {");
        sb.append(super.toString());
        sb.append("         tipologiaOggetto:").append(tipologiaOggetto);
        sb.append(",         tipoCompetenzaList:").append(tipoCompetenzaList);
        sb.append("}");
        return sb.toString();
    }
}