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

/**
 * The type Oggetto istanza categoria extended dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoIstanzaCategoriaExtendedDTO extends OggettoIstanzaCategoriaDTO implements Serializable {

    @JsonProperty("categoria_oggetto")
    private CategoriaOggettoExtendedDTO categoriaOggetto;

    /**
     * Gets categoria oggetto.
     *
     * @return categoriaOggetto categoria oggetto
     */
    public CategoriaOggettoExtendedDTO getCategoriaOggetto() {
        return categoriaOggetto;
    }

    /**
     * Sets categoria oggetto.
     *
     * @param categoriaOggetto categoriaOggetto
     */
    public void setCategoriaOggetto(CategoriaOggettoExtendedDTO categoriaOggetto) {
        this.categoriaOggetto = categoriaOggetto;
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
        OggettoIstanzaCategoriaExtendedDTO that = (OggettoIstanzaCategoriaExtendedDTO) o;
        return Objects.equals(categoriaOggetto, that.categoriaOggetto);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), categoriaOggetto);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoIstanzaCategoriaExtendedDTO {");
        sb.append(super.toString());
        sb.append("         categoriaOggetto:").append(categoriaOggetto);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return OggettoIstanzaCategoriaDTO dto
     */
    @JsonIgnore
    public OggettoIstanzaCategoriaDTO getDTO() {
        OggettoIstanzaCategoriaDTO dto = new OggettoIstanzaCategoriaDTO();
        dto.setIdOggettoIstanza(this.getIdOggettoIstanza());
        if (null != this.getCategoriaOggetto()) {
            dto.setIdCategoriaOggetto(this.getCategoriaOggetto().getIdCategoriaOggetto());
        }
        dto.setDataInizioValidita(this.getDataInizioValidita());
        dto.setDataFineValidita(this.getDataFineValidita());
        dto.setFlgCatNuovoOggetto(this.getFlgCatNuovoOggetto());
        dto.setFlgCatModificaOggetto(this.getFlgCatModificaOggetto());
        dto.setFlgCatPrincipale(this.getFlgCatPrincipale());
        dto.setOrdinamentoIstanzaCompetenza(this.getOrdinamentoIstanzaCompetenza());
        dto.setGestAttoreIns(this.gestAttoreIns);
        dto.setGestAttoreUpd(this.gestAttoreUpd);
        dto.setGestDataIns(this.gestDataIns);
        dto.setGestDataUpd(this.gestDataUpd);
        dto.setGestUID(this.gestUID);
        return dto;
    }

}