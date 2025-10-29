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
public class LivelloHelpDTO implements Serializable {

    @JsonProperty("id_livello_help")
    private Long idLivelloHelp;

    @JsonProperty("cod_livello_help")
    private String codLivelloHelp;

    @JsonProperty("des_livello_help")
    private String desLivellooHelp;

    /**
     * @return idTipoHelp
     */
    public Long getIdLivelloHelp() {
        return idLivelloHelp;
    }

    /**
     * @param idLivelloHelp idTipoHelp
     */
    public void setIdLivelloHelp(Long idLivelloHelp) {
        this.idLivelloHelp = idLivelloHelp;
    }

    /**
     * @return codTipoHelp
     */
    public String getCodLivelloHelp() {
        return codLivelloHelp;
    }

    /**
     * @param codLivelloHelp codTipoHelp
     */
    public void setCodLivelloHelp(String codLivelloHelp) {
        this.codLivelloHelp = codLivelloHelp;
    }

    /**
     * @return desTipoHelp
     */
    public String getDesLivellooHelp() {
        return desLivellooHelp;
    }

    /**
     * @param desLivellooHelp desTipoHelp
     */
    public void setDesLivellooHelp(String desLivellooHelp) {
        this.desLivellooHelp = desLivellooHelp;
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
        LivelloHelpDTO that = (LivelloHelpDTO) o;
        return Objects.equals(idLivelloHelp, that.idLivelloHelp) && Objects.equals(codLivelloHelp, that.codLivelloHelp) && Objects.equals(desLivellooHelp, that.desLivellooHelp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idLivelloHelp, codLivelloHelp, desLivellooHelp);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LivelloHelpDTO {");
        sb.append("         idLivelloHelp:").append(idLivelloHelp);
        sb.append(",         codLivelloHelp:'").append(codLivelloHelp).append("'");
        sb.append(",         desLivellooHelp:'").append(desLivellooHelp).append("'");
        sb.append("}");
        return sb.toString();
    }
}