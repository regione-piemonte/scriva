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
 * The type Adempimento tipo oggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdempimentoTipoOggettoDTO implements Serializable {

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("id_tipologia_oggetto")
    private Long idTipologiaOggetto;

    @JsonProperty("flg_assegna")
    private Boolean flgAssegna;

    /**
     * Gets id adempimento.
     *
     * @return the id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento the id adempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

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
     * Gets flg assegna.
     *
     * @return the flg assegna
     */
    public Boolean getFlgAssegna() {
        return flgAssegna;
    }

    /**
     * Sets flg assegna.
     *
     * @param flgAssegna the flg assegna
     */
    public void setFlgAssegna(Boolean flgAssegna) {
        this.flgAssegna = flgAssegna;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdempimentoTipoOggettoDTO that = (AdempimentoTipoOggettoDTO) o;
        return Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(idTipologiaOggetto, that.idTipologiaOggetto) && Objects.equals(flgAssegna, that.flgAssegna);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idAdempimento, idTipologiaOggetto, flgAssegna);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "AdempimentoTipoOggettoDTO {\n" +
                "         idAdempimento:" + idAdempimento +
                ",\n         idTipologiaOggetto:" + idTipologiaOggetto +
                ",\n         flgAssegna:" + flgAssegna +
                "}\n";
    }
}