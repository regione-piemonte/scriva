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
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * The type Catasto particella dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(Include.NON_NULL)
public class CatastoParticellaDTO implements Serializable {

    @JsonProperty("comune")
    private CatastoComuneDTO comune;

    @JsonProperty("sezione")
    private String sezione;

    @JsonProperty("foglio")
    private String foglio;

    @JsonProperty("particella")
    private String particella;

    @JsonProperty("id_geo_particella")
    private Integer idGeoParticella;

    @JsonProperty("aggiornato_al")
    private String aggiornatoAl;

    @JsonProperty("allegato")
    private String allegato;

    @JsonProperty("sviluppo")
    private String sviluppo;

    @JsonProperty("tipo_geometria")
    private String tipoGeometria;

    @JsonProperty("coordinate")
    private List<List<List<BigDecimal>>> coordinates;

    /**
     * Gets comune.
     *
     * @return the comune
     */
    public CatastoComuneDTO getComune() {
        return comune;
    }

    /**
     * Sets comune.
     *
     * @param comune the comune
     */
    public void setComune(CatastoComuneDTO comune) {
        this.comune = comune;
    }

    /**
     * Gets sezione.
     *
     * @return the sezione
     */
    public String getSezione() {
        return sezione;
    }

    /**
     * Sets sezione.
     *
     * @param sezione the sezione
     */
    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    /**
     * Gets foglio.
     *
     * @return the foglio
     */
    public String getFoglio() {
        return foglio;
    }

    /**
     * Sets foglio.
     *
     * @param foglio the foglio
     */
    public void setFoglio(String foglio) {
        this.foglio = foglio;
    }

    /**
     * Gets particella.
     *
     * @return the particella
     */
    public String getParticella() {
        return particella;
    }

    /**
     * Sets particella.
     *
     * @param particella the particella
     */
    public void setParticella(String particella) {
        this.particella = particella;
    }

    /**
     * Gets id geo particella.
     *
     * @return the id geo particella
     */
    public Integer getIdGeoParticella() {
        return idGeoParticella;
    }

    /**
     * Sets id geo particella.
     *
     * @param idGeoParticella the id geo particella
     */
    public void setIdGeoParticella(Integer idGeoParticella) {
        this.idGeoParticella = idGeoParticella;
    }

    /**
     * Gets aggiornato al.
     *
     * @return the aggiornato al
     */
    public String getAggiornatoAl() {
        return aggiornatoAl;
    }

    /**
     * Sets aggiornato al.
     *
     * @param aggiornatoAl the aggiornato al
     */
    public void setAggiornatoAl(String aggiornatoAl) {
        this.aggiornatoAl = aggiornatoAl;
    }

    /**
     * Gets allegato.
     *
     * @return the allegato
     */
    public String getAllegato() {
        return allegato;
    }

    /**
     * Sets allegato.
     *
     * @param allegato the allegato
     */
    public void setAllegato(String allegato) {
        this.allegato = allegato;
    }

    /**
     * Gets sviluppo.
     *
     * @return the sviluppo
     */
    public String getSviluppo() {
        return sviluppo;
    }

    /**
     * Sets sviluppo.
     *
     * @param sviluppo the sviluppo
     */
    public void setSviluppo(String sviluppo) {
        this.sviluppo = sviluppo;
    }

    /**
     * Gets tipo geometria.
     *
     * @return the tipo geometria
     */
    public String getTipoGeometria() {
        return tipoGeometria;
    }

    /**
     * Sets tipo geometria.
     *
     * @param tipoGeometria the tipo geometria
     */
    public void setTipoGeometria(String tipoGeometria) {
        this.tipoGeometria = tipoGeometria;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public List<List<List<BigDecimal>>> getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates.
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(List<List<List<BigDecimal>>> coordinates) {
        this.coordinates = coordinates;
    }
}