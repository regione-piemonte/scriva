/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author CSI PIEMONTE
 */
public class TipologiaAllegatoExtendedDTO extends TipologiaAllegatoDTO implements Serializable {

    @JsonProperty("categoria_allegato")
    private CategoriaAllegatoDTO categoriaAllegato;

    public CategoriaAllegatoDTO getCategoriaAllegato() {
        return categoriaAllegato;
    }

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
     * @return TipologiaAllegatoDTO
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