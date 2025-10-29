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
 * The type Tipo adempimento master slim dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoAdempimentoMasterSlimDTO implements Serializable {

    private Long idMasterData;

    private Integer ordinamento;

    private String codMasterData;

    /**
     * Gets id master data.
     *
     * @return idMasterData id master data
     */
    public Long getIdMasterData() {
        return idMasterData;
    }

    /**
     * Sets id master data.
     *
     * @param idMasterData idMasterData
     */
    public void setIdMasterData(Long idMasterData) {
        this.idMasterData = idMasterData;
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
     * Gets cod master data.
     *
     * @return codMasterData cod master data
     */
    public String getCodMasterData() {
        return codMasterData;
    }

    /**
     * Sets cod master data.
     *
     * @param codMasterData codMasterData
     */
    public void setCodMasterData(String codMasterData) {
        this.codMasterData = codMasterData;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codMasterData, idMasterData, ordinamento);
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
        TipoAdempimentoMasterSlimDTO other = (TipoAdempimentoMasterSlimDTO) obj;
        return Objects.equals(codMasterData, other.codMasterData) && Objects.equals(idMasterData, other.idMasterData) && Objects.equals(ordinamento, other.ordinamento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TipoAdempimentoMasterSlimDTO [idMasterData=").append(idMasterData).append("\n  ordinamento=").append(ordinamento).append("\n  codMasterData=").append(codMasterData).append("]");
        return builder.toString();
    }

}