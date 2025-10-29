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
 * The type Adempimento config dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
     * Gets id adempimento config.
     *
     * @return idAdempimentoConfig id adempimento config
     */
    public Long getIdAdempimentoConfig() {
        return idAdempimentoConfig;
    }

    /**
     * Sets id adempimento config.
     *
     * @param idAdempimentoConfig idAdempimentoConfig
     */
    public void setIdAdempimentoConfig(Long idAdempimentoConfig) {
        this.idAdempimentoConfig = idAdempimentoConfig;
    }

    /**
     * Gets id adempimento.
     *
     * @return idAdempimento id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento idAdempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    /**
     * Gets des adempimento.
     *
     * @return desAdempimento des adempimento
     */
    public String getDesAdempimento() {
        return desAdempimento;
    }

    /**
     * Sets des adempimento.
     *
     * @param desAdempimento desAdempimento
     */
    public void setDesAdempimento(String desAdempimento) {
        this.desAdempimento = desAdempimento;
    }

    /**
     * Gets id informazione scriva.
     *
     * @return idInformazioneScriva id informazione scriva
     */
    public Long getIdInformazioneScriva() {
        return idInformazioneScriva;
    }

    /**
     * Sets id informazione scriva.
     *
     * @param idInformazioneScriva idInformazioneScriva
     */
    public void setIdInformazioneScriva(Long idInformazioneScriva) {
        this.idInformazioneScriva = idInformazioneScriva;
    }

    /**
     * Gets des informazione scriva.
     *
     * @return desInformazioneScriva des informazione scriva
     */
    public String getDesInformazioneScriva() {
        return desInformazioneScriva;
    }

    /**
     * Sets des informazione scriva.
     *
     * @param desInformazioneScriva desInformazioneScriva
     */
    public void setDesInformazioneScriva(String desInformazioneScriva) {
        this.desInformazioneScriva = desInformazioneScriva;
    }

    /**
     * Gets chiave.
     *
     * @return chiave chiave
     */
    public String getChiave() {
        return chiave;
    }

    /**
     * Sets chiave.
     *
     * @param chiave chiave
     */
    public void setChiave(String chiave) {
        this.chiave = chiave;
    }

    /**
     * Gets valore.
     *
     * @return valore valore
     */
    public String getValore() {
        return valore;
    }

    /**
     * Sets valore.
     *
     * @param valore valore
     */
    public void setValore(String valore) {
        this.valore = valore;
    }

    /**
     * Gets ordinamento.
     *
     * @return ordinamento ordinamento
     */
    public Integer getOrdinamento() {
        return ordinamento;
    }

    /**
     * Sets ordinamento.
     *
     * @param ordinamento ordinamento
     */
    public void setOrdinamento(Integer ordinamento) {
        this.ordinamento = ordinamento;
    }

    /**
     * Gets flg attivo.
     *
     * @return flgAttivo flg attivo
     */
    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    /**
     * Sets flg attivo.
     *
     * @param flgAttivo flgAttivo
     */
    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    /**
     * Gets note.
     *
     * @return note note
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets note.
     *
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
        builder.append("AdempimentoConfigDTO {\n    idAdempimentoConfig: ").append(idAdempimentoConfig).append("\n    idAdempimento: ").append(idAdempimento).append("\n    desAdempimento: ").append(desAdempimento).append("\n    idInformazioneScriva: ").append(idInformazioneScriva).append("\n    desInformazioneScriva: ").append(desInformazioneScriva).append("\n    chiave: ").append(chiave).append("\n    valore: ").append(valore).append("\n    ordinamento: ").append(ordinamento).append("\n    flgAttivo: ").append(flgAttivo).append("\n    note: ").append(note).append("\n}");
        return builder.toString();
    }

}