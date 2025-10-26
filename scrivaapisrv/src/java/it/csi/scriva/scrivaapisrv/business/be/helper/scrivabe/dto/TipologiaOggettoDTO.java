/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipologia oggetto dto.
 *
 * @author CSI PIEMONTE
 */
public class TipologiaOggettoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_tipologia_oggetto")
    private Long idTipologiaOggetto;

    @JsonProperty("id_natura_oggetto")
    private Long idNaturaOggetto;

    @JsonProperty("cod_tipologia_oggetto")
    private String codTipologiaOggetto;

    @JsonProperty("des_tipologia_oggetto")
    private String desTipologiaOggetto;

    /**
     * Gets id tipologia oggetto.
     *
     * @return the id tipologia oggetto
     */
    public Long getIdTipologiaOggetto() {
        return idTipologiaOggetto;
    }

    /**
     * Sets id tipologia oggetto.
     *
     * @param idTipologiaOggetto the id tipologia oggetto
     */
    public void setIdTipologiaOggetto(Long idTipologiaOggetto) {
        this.idTipologiaOggetto = idTipologiaOggetto;
    }

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
     * Gets cod tipologia oggetto.
     *
     * @return the cod tipologia oggetto
     */
    public String getCodTipologiaOggetto() {
        return codTipologiaOggetto;
    }

    /**
     * Sets cod tipologia oggetto.
     *
     * @param codTipologiaOggetto the cod tipologia oggetto
     */
    public void setCodTipologiaOggetto(String codTipologiaOggetto) {
        this.codTipologiaOggetto = codTipologiaOggetto;
    }

    /**
     * Gets des tipologia oggetto.
     *
     * @return the des tipologia oggetto
     */
    public String getDesTipologiaOggetto() {
        return desTipologiaOggetto;
    }

    /**
     * Sets des tipologia oggetto.
     *
     * @param desTipologiaOggetto the des tipologia oggetto
     */
    public void setDesTipologiaOggetto(String desTipologiaOggetto) {
        this.desTipologiaOggetto = desTipologiaOggetto;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codTipologiaOggetto, desTipologiaOggetto, idNaturaOggetto, idTipologiaOggetto);
    }

    /**
     * @param obj Object
     * @return booelan
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
        TipologiaOggettoDTO other = (TipologiaOggettoDTO) obj;
        return Objects.equals(codTipologiaOggetto, other.codTipologiaOggetto) && Objects.equals(desTipologiaOggetto, other.desTipologiaOggetto) && Objects.equals(idNaturaOggetto, other.idNaturaOggetto) && Objects.equals(idTipologiaOggetto, other.idTipologiaOggetto);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TipologiaOggettoDTO {\n    idTipologiaOggetto: ").append(idTipologiaOggetto).append("\n    idNaturaOggetto: ").append(idNaturaOggetto).append("\n    codTipologiaOggetto: ").append(codTipologiaOggetto).append("\n    desTipologiaOggetto: ").append(desTipologiaOggetto).append("\n}");
        return builder.toString();
    }

}