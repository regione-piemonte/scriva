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
 * The type Geo area oggetto istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GeoAreaOggettoIstanzaDTO extends GeoBaseOggettoIstanzaDTO implements Serializable {

    @JsonProperty("id_geo_area_oggetto_istanza")
    private Long idGeoAreaOggettoIstanza;

    /**
     * Gets id geo area oggetto istanza.
     *
     * @return the id geo area oggetto istanza
     */
    public Long getIdGeoAreaOggettoIstanza() {
        return idGeoAreaOggettoIstanza;
    }


    public GeoAreaOggettoIstanzaDTO (){
    }

    public GeoAreaOggettoIstanzaDTO (GeoBaseOggettoIstanzaDTO geoBaseOggettoIstanza){
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
     * Sets id geo area oggetto istanza.
     *
     * @param idGeoAreaOggettoIstanza the id geo area oggetto istanza
     */
    public void setIdGeoAreaOggettoIstanza(Long idGeoAreaOggettoIstanza) {
        this.idGeoAreaOggettoIstanza = idGeoAreaOggettoIstanza;
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
        GeoAreaOggettoIstanzaDTO that = (GeoAreaOggettoIstanzaDTO) o;
        return Objects.equals(idGeoAreaOggettoIstanza, that.idGeoAreaOggettoIstanza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idGeoAreaOggettoIstanza);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "GeoAreaOggettoIstanzaDTO {\n" +
                "         idGeoAreaOggettoIstanza:" + idGeoAreaOggettoIstanza +
                super.toString() +
                "}\n";
    }
}