/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Oggetto istanza ubicazione extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OggettoIstanzaUbicazioneExtendedDTO extends OggettoIstanzaDTO implements Serializable {

    @JsonProperty("tipologia_oggetto")
    private TipologiaOggettoExtendedDTO tipologiaOggetto;

    @JsonProperty("ubicazione_oggetto")
    private List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanza;

    @JsonProperty("siti_natura_2000")
    private List<OggettoIstanzaNatura2000ExtendedDTO> oggettoIstanzaNatura2000List;

    @JsonProperty("aree_protette")
    private List<OggettoIstanzaAreaProtettaExtendedDTO> oggettoIstanzaAreaProtettaList;

    @JsonProperty("vincoli")
    private List<OggettoIstanzaVincoloAutorizzaExtendedDTO> oggettoIstanzaVincoloAutorizzaList;

    @JsonProperty("id_oggetto_istanza_padre")
    private Long idOggettoIstanzaPadre;

    /**
     * Gets tipologia oggetto.
     *
     * @return the tipologia oggetto
     */
    public TipologiaOggettoExtendedDTO getTipologiaOggetto() {
        return tipologiaOggetto;
    }

    /**
     * Sets tipologia oggetto.
     *
     * @param tipologiaOggetto the tipologia oggetto
     */
    public void setTipologiaOggetto(TipologiaOggettoExtendedDTO tipologiaOggetto) {
        this.tipologiaOggetto = tipologiaOggetto;
    }

    /**
     * Gets ubicazione oggetto istanza.
     *
     * @return the ubicazione oggetto istanza
     */
    public List<UbicazioneOggettoIstanzaExtendedDTO> getUbicazioneOggettoIstanza() {
        return ubicazioneOggettoIstanza;
    }

    /**
     * Sets ubicazione oggetto istanza.
     *
     * @param ubicazioneOggettoIstanza the ubicazione oggetto istanza
     */
    public void setUbicazioneOggettoIstanza(List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanza) {
        this.ubicazioneOggettoIstanza = ubicazioneOggettoIstanza;
    }

    /**
     * Gets oggetto istanza natura 2000 list.
     *
     * @return the oggetto istanza natura 2000 list
     */
    public List<OggettoIstanzaNatura2000ExtendedDTO> getOggettoIstanzaNatura2000List() {
        return oggettoIstanzaNatura2000List;
    }

    /**
     * Sets oggetto istanza natura 2000 list.
     *
     * @param oggettoIstanzaNatura2000List the oggetto istanza natura 2000 list
     */
    public void setOggettoIstanzaNatura2000List(List<OggettoIstanzaNatura2000ExtendedDTO> oggettoIstanzaNatura2000List) {
        this.oggettoIstanzaNatura2000List = oggettoIstanzaNatura2000List;
    }

    /**
     * Gets oggetto istanza area protetta list.
     *
     * @return the oggetto istanza area protetta list
     */
    public List<OggettoIstanzaAreaProtettaExtendedDTO> getOggettoIstanzaAreaProtettaList() {
        return oggettoIstanzaAreaProtettaList;
    }

    /**
     * Sets oggetto istanza area protetta list.
     *
     * @param oggettoIstanzaAreaProtettaList the oggetto istanza area protetta list
     */
    public void setOggettoIstanzaAreaProtettaList(List<OggettoIstanzaAreaProtettaExtendedDTO> oggettoIstanzaAreaProtettaList) {
        this.oggettoIstanzaAreaProtettaList = oggettoIstanzaAreaProtettaList;
    }

    /**
     * Gets oggetto istanza vincolo autorizza list.
     *
     * @return the oggetto istanza vincolo autorizza list
     */
    public List<OggettoIstanzaVincoloAutorizzaExtendedDTO> getOggettoIstanzaVincoloAutorizzaList() {
        return oggettoIstanzaVincoloAutorizzaList;
    }

    /**
     * Sets oggetto istanza vincolo autorizza list.
     *
     * @param oggettoIstanzaVincoloAutorizzaList the oggetto istanza vincolo autorizza list
     */
    public void setOggettoIstanzaVincoloAutorizzaList(List<OggettoIstanzaVincoloAutorizzaExtendedDTO> oggettoIstanzaVincoloAutorizzaList) {
        this.oggettoIstanzaVincoloAutorizzaList = oggettoIstanzaVincoloAutorizzaList;
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
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OggettoIstanzaUbicazioneExtendedDTO that = (OggettoIstanzaUbicazioneExtendedDTO) o;
        return Objects.equals(tipologiaOggetto, that.tipologiaOggetto) && Objects.equals(ubicazioneOggettoIstanza, that.ubicazioneOggettoIstanza) && Objects.equals(oggettoIstanzaNatura2000List, that.oggettoIstanzaNatura2000List) && Objects.equals(oggettoIstanzaAreaProtettaList, that.oggettoIstanzaAreaProtettaList) && Objects.equals(oggettoIstanzaVincoloAutorizzaList, that.oggettoIstanzaVincoloAutorizzaList) && Objects.equals(idOggettoIstanzaPadre, that.idOggettoIstanzaPadre);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipologiaOggetto, ubicazioneOggettoIstanza, oggettoIstanzaNatura2000List, oggettoIstanzaAreaProtettaList, oggettoIstanzaVincoloAutorizzaList, idOggettoIstanzaPadre);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "OggettoIstanzaUbicazioneExtendedDTO {\n" +
                super.toString() +
                "         tipologiaOggetto:" + tipologiaOggetto +
                ",\n         ubicazioneOggettoIstanza:" + ubicazioneOggettoIstanza +
                ",\n         oggettoIstanzaNatura2000List:" + oggettoIstanzaNatura2000List +
                ",\n         oggettoIstanzaAreaProtettaList:" + oggettoIstanzaAreaProtettaList +
                ",\n         oggettoIstanzaVincoloAutorizzaList:" + oggettoIstanzaVincoloAutorizzaList +
                ",\n         idOggettoIstanzaPadre:" + idOggettoIstanzaPadre +
                "}\n";
    }

}