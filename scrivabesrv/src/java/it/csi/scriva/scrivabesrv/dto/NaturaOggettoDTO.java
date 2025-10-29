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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Natura oggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NaturaOggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_natura_oggetto")
    private Long idNaturaOggetto;

    @JsonProperty("cod_natura_oggetto")
    private String codNaturaOggetto;

    @JsonProperty("des_natura_oggetto")
    private String desNaturaOggetto;

    /**
     * Gets id natura oggetto.
     *
     * @return the id natura oggetto
     */
    public Long getIdNaturaOggetto() {
        return idNaturaOggetto;
    }

    /**
     * Sets id natura oggetto.
     *
     * @param idNaturaOggetto the id natura oggetto
     */
    public void setIdNaturaOggetto(Long idNaturaOggetto) {
        this.idNaturaOggetto = idNaturaOggetto;
    }

    /**
     * Gets cod natura oggetto.
     *
     * @return the cod natura oggetto
     */
    public String getCodNaturaOggetto() {
        return codNaturaOggetto;
    }

    /**
     * Sets cod natura oggetto.
     *
     * @param codNaturaOggetto the cod natura oggetto
     */
    public void setCodNaturaOggetto(String codNaturaOggetto) {
        this.codNaturaOggetto = codNaturaOggetto;
    }

    /**
     * Gets des natura oggetto.
     *
     * @return the des natura oggetto
     */
    public String getDesNaturaOggetto() {
        return desNaturaOggetto;
    }

    /**
     * Sets des natura oggetto.
     *
     * @param desNaturaOggetto the des natura oggetto
     */
    public void setDesNaturaOggetto(String desNaturaOggetto) {
        this.desNaturaOggetto = desNaturaOggetto;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codNaturaOggetto, desNaturaOggetto, idNaturaOggetto);
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
        NaturaOggettoDTO other = (NaturaOggettoDTO) obj;
        return Objects.equals(codNaturaOggetto, other.codNaturaOggetto) && Objects.equals(desNaturaOggetto, other.desNaturaOggetto) && Objects.equals(idNaturaOggetto, other.idNaturaOggetto);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NaturaOggettoDTO {\n    idNaturaOggetto: ").append(idNaturaOggetto).append("\n    codNaturaOggetto: ").append(codNaturaOggetto).append("\n    desNaturaOggetto: ").append(desNaturaOggetto).append("\n}");
        return builder.toString();
    }

}