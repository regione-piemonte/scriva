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
 * The type Tipo help dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoHelpDTO implements Serializable {

    @JsonProperty("id_tipo_help")
    private Long idTipoHelp;

    @JsonProperty("cod_tipo_help")
    private String codTipoHelp;

    @JsonProperty("des_tipo_help")
    private String desTipoHelp;

    /**
     * Gets id tipo help.
     *
     * @return idTipoHelp id tipo help
     */
    public Long getIdTipoHelp() {
        return idTipoHelp;
    }

    /**
     * Sets id tipo help.
     *
     * @param idTipoHelp idTipoHelp
     */
    public void setIdTipoHelp(Long idTipoHelp) {
        this.idTipoHelp = idTipoHelp;
    }

    /**
     * Gets cod tipo help.
     *
     * @return codTipoHelp cod tipo help
     */
    public String getCodTipoHelp() {
        return codTipoHelp;
    }

    /**
     * Sets cod tipo help.
     *
     * @param codTipoHelp codTipoHelp
     */
    public void setCodTipoHelp(String codTipoHelp) {
        this.codTipoHelp = codTipoHelp;
    }

    /**
     * Gets des tipo help.
     *
     * @return desTipoHelp des tipo help
     */
    public String getDesTipoHelp() {
        return desTipoHelp;
    }

    /**
     * Sets des tipo help.
     *
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