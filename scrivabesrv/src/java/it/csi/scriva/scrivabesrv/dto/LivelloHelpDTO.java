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
 * The type Livello help dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LivelloHelpDTO implements Serializable {

    @JsonProperty("id_livello_help")
    private Long idLivelloHelp;

    @JsonProperty("cod_livello_help")
    private String codLivelloHelp;

    @JsonProperty("des_livello_help")
    private String desLivellooHelp;

    /**
     * Gets id livello help.
     *
     * @return idTipoHelp id livello help
     */
    public Long getIdLivelloHelp() {
        return idLivelloHelp;
    }

    /**
     * Sets id livello help.
     *
     * @param idLivelloHelp idTipoHelp
     */
    public void setIdLivelloHelp(Long idLivelloHelp) {
        this.idLivelloHelp = idLivelloHelp;
    }

    /**
     * Gets cod livello help.
     *
     * @return codTipoHelp cod livello help
     */
    public String getCodLivelloHelp() {
        return codLivelloHelp;
    }

    /**
     * Sets cod livello help.
     *
     * @param codLivelloHelp codTipoHelp
     */
    public void setCodLivelloHelp(String codLivelloHelp) {
        this.codLivelloHelp = codLivelloHelp;
    }

    /**
     * Gets des livelloo help.
     *
     * @return desTipoHelp des livelloo help
     */
    public String getDesLivellooHelp() {
        return desLivellooHelp;
    }

    /**
     * Sets des livelloo help.
     *
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