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

public class TipoQuadroDTO implements Serializable {

    @JsonProperty("id_tipo_quadro")
    private Long idTipoQuadro;

    @JsonProperty("cod_tipo_quadro")
    private String codiceTipoQuadro;

    @JsonProperty("des_tipo_quadro")
    private String descrizioneTipoQuadro;

    public Long getIdTipoQuadro() {
        return idTipoQuadro;
    }

    public void setIdTipoQuadro(Long idTipoQuadro) {
        this.idTipoQuadro = idTipoQuadro;
    }

    public String getCodiceTipoQuadro() {
        return codiceTipoQuadro;
    }

    public void setCodiceTipoQuadro(String codiceTipoQuadro) {
        this.codiceTipoQuadro = codiceTipoQuadro;
    }

    public String getDescrizioneTipoQuadro() {
        return descrizioneTipoQuadro;
    }

    public void setDescrizioneTipoQuadro(String descrizioneTipoQuadro) {
        this.descrizioneTipoQuadro = descrizioneTipoQuadro;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceTipoQuadro, descrizioneTipoQuadro, idTipoQuadro);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TipoQuadroDTO other = (TipoQuadroDTO) obj;
        return Objects.equals(codiceTipoQuadro, other.codiceTipoQuadro) && Objects.equals(descrizioneTipoQuadro, other.descrizioneTipoQuadro) && Objects.equals(idTipoQuadro, other.idTipoQuadro);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TipoQuadroDTO {\n    idTipoQuadro: ").append(idTipoQuadro).append("\n    codiceTipoQuadro: ").append(codiceTipoQuadro).append("\n    descrizioneTipoQuadro: ").append(descrizioneTipoQuadro).append("\n}");
        return builder.toString();
    }

}