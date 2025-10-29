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
import java.util.Objects;

/**
 * The type Tipo quadro dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoQuadroDTO implements Serializable {

    @JsonProperty("id_tipo_quadro")
    private Long idTipoQuadro;

    @JsonProperty("cod_tipo_quadro")
    private String codiceTipoQuadro;

    @JsonProperty("des_tipo_quadro")
    private String descrizioneTipoQuadro;

    /**
     * Gets id tipo quadro.
     *
     * @return the id tipo quadro
     */
    public Long getIdTipoQuadro() {
        return idTipoQuadro;
    }

    /**
     * Sets id tipo quadro.
     *
     * @param idTipoQuadro the id tipo quadro
     */
    public void setIdTipoQuadro(Long idTipoQuadro) {
        this.idTipoQuadro = idTipoQuadro;
    }

    /**
     * Gets codice tipo quadro.
     *
     * @return the codice tipo quadro
     */
    public String getCodiceTipoQuadro() {
        return codiceTipoQuadro;
    }

    /**
     * Sets codice tipo quadro.
     *
     * @param codiceTipoQuadro the codice tipo quadro
     */
    public void setCodiceTipoQuadro(String codiceTipoQuadro) {
        this.codiceTipoQuadro = codiceTipoQuadro;
    }

    /**
     * Gets descrizione tipo quadro.
     *
     * @return the descrizione tipo quadro
     */
    public String getDescrizioneTipoQuadro() {
        return descrizioneTipoQuadro;
    }

    /**
     * Sets descrizione tipo quadro.
     *
     * @param descrizioneTipoQuadro the descrizione tipo quadro
     */
    public void setDescrizioneTipoQuadro(String descrizioneTipoQuadro) {
        this.descrizioneTipoQuadro = descrizioneTipoQuadro;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codiceTipoQuadro, descrizioneTipoQuadro, idTipoQuadro);
    }

    /**
     * @param obj Object
     * @return boolean
     */
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

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TipoQuadroDTO {\n    idTipoQuadro: ").append(idTipoQuadro).append("\n    codiceTipoQuadro: ").append(codiceTipoQuadro).append("\n    descrizioneTipoQuadro: ").append(descrizioneTipoQuadro).append("\n}");
        return builder.toString();
    }

}