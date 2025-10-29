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
 * @author CSI PIEMONTE
 */
public class TipoHelpDTO implements Serializable {
    @JsonProperty("id_tipo_help")
    private Long idTipoHelp;

    @JsonProperty("cod_tipo_help")
    private String codTipoHelp;

    @JsonProperty("des_tipo_help")
    private String desTipoHelp;

    /**
     * @return idTipoHelp
     */
    public Long getIdTipoHelp() {
        return idTipoHelp;
    }

    /**
     * @param idTipoHelp idTipoHelp
     */
    public void setIdTipoHelp(Long idTipoHelp) {
        this.idTipoHelp = idTipoHelp;
    }

    /**
     * @return codTipoHelp
     */
    public String getCodTipoHelp() {
        return codTipoHelp;
    }

    /**
     * @param codTipoHelp codTipoHelp
     */
    public void setCodTipoHelp(String codTipoHelp) {
        this.codTipoHelp = codTipoHelp;
    }

    /**
     * @return desTipoHelp
     */
    public String getDesTipoHelp() {
        return desTipoHelp;
    }

    /**
     * @param desTipoHelp desTipoHelp
     */
    public void setDesTipoHelp(String desTipoHelp) {
        this.desTipoHelp = desTipoHelp;
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
        TipoHelpDTO that = (TipoHelpDTO) o;
        return Objects.equals(idTipoHelp, that.idTipoHelp) && Objects.equals(codTipoHelp, that.codTipoHelp) && Objects.equals(desTipoHelp, that.desTipoHelp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idTipoHelp, codTipoHelp, desTipoHelp);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoHelpDTO {");
        sb.append("         idTipoHelp:").append(idTipoHelp);
        sb.append(",         codTipoHelp:'").append(codTipoHelp).append("'");
        sb.append(",         desTipoHelp:'").append(desTipoHelp).append("'");
        sb.append("}");
        return sb.toString();
    }
}