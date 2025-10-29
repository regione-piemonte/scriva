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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoConfigDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_adempimento_config")
    private Long idAdempimentoConfig;

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("des_adempimento")
    private String desAdempimento;

    @JsonProperty("id_informazione_scriva")
    private Long idInformazioneScriva;

    @JsonProperty("des_informazione_scriva")
    private String desInformazioneScriva;

    @JsonProperty("chiave")
    private String chiave;

    @JsonProperty("valore")
    private String valore;

    @JsonProperty("ordinamento")
    private Integer ordinamento;

    @JsonIgnore
    private Boolean flgAttivo;

    @JsonProperty("note")
    @JsonIgnore
    private String note;

    /**
     * @return idAdempimentoConfig
     */
    public Long getIdAdempimentoConfig() {
        return idAdempimentoConfig;
    }

    /**
     * @param idAdempimentoConfig idAdempimentoConfig
     */
    public void setIdAdempimentoConfig(Long idAdempimentoConfig) {
        this.idAdempimentoConfig = idAdempimentoConfig;
    }

    /**
     * @return idTipoProcedimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * @param idAdempimento idTipoProcedimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    /**
     * @return desAdempimento
     */
    public String getDesAdempimento() {
        return desAdempimento;
    }

    /**
     * @param desAdempimento desAdempimento
     */
    public void setDesAdempimento(String desAdempimento) {
        this.desAdempimento = desAdempimento;
    }

    /**
     * @return idInformazioneScriva
     */
    public Long getIdInformazioneScriva() {
        return idInformazioneScriva;
    }

    /**
     * @param idInformazioneScriva idInformazioneScriva
     */
    public void setIdInformazioneScriva(Long idInformazioneScriva) {
        this.idInformazioneScriva = idInformazioneScriva;
    }

    /**
     * @return desInformazioneScriva
     */
    public String getDesInformazioneScriva() {
        return desInformazioneScriva;
    }

    /**
     * @param desInformazioneScriva desInformazioneScriva
     */
    public void setDesInformazioneScriva(String desInformazioneScriva) {
        this.desInformazioneScriva = desInformazioneScriva;
    }

    /**
     * @return chiave
     */
    public String getChiave() {
        return chiave;
    }

    /**
     * @param chiave chiave
     */
    public void setChiave(String chiave) {
        this.chiave = chiave;
    }

    /**
     * @return valore
     */
    public String getValore() {
        return valore;
    }

    /**
     * @param valore valore
     */
    public void setValore(String valore) {
        this.valore = valore;
    }

    /**
     * @return ordinamento
     */
    public Integer getOrdinamento() {
        return ordinamento;
    }

    /**
     * @param ordinamento ordinamento
     */
    public void setOrdinamento(Integer ordinamento) {
        this.ordinamento = ordinamento;
    }

    /**
     * @return flgAttivo
     */
    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    /**
     * @param flgAttivo flgAttivo
     */
    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    /**
     * @return note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(chiave, desInformazioneScriva, desAdempimento, flgAttivo, idInformazioneScriva, idAdempimentoConfig, idAdempimento, note, ordinamento, valore);
    }

    /**
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AdempimentoConfigDTO other = (AdempimentoConfigDTO) obj;
        return Objects.equals(chiave, other.chiave)
                && Objects.equals(desInformazioneScriva, other.desInformazioneScriva)
                && Objects.equals(desAdempimento, other.desAdempimento)
                && Objects.equals(flgAttivo, other.flgAttivo)
                && Objects.equals(idInformazioneScriva, other.idInformazioneScriva)
                && Objects.equals(idAdempimentoConfig, other.idAdempimentoConfig)
                && Objects.equals(idAdempimento, other.idAdempimento)
                && Objects.equals(note, other.note)
                && Objects.equals(ordinamento, other.ordinamento)
                && Objects.equals(valore, other.valore);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AdempimentoConfigDTO {\n    idAdempimentoConfig: ").append(idAdempimentoConfig).append("\n    idTipoProcedimento: ").append(idAdempimento).append("\n    desAdempimento: ").append(desAdempimento).append("\n    idInformazioneScriva: ").append(idInformazioneScriva).append("\n    desInformazioneScriva: ").append(desInformazioneScriva).append("\n    chiave: ").append(chiave).append("\n    valore: ").append(valore).append("\n    ordinamento: ").append(ordinamento).append("\n    flgAttivo: ").append(flgAttivo).append("\n    note: ").append(note).append("\n}");
        return builder.toString();
    }

}