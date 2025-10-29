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
 * The type Tipologia oggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipologiaOggettoDTO implements Serializable {

    @JsonProperty("id_tipologia_oggetto")
    private Long idTipologiaOggetto;

    @JsonProperty("id_natura_oggetto")
    private Long idNaturaOggetto;

    @JsonProperty("cod_tipologia_oggetto")
    private String codTipologiaOggetto;

    @JsonProperty("des_tipologia_oggetto")
    private String desTipologiaOggetto;

    @JsonIgnore
    private Boolean flgAssegna;

    @JsonIgnore
    private Boolean flgRicerca;

    @JsonIgnore
    private Long idLayer;

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
     * Gets flg ricerca.
     *
     * @return the flg ricerca
     */
    public Boolean getFlgRicerca() {
        return flgRicerca;
    }

    /**
     * Sets flg ricerca.
     *
     * @param flgRicerca the flg ricerca
     */
    public void setFlgRicerca(Boolean flgRicerca) {
        this.flgRicerca = flgRicerca;
    }

    /**
     * Gets id layer.
     *
     * @return the id layer
     */
    public Long getIdLayer() {
        return idLayer;
    }

    /**
     * Sets id layer.
     *
     * @param idLayer the id layer
     */
    public void setIdLayer(Long idLayer) {
        this.idLayer = idLayer;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipologiaOggettoDTO that = (TipologiaOggettoDTO) o;
        return Objects.equals(idTipologiaOggetto, that.idTipologiaOggetto) && Objects.equals(idNaturaOggetto, that.idNaturaOggetto) && Objects.equals(codTipologiaOggetto, that.codTipologiaOggetto) && Objects.equals(desTipologiaOggetto, that.desTipologiaOggetto) && Objects.equals(flgAssegna, that.flgAssegna) && Objects.equals(flgRicerca, that.flgRicerca) && Objects.equals(idLayer, that.idLayer);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipologiaOggetto, idNaturaOggetto, codTipologiaOggetto, desTipologiaOggetto, flgAssegna, flgRicerca, idLayer);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TipologiaOggettoDTO {\n" +
                "         idTipologiaOggetto:" + idTipologiaOggetto +
                ",\n         idNaturaOggetto:" + idNaturaOggetto +
                ",\n         codTipologiaOggetto:'" + codTipologiaOggetto + "'" +
                ",\n         desTipologiaOggetto:'" + desTipologiaOggetto + "'" +
                ",\n         flgAssegna:" + flgAssegna +
                ",\n         flgRicerca:" + flgRicerca +
                ",\n         idLayer:" + idLayer +
                "}\n";
    }
}