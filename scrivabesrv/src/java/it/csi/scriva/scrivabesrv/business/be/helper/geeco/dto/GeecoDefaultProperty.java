/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto;

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoDTO;

import java.util.List;
import java.util.Objects;

/**
 * The type Geeco default property.
 *
 * @author CSI PIEMONTE
 */
public class GeecoDefaultProperty {

    /**
     * The Id istanza.
     */
    Long idIstanza;

    /**
     * The Id soggetto.
     */
    Long idSoggetto;

    /**
     * The Id oggetto istanza list.
     */
    List<Long> idOggettoIstanzaList;

    /**
     * The Den oggetto.
     */
    String denOggetto;

    /**
     * The Des oggetto.
     */
    String desOggetto;

    /**
     * The Attore scriva.
     */
    AttoreScriva attoreScriva;

    /**
     * The Id oggetto istanza padre.
     */
    Long idOggettoIstanzaPadre;

    /**
     * The Flg aggiorna oggetto.
     */
    boolean flgAggiornaOggetto;

    /**
     * The Id layer list.
     */
    List<Long> idLayerList;

    /**
     * The Id tipologia oggetto.
     */
    Long idTipologiaOggetto;

    /**
     * The Tipologia oggetto list.
     */
    List<String> tipologiaOggettoList;

    /**
     * The Tipologia oggetto obj list.
     */
    List<TipologiaOggettoDTO> tipologiaOggettoObjList;

    /**
     * The Quit url.
     */
    String quitUrl;

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
     * Gets des oggetto.
     *
     * @return the des oggetto
     */
    public String getDesOggetto() {
        return desOggetto;
    }

    /**
     * Sets des oggetto.
     *
     * @param desOggetto the des oggetto
     */
    public void setDesOggetto(String desOggetto) {
        this.desOggetto = desOggetto;
    }

    /**
     * Gets attore scriva.
     *
     * @return the attore scriva
     */
    public AttoreScriva getAttoreScriva() {
        return attoreScriva;
    }

    /**
     * Sets attore scriva.
     *
     * @param attoreScriva the attore scriva
     */
    public void setAttoreScriva(AttoreScriva attoreScriva) {
        this.attoreScriva = attoreScriva;
    }

    /**
     * Gets id oggetto istanza padre.
     *
     * @return the id oggetto istanza padre
     */
    public Long getIdOggettoIstanzaPadre() {
        return idOggettoIstanzaPadre;
    }

    /**
     * Sets id oggetto istanza padre.
     *
     * @param idOggettoIstanzaPadre the id oggetto istanza padre
     */
    public void setIdOggettoIstanzaPadre(Long idOggettoIstanzaPadre) {
        this.idOggettoIstanzaPadre = idOggettoIstanzaPadre;
    }

    /**
     * Is flg aggiorna oggetto boolean.
     *
     * @return the boolean
     */
    public boolean isFlgAggiornaOggetto() {
        return flgAggiornaOggetto;
    }

    /**
     * Sets flg aggiorna oggetto.
     *
     * @param flgAggiornaOggetto the flg aggiorna oggetto
     */
    public void setFlgAggiornaOggetto(boolean flgAggiornaOggetto) {
        this.flgAggiornaOggetto = flgAggiornaOggetto;
    }

    /**
     * Gets id layer list.
     *
     * @return the id layer list
     */
    public List<Long> getIdLayerList() {
        return idLayerList;
    }

    /**
     * Sets id layer list.
     *
     * @param idLayerList the id layer list
     */
    public void setIdLayerList(List<Long> idLayerList) {
        this.idLayerList = idLayerList;
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
     * Gets tipologia oggetto list.
     *
     * @return the tipologia oggetto list
     */
    public List<String> getTipologiaOggettoList() {
        return tipologiaOggettoList;
    }

    /**
     * Sets tipologia oggetto list.
     *
     * @param tipologiaOggettoList the tipologia oggetto list
     */
    public void setTipologiaOggettoList(List<String> tipologiaOggettoList) {
        this.tipologiaOggettoList = tipologiaOggettoList;
    }

    /**
     * Gets tipologia oggetto obj list.
     *
     * @return the tipologia oggetto obj list
     */
    public List<TipologiaOggettoDTO> getTipologiaOggettoObjList() {
        return tipologiaOggettoObjList;
    }

    /**
     * Sets tipologia oggetto obj list.
     *
     * @param tipologiaOggettoObjList the tipologia oggetto obj list
     */
    public void setTipologiaOggettoObjList(List<TipologiaOggettoDTO> tipologiaOggettoObjList) {
        this.tipologiaOggettoObjList = tipologiaOggettoObjList;
    }

    /**
     * Gets quit url.
     *
     * @return the quit url
     */
    public String getQuitUrl() {
        return quitUrl;
    }

    /**
     * Sets quit url.
     *
     * @param quitUrl the quit url
     */
    public void setQuitUrl(String quitUrl) {
        this.quitUrl = quitUrl;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeecoDefaultProperty that = (GeecoDefaultProperty) o;
        return flgAggiornaOggetto == that.flgAggiornaOggetto && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idOggettoIstanzaList, that.idOggettoIstanzaList) && Objects.equals(denOggetto, that.denOggetto) && Objects.equals(desOggetto, that.desOggetto) && Objects.equals(attoreScriva, that.attoreScriva) && Objects.equals(idOggettoIstanzaPadre, that.idOggettoIstanzaPadre) && Objects.equals(idLayerList, that.idLayerList) && Objects.equals(idTipologiaOggetto, that.idTipologiaOggetto) && Objects.equals(tipologiaOggettoList, that.tipologiaOggettoList) && Objects.equals(tipologiaOggettoObjList, that.tipologiaOggettoObjList) && Objects.equals(quitUrl, that.quitUrl);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, idOggettoIstanzaList, denOggetto, desOggetto, attoreScriva, idOggettoIstanzaPadre, flgAggiornaOggetto, idLayerList, idTipologiaOggetto, tipologiaOggettoList, tipologiaOggettoObjList, quitUrl);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "GeecoDefaultProperty {\n" +
                "         idIstanza:" + idIstanza +
                ",\n         idOggettoIstanzaList:" + idOggettoIstanzaList +
                ",\n         denOggetto:'" + denOggetto + "'" +
                ",\n         desOggetto:'" + desOggetto + "'" +
                ",\n         attoreScriva:" + attoreScriva +
                ",\n         idOggettoIstanzaPadre:" + idOggettoIstanzaPadre +
                ",\n         flgAggiornaOggetto:" + flgAggiornaOggetto +
                ",\n         idLayerList:" + idLayerList +
                ",\n         idTipologiaOggetto:" + idTipologiaOggetto +
                ",\n         tipologiaOggettoList:" + tipologiaOggettoList +
                ",\n         tipologiaOggettoObjList:" + tipologiaOggettoObjList +
                ",\n         quitUrl:'" + quitUrl + "'" +
                "}\n";
    }

  
}