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
 * The type Geo linea oggetto istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GeoLineaOggettoIstanzaDTO extends GeoBaseOggettoIstanzaDTO implements Serializable {

    @JsonProperty("id_geo_linea_oggetto_istanza")
    private Long idGeoLineaOggettoIstanza;

    /**
     * Instantiates a new Geo linea oggetto istanza dto.
     */
    public GeoLineaOggettoIstanzaDTO (){
    }

    /**
     * Instantiates a new Geo linea oggetto istanza dto.
     *
     * @param geoBaseOggettoIstanza the geo base oggetto istanza
     */
    public GeoLineaOggettoIstanzaDTO (GeoBaseOggettoIstanzaDTO geoBaseOggettoIstanza){
        this.setDesGeeco(geoBaseOggettoIstanza.getDesGeeco());
        this.setGeometry(geoBaseOggettoIstanza.getGeometry());
        this.setIdGeometrico(geoBaseOggettoIstanza.getIdGeometrico());
        this.setIdOggettoIstanza(geoBaseOggettoIstanza.getIdOggettoIstanza());
        this.setIdVirtuale(geoBaseOggettoIstanza.getIdVirtuale());
        this.setJsonGeoFeature(geoBaseOggettoIstanza.getJsonGeoFeature());
        this.setGestDataIns(geoBaseOggettoIstanza.getGestDataIns());
        this.setGestAttoreIns(geoBaseOggettoIstanza.getGestAttoreIns());
        this.setGestDataUpd(geoBaseOggettoIstanza.getGestDataUpd());
        this.setGestAttoreUpd(geoBaseOggettoIstanza.getGestAttoreUpd());
    }

    /**
     * Gets id geo linea oggetto istanza.
     *
     * @return the id geo linea oggetto istanza
     */
    public Long getIdGeoLineaOggettoIstanza() {
        return idGeoLineaOggettoIstanza;
    }

    /**
     * Sets id geo linea oggetto istanza.
     *
     * @param idGeoLineaOggettoIstanza the id geo linea oggetto istanza
     */
    public void setIdGeoLineaOggettoIstanza(Long idGeoLineaOggettoIstanza) {
        this.idGeoLineaOggettoIstanza = idGeoLineaOggettoIstanza;
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
        GeoLineaOggettoIstanzaDTO that = (GeoLineaOggettoIstanzaDTO) o;
        return Objects.equals(idGeoLineaOggettoIstanza, that.idGeoLineaOggettoIstanza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idGeoLineaOggettoIstanza);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "GeoLineaOggettoIstanzaDTO {\n" +
                "         idGeoLineaOggettoIstanza:" + idGeoLineaOggettoIstanza +
                super.toString() +
                "}\n";
    }
}