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

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipo adempimento master dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoAdempimentoMasterDTO implements Serializable {

    private Long idTipoAdempimentoMaster;

    private Long idTipoAdempimento;

    private Long idMasterdata;

    private Long idInformazioneScriva;

    private Integer ordinamento;

    private String rifServizio;

    private String codInformazioneScriva;

    private String codMasterdata;


    /**
     * Gets id tipo adempimento master.
     *
     * @return idTipoAdempimentoMaster id tipo adempimento master
     */
    public Long getIdTipoAdempimentoMaster() {
        return idTipoAdempimentoMaster;
    }

    /**
     * Sets id tipo adempimento master.
     *
     * @param idTipoAdempimentoMaster idTipoAdempimentoMaster
     */
    public void setIdTipoAdempimentoMaster(Long idTipoAdempimentoMaster) {
        this.idTipoAdempimentoMaster = idTipoAdempimentoMaster;
    }

    /**
     * Gets id tipo adempimento.
     *
     * @return idTipoAdempimento id tipo adempimento
     */
    public Long getIdTipoAdempimento() {
        return idTipoAdempimento;
    }

    /**
     * Sets id tipo adempimento.
     *
     * @param idTipoAdempimento idTipoAdempimento
     */
    public void setIdTipoAdempimento(Long idTipoAdempimento) {
        this.idTipoAdempimento = idTipoAdempimento;
    }

    /**
     * Gets id masterdata.
     *
     * @return idMasterdata id masterdata
     */
    public Long getIdMasterdata() {
        return idMasterdata;
    }

    /**
     * Sets id masterdata.
     *
     * @param idMasterdata idMasterdata
     */
    public void setIdMasterdata(Long idMasterdata) {
        this.idMasterdata = idMasterdata;
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
     * Gets rif servizio.
     *
     * @return rifServizio rif servizio
     */
    public String getRifServizio() {
        return rifServizio;
    }

    /**
     * Sets rif servizio.
     *
     * @param rifServizio rifServizio
     */
    public void setRifServizio(String rifServizio) {
        this.rifServizio = rifServizio;
    }

    /**
     * Gets cod informazione scriva.
     *
     * @return codInformazioneScriva cod informazione scriva
     */
    public String getCodInformazioneScriva() {
        return codInformazioneScriva;
    }

    /**
     * Sets cod informazione scriva.
     *
     * @param codInformazioneScriva codInformazioneScriva
     */
    public void setCodInformazioneScriva(String codInformazioneScriva) {
        this.codInformazioneScriva = codInformazioneScriva;
    }

    /**
     * Gets cod masterdata.
     *
     * @return codMasterdata cod masterdata
     */
    public String getCodMasterdata() {
        return codMasterdata;
    }

    /**
     * Sets cod masterdata.
     *
     * @param codMasterdata codMasterdata
     */
    public void setCodMasterdata(String codMasterdata) {
        this.codMasterdata = codMasterdata;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codInformazioneScriva, codMasterdata, idInformazioneScriva, idMasterdata, idTipoAdempimentoMaster, idTipoAdempimento, ordinamento, rifServizio);
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
        TipoAdempimentoMasterDTO other = (TipoAdempimentoMasterDTO) obj;
        return Objects.equals(codInformazioneScriva, other.codInformazioneScriva) && Objects.equals(codMasterdata, other.codMasterdata) && Objects.equals(idInformazioneScriva, other.idInformazioneScriva) && Objects.equals(idMasterdata, other.idMasterdata) && Objects.equals(idTipoAdempimentoMaster, other.idTipoAdempimentoMaster) && Objects.equals(idTipoAdempimento, other.idTipoAdempimento) && Objects.equals(ordinamento, other.ordinamento) && Objects.equals(rifServizio, other.rifServizio);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TipoAdempimentoMasterDTO [idTipoAdempimentoMaster=").append(idTipoAdempimentoMaster).append("\n  idTipoAdempimento=").append(idTipoAdempimento).append("\n  idMasterdata=").append(idMasterdata).append("\n  idInformazioneScriva=").append(idInformazioneScriva).append("\n  ordinamento=").append(ordinamento).append("\n  rifServizio=").append(rifServizio).append("\n  codInformazioneScriva=").append(codInformazioneScriva).append("\n  codMasterdata=").append(codMasterdata).append("]");
        return builder.toString();
    }

}