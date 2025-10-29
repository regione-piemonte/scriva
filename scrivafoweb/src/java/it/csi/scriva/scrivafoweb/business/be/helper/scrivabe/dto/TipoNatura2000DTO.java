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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipo natura 2000 dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TipoNatura2000DTO implements Serializable {

    @JsonProperty("id_tipo_natura_2000")
    private Long idTipoNatura2000;

    @JsonProperty("cod_tipo_natura_2000")
    private String codTipoNatura2000;

    @JsonProperty("des_tipo_natura_2000")
    private String desTipoNatura2000;

    /**
     * Gets id tipo natura 2000.
     *
     * @return the id tipo natura 2000
     */
    public Long getIdTipoNatura2000() {
        return idTipoNatura2000;
    }

    /**
     * Sets id tipo natura 2000.
     *
     * @param idTipoNatura2000 the id tipo natura 2000
     */
    public void setIdTipoNatura2000(Long idTipoNatura2000) {
        this.idTipoNatura2000 = idTipoNatura2000;
    }

    /**
     * Gets cod tipo natura 2000.
     *
     * @return the cod tipo natura 2000
     */
    public String getCodTipoNatura2000() {
        return codTipoNatura2000;
    }

    /**
     * Sets cod tipo natura 2000.
     *
     * @param codTipoNatura2000 the cod tipo natura 2000
     */
    public void setCodTipoNatura2000(String codTipoNatura2000) {
        this.codTipoNatura2000 = codTipoNatura2000;
    }

    /**
     * Gets des tipo natura 2000.
     *
     * @return the des tipo natura 2000
     */
    public String getDesTipoNatura2000() {
        return desTipoNatura2000;
    }

    /**
     * Sets des tipo natura 2000.
     *
     * @param desTipoNatura2000 the des tipo natura 2000
     */
    public void setDesTipoNatura2000(String desTipoNatura2000) {
        this.desTipoNatura2000 = desTipoNatura2000;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoNatura2000DTO that = (TipoNatura2000DTO) o;
        return Objects.equals(idTipoNatura2000, that.idTipoNatura2000) && Objects.equals(codTipoNatura2000, that.codTipoNatura2000) && Objects.equals(desTipoNatura2000, that.desTipoNatura2000);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipoNatura2000, codTipoNatura2000, desTipoNatura2000);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoNatura2000DTO {\n");
        sb.append("         idTipoNatura2000:").append(idTipoNatura2000);
        sb.append(",\n         codTipoNatura2000:'").append(codTipoNatura2000).append("'");
        sb.append(",\n         desTipoNatura2000:'").append(desTipoNatura2000).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}