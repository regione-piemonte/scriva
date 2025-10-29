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
 * The type Help dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HelpDTO implements Serializable {

    @JsonProperty("id_help")
    private Long idHelp;

    @JsonProperty("chiave_help")
    private String chiaveHelp;

    @JsonProperty("valore_campo_help")
    private String valoreCampoHelp;

    @JsonProperty("des_testo_help")
    private String desTestoHelp;

    @JsonProperty("id_tipo_help")
    private Long idTipoHelp;

    @JsonProperty("id_livello_help")
    private Long idLivelloHelp;

    @JsonProperty("note_help")
    private String noteHelp;

    /**
     * Gets id help.
     *
     * @return idHelp id help
     */
    public Long getIdHelp() {
        return idHelp;
    }

    /**
     * Sets id help.
     *
     * @param idHelp idHelp
     */
    public void setIdHelp(Long idHelp) {
        this.idHelp = idHelp;
    }

    /**
     * Gets chiave help.
     *
     * @return chiaveHelp chiave help
     */
    public String getChiaveHelp() {
        return chiaveHelp;
    }

    /**
     * Sets chiave help.
     *
     * @param chiaveHelp chiaveHelp
     */
    public void setChiaveHelp(String chiaveHelp) {
        this.chiaveHelp = chiaveHelp;
    }

    /**
     * Gets valore campo help.
     *
     * @return valoreCampoHelp valore campo help
     */
    public String getValoreCampoHelp() {
        return valoreCampoHelp;
    }

    /**
     * Sets valore campo help.
     *
     * @param valoreCampoHelp valoreCampoHelp
     */
    public void setValoreCampoHelp(String valoreCampoHelp) {
        this.valoreCampoHelp = valoreCampoHelp;
    }

    /**
     * Gets des testo help.
     *
     * @return desTestoHelp des testo help
     */
    public String getDesTestoHelp() {
        return desTestoHelp;
    }

    /**
     * Sets des testo help.
     *
     * @param desTestoHelp desTestoHelp
     */
    public void setDesTestoHelp(String desTestoHelp) {
        this.desTestoHelp = desTestoHelp;
    }

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
     * Gets id livello help.
     *
     * @return idLivelloHelp id livello help
     */
    public Long getIdLivelloHelp() {
        return idLivelloHelp;
    }

    /**
     * Sets id livello help.
     *
     * @param idLivelloHelp idLivelloHelp
     */
    public void setIdLivelloHelp(Long idLivelloHelp) {
        this.idLivelloHelp = idLivelloHelp;
    }

    /**
     * Gets note help.
     *
     * @return noteHelp note help
     */
    public String getNoteHelp() {
        return noteHelp;
    }

    /**
     * Sets note help.
     *
     * @param noteHelp noteHelp
     */
    public void setNoteHelp(String noteHelp) {
        this.noteHelp = noteHelp;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HelpDTO helpDTO = (HelpDTO) o;
        return Objects.equals(idHelp, helpDTO.idHelp) && Objects.equals(chiaveHelp, helpDTO.chiaveHelp) && Objects.equals(valoreCampoHelp, helpDTO.valoreCampoHelp) && Objects.equals(desTestoHelp, helpDTO.desTestoHelp) && Objects.equals(idTipoHelp, helpDTO.idTipoHelp) && Objects.equals(idLivelloHelp, helpDTO.idLivelloHelp) && Objects.equals(noteHelp, helpDTO.noteHelp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idHelp, chiaveHelp, valoreCampoHelp, desTestoHelp, idTipoHelp, idLivelloHelp, noteHelp);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HelpDTO {");
        sb.append("         idHelp:").append(idHelp);
        sb.append(",         chiaveHelp:").append(chiaveHelp);
        sb.append(",         valoreCampoHelp:").append(valoreCampoHelp);
        sb.append(",         desTestoHelp:").append(desTestoHelp);
        sb.append(",         idTipoHelp:").append(idTipoHelp);
        sb.append(",         idLivelloHelp:").append(idLivelloHelp);
        sb.append(",         noteHelp:").append(noteHelp);
        sb.append("}");
        return sb.toString();
    }
}