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
import java.util.List;
import java.util.Objects;

/**
 * The type Geeco url req dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeecoUrlReqDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_oggetto_istanza")
    private List<Long> idOggettoIstanzaList;

    @JsonProperty("id_soggetto")
    private Long idSoggetto;

    @JsonProperty("chiave_config")
    private String chiaveConfig;

    @JsonProperty("cod_tipologia_oggetto")
    private String codTipologiaOggetto;

    @JsonProperty("quit_url_params")
    private String quitUrlParams;

    /**
     * Gets id istanza.
     *
     * @return the id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza the id istanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Retrieves the  subject identifier.
     *
     * @return the subject identifiers
     */
    public Long getIdSoggetto() {
        return idSoggetto;
    }

    /**
     * Sets the  subject ID.
     *
     * @param idSoggetto the ID assigned to the subject
     */
    public void setIdSoggetto(Long idSoggetto) {
        this.idSoggetto = idSoggetto;
    }

    /**
     * Gets id oggetto istanza list.
     *
     * @return the id oggetto istanza list
     */
    public List<Long> getIdOggettoIstanzaList() {
        return idOggettoIstanzaList;
    }

    /**
     * Sets id oggetto istanza list.
     *
     * @param idOggettoIstanzaList the id oggetto istanza list
     */
    public void setIdOggettoIstanzaList(List<Long> idOggettoIstanzaList) {
        this.idOggettoIstanzaList = idOggettoIstanzaList;
    }

    /**
     * Gets chiave config.
     *
     * @return the chiave config
     */
    public String getChiaveConfig() {
        return chiaveConfig;
    }

    /**
     * Sets chiave config.
     *
     * @param chiaveConfig the chiave config
     */
    public void setChiaveConfig(String chiaveConfig) {
        this.chiaveConfig = chiaveConfig;
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
     * Gets quit url param.
     *
     * @return the quit url param
     */
    public String getQuitUrlParams() {
        return quitUrlParams;
    }

    /**
     * Sets quit url param.
     *
     * @param quitUrlParams the quit url param
     */
    public void setQuitUrlParam(String quitUrlParams) {
        this.quitUrlParams = quitUrlParams;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeecoUrlReqDTO that = (GeecoUrlReqDTO) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idOggettoIstanzaList, that.idOggettoIstanzaList) && Objects.equals(chiaveConfig, that.chiaveConfig) && Objects.equals(codTipologiaOggetto, that.codTipologiaOggetto) && Objects.equals(quitUrlParams, that.quitUrlParams);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, idOggettoIstanzaList, chiaveConfig, codTipologiaOggetto, quitUrlParams);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "GeecoUrlReqDTO {\n" +
                "         idIstanza:" + idIstanza +
                ",\n         idOggettoIstanzaList:" + idOggettoIstanzaList +
                ",\n         chiaveConfig:'" + chiaveConfig + "'" +
                ",\n         codTipologiaOggetto:'" + codTipologiaOggetto + "'" +
                ",\n         quitUrlParams:'" + quitUrlParams + "'" +
                "}\n";
    }
}