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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Quadro extended dto.
 *
 * @author CSI PIEMONTE
 */
public class QuadroExtendedDTO extends QuadroDTO implements Serializable {

    @JsonProperty("tipo_quadro")
    private TipoQuadroDTO tipoQuadro;

    public TipoQuadroDTO getTipoQuadro() {
        return tipoQuadro;
    }

    public void setTipoQuadro(TipoQuadroDTO tipoQuadro) {
        this.tipoQuadro = tipoQuadro;
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
        QuadroExtendedDTO that = (QuadroExtendedDTO) o;
        return Objects.equals(tipoQuadro, that.tipoQuadro);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoQuadro);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "QuadroExtendedDTO {\n" +
                super.toString() +
                "         tipoQuadro:" + tipoQuadro +
                "}\n";
    }

    /**
     * @return QuadroDTO
     */
    /*
    @JsonIgnore
    public QuadroDTO getDTO() {
        QuadroDTO dto = new QuadroDTO();
        dto.setIdQuadro(this.getIdQuadro());
        dto.setCodQuadro(this.getCodQuadro());
        dto.setDesQuadro(this.getDesQuadro());
        if (null != this.tipoQuadro) {
            dto.setIdTipoQuadro(this.tipoQuadro.getIdTipoQuadro());
        }
        dto.setFlgTipoGestione(this.getFlgTipoGestione());
        dto.setJsonConfiguraQuadro(this.getJsonConfiguraQuadro());
        dto.setJsonConfiguraRiepilogo(this.getJsonConfiguraRiepilogo());
        return dto;
    }

     */

}