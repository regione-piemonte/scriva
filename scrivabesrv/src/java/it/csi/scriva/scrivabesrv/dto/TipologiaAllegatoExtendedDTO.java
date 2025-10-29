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
 * The type Tipologia allegato extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipologiaAllegatoExtendedDTO extends TipologiaAllegatoDTO implements Serializable {

    @JsonProperty("categoria_allegato")
    private CategoriaAllegatoDTO categoriaAllegato;

    /**
     * Gets categoria allegato.
     *
     * @return the categoria allegato
     */
    public CategoriaAllegatoDTO getCategoriaAllegato() {
        return categoriaAllegato;
    }

    /**
     * Sets categoria allegato.
     *
     * @param categoriaAllegato the categoria allegato
     */
    public void setCategoriaAllegato(CategoriaAllegatoDTO categoriaAllegato) {
        this.categoriaAllegato = categoriaAllegato;
    }

    /**
     * @param o object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        TipologiaAllegatoExtendedDTO that = (TipologiaAllegatoExtendedDTO) o;
        return Objects.equals(categoriaAllegato, that.categoriaAllegato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), categoriaAllegato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipologiaAllegatoExtendedDTO {");
        sb.append(super.toString());
        sb.append("         categoriaAllegato:").append(categoriaAllegato);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return TipologiaAllegatoDTO dto
     */
    @JsonIgnore
    public TipologiaAllegatoDTO getDTO() {
        TipologiaAllegatoDTO dto = new TipologiaAllegatoDTO();
        dto.setIdTipologiaAllegato(this.getIdTipologiaAllegato());
        if (null != this.categoriaAllegato) {
            dto.setIdCategoriaAllegato(this.categoriaAllegato.getIdCategoriaAllegato());
        }
        dto.setCodTipologiaAllegato(this.getCodTipologiaAllegato());
        dto.setDesTipologiaAllegato(this.getDesTipologiaAllegato());
        dto.setFlgAttivo(this.getFlgAttivo());
        dto.setOrdinamentoTipologiaAllegato(this.getOrdinamentoTipologiaAllegato());
        return dto;
    }
}