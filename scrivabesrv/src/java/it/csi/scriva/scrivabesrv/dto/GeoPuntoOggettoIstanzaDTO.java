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
 * The type Geo punto oggetto istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GeoPuntoOggettoIstanzaDTO extends GeoBaseOggettoIstanzaDTO implements Serializable {

    @JsonProperty("id_geo_punto_oggetto_istanza")
    private Long idGeoPuntoOggettoIstanza;

    /**
     * Instantiates a new Geo linea oggetto istanza dto.
     */
    public GeoPuntoOggettoIstanzaDTO (){
    }

    /**
     * Instantiates a new Geo linea oggetto istanza dto.
     *
     * @param geoBaseOggettoIstanza the geo base oggetto istanza
     */
    public GeoPuntoOggettoIstanzaDTO (GeoBaseOggettoIstanzaDTO geoBaseOggettoIstanza){
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
     * Gets id geo punto oggetto istanza.
     *
     * @return the id geo punto oggetto istanza
     */
    public Long getIdGeoPuntoOggettoIstanza() {
        return idGeoPuntoOggettoIstanza;
    }

    /**
     * Sets id geo punto oggetto istanza.
     *
     * @param idGeoPuntoOggettoIstanza the id geo punto oggetto istanza
     */
    public void setIdGeoPuntoOggettoIstanza(Long idGeoPuntoOggettoIstanza) {
        this.idGeoPuntoOggettoIstanza = idGeoPuntoOggettoIstanza;
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
        GeoPuntoOggettoIstanzaDTO that = (GeoPuntoOggettoIstanzaDTO) o;
        return Objects.equals(idGeoPuntoOggettoIstanza, that.idGeoPuntoOggettoIstanza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idGeoPuntoOggettoIstanza);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "GeoPuntoOggettoIstanzaDTO {\n" +
                "         idGeoPuntoOggettoIstanza:" + idGeoPuntoOggettoIstanza +
                super.toString() +
                "}\n";
    }
}