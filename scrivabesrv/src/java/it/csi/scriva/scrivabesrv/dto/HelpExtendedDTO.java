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
 * The type Help extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HelpExtendedDTO extends HelpDTO implements Serializable {

    @JsonProperty("tipo_help")
    private TipoHelpDTO tipoHelp;

    @JsonProperty("livello_help")
    private LivelloHelpDTO livelloHelp;

    /**
     * Gets tipo help.
     *
     * @return TipoHelpDTO tipo help
     */
    public TipoHelpDTO getTipoHelp() {
        return tipoHelp;
    }

    /**
     * Sets tipo help.
     *
     * @param tipoHelp TipoHelpDTO
     */
    public void setTipoHelp(TipoHelpDTO tipoHelp) {
        this.tipoHelp = tipoHelp;
    }

    /**
     * Gets livello help.
     *
     * @return LivelloHelpDTO livello help
     */
    public LivelloHelpDTO getLivelloHelp() {
        return livelloHelp;
    }

    /**
     * Sets livello help.
     *
     * @param livelloHelp LivelloHelpDTO
     */
    public void setLivelloHelp(LivelloHelpDTO livelloHelp) {
        this.livelloHelp = livelloHelp;
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
        HelpExtendedDTO that = (HelpExtendedDTO) o;
        return Objects.equals(tipoHelp, that.tipoHelp) && Objects.equals(livelloHelp, that.livelloHelp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoHelp, livelloHelp);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HelpExtendedDTO {");
        sb.append(super.toString());
        sb.append("         tipoHelp:").append(tipoHelp);
        sb.append(",         livelloHelp:").append(livelloHelp);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return HelpDTO dto
     */
    @JsonIgnore
    public HelpDTO getDTO() {
        HelpDTO dto = new HelpDTO();
        dto.setIdHelp(this.getIdHelp());
        dto.setChiaveHelp(this.getChiaveHelp());
        dto.setValoreCampoHelp(this.getValoreCampoHelp());
        dto.setDesTestoHelp(this.getDesTestoHelp());
        dto.setNoteHelp(this.getNoteHelp());
        if (null != this.getTipoHelp()) {
            dto.setIdTipoHelp(this.getTipoHelp().getIdTipoHelp());
        }
        if (null != this.getLivelloHelp()) {
            dto.setIdLivelloHelp(this.getLivelloHelp().getIdLivelloHelp());
        }
        return dto;
    }
}