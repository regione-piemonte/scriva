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
import java.util.List;
import java.util.Objects;

/**
 * The type Search oggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchOggettoDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_comune")
    private Long idComune;

    @JsonProperty("id_soggetti")
    private List<Long> idSoggetti;

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("anno_presentazione")
    private Integer annoPresentazione;

    @JsonProperty("cod_scriva")
    private String codScriva;

    @JsonProperty("den_oggetto")
    private String denOggetto;

    @JsonProperty("cod_tipo_oggetto")
    private String codTipoOggetto; //parametro di ricerca tipologia di oggetti da utilizzare come chiave nella scriva_r_adempi_config

    @JsonProperty("flg_cat_ogg")
    private boolean flgCatOgg = Boolean.FALSE;

    @JsonProperty("cod_tipologia_oggetto")
    private String codTipologiaOggetto; //tipologia oggetto passate in input separate da |

    @JsonProperty("cf_soggetto")
    private String cfSoggetto;

    @JsonIgnore
    // lista codici tipo oggetto da ricercare
    private List<String> codTipoOggettoSearchList;

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
     * Gets id comune.
     *
     * @return the id comune
     */
    public Long getIdComune() {
        return idComune;
    }

    /**
     * Sets id comune.
     *
     * @param idComune the id comune
     */
    public void setIdComune(Long idComune) {
        this.idComune = idComune;
    }

    /**
     * Gets id soggetti.
     *
     * @return the id soggetti
     */
    public List<Long> getIdSoggetti() {
        return idSoggetti;
    }

    /**
     * Sets id soggetti.
     *
     * @param idSoggetti the id soggetti
     */
    public void setIdSoggetti(List<Long> idSoggetti) {
        this.idSoggetti = idSoggetti;
    }

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
     * Gets anno presentazione.
     *
     * @return the anno presentazione
     */
    public Integer getAnnoPresentazione() {
        return annoPresentazione;
    }

    /**
     * Sets anno presentazione.
     *
     * @param annoPresentazione the anno presentazione
     */
    public void setAnnoPresentazione(Integer annoPresentazione) {
        this.annoPresentazione = annoPresentazione;
    }

    /**
     * Gets cod scriva.
     *
     * @return the cod scriva
     */
    public String getCodScriva() {
        return codScriva;
    }

    /**
     * Sets cod scriva.
     *
     * @param codScriva the cod scriva
     */
    public void setCodScriva(String codScriva) {
        this.codScriva = codScriva;
    }

    /**
     * Gets den oggetto.
     *
     * @return the den oggetto
     */
    public String getDenOggetto() {
        return denOggetto;
    }

    /**
     * Sets den oggetto.
     *
     * @param denOggetto the den oggetto
     */
    public void setDenOggetto(String denOggetto) {
        this.denOggetto = denOggetto;
    }

    /**
     * Gets cod tipo oggetto.
     *
     * @return the cod tipo oggetto
     */
    public String getCodTipoOggetto() {
        return codTipoOggetto;
    }

    /**
     * Sets cod tipo oggetto.
     *
     * @param codTipoOggetto the cod tipo oggetto
     */
    public void setCodTipoOggetto(String codTipoOggetto) {
        this.codTipoOggetto = codTipoOggetto;
    }

    /**
     * Is flg cat ogg boolean.
     *
     * @return the boolean
     */
    public boolean isFlgCatOgg() {
        return flgCatOgg;
    }

    /**
     * Sets flg cat ogg.
     *
     * @param flgCatOgg the flg cat ogg
     */
    public void setFlgCatOgg(boolean flgCatOgg) {
        this.flgCatOgg = flgCatOgg;
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
     * Gets cf soggetto.
     *
     * @return the cf soggetto
     */
    public String getCfSoggetto() {
        return cfSoggetto;
    }

    /**
     * Sets cf soggetto.
     *
     * @param cfSoggetto the cf soggetto
     */
    public void setCfSoggetto(String cfSoggetto) {
        this.cfSoggetto = cfSoggetto;
    }

    /**
     * Gets cod tipo oggetto search list.
     *
     * @return the cod tipo oggetto search list
     */
    public List<String> getCodTipoOggettoSearchList() {
        return codTipoOggettoSearchList;
    }

    /**
     * Sets cod tipo oggetto search list.
     *
     * @param codTipoOggettoSearchList the cod tipo oggetto search list
     */
    public void setCodTipoOggettoSearchList(List<String> codTipoOggettoSearchList) {
        this.codTipoOggettoSearchList = codTipoOggettoSearchList;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchOggettoDTO that = (SearchOggettoDTO) o;
        return flgCatOgg == that.flgCatOgg && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idComune, that.idComune) && Objects.equals(idSoggetti, that.idSoggetti) && Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(annoPresentazione, that.annoPresentazione) && Objects.equals(codScriva, that.codScriva) && Objects.equals(denOggetto, that.denOggetto) && Objects.equals(codTipoOggetto, that.codTipoOggetto) && Objects.equals(codTipologiaOggetto, that.codTipologiaOggetto) && Objects.equals(cfSoggetto, that.cfSoggetto) && Objects.equals(codTipoOggettoSearchList, that.codTipoOggettoSearchList);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, idComune, idSoggetti, idAdempimento, annoPresentazione, codScriva, denOggetto, codTipoOggetto, flgCatOgg, codTipologiaOggetto, cfSoggetto, codTipoOggettoSearchList);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "SearchOggettoDTO {\n" +
                "         idIstanza:" + idIstanza +
                ",\n         idComune:" + idComune +
                ",\n         idSoggetti:" + idSoggetti +
                ",\n         idAdempimento:" + idAdempimento +
                ",\n         annoPresentazione:" + annoPresentazione +
                ",\n         codScriva:'" + codScriva + "'" +
                ",\n         denOggetto:'" + denOggetto + "'" +
                ",\n         codTipoOggetto:'" + codTipoOggetto + "'" +
                ",\n         flgCatOgg:" + flgCatOgg +
                ",\n         codTipologiaOggetto:'" + codTipologiaOggetto + "'" +
                ",\n         cfSoggetto:'" + cfSoggetto + "'" +
                ",\n         codTipoOggettoSearchList:" + codTipoOggettoSearchList +
                "}\n";
    }
}