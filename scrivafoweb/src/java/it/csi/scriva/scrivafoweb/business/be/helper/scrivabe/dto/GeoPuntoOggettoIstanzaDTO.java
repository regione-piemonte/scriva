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
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GeoPuntoOggettoIstanzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_geo_punto_oggetto_istanza")
    private Long idGeoPuntoOggettoIstanza;

    @JsonProperty("id_oggetto_istanza")
    private Long idOggettoIstanza;

    @JsonProperty("id_geometrico")
    private Long idGeometrico;

    @JsonProperty("id_virtuale")
    private Long idVirtuale;

    @JsonProperty("geometry")
    private String geometry;

    @JsonProperty("json_geo_feature")
    private String jsonGeoFeature;

    @JsonProperty("des_geeco")
    private String desGeeco;

    public Long getIdGeoPuntoOggettoIstanza() {
        return idGeoPuntoOggettoIstanza;
    }

    public void setIdGeoPuntoOggettoIstanza(Long idGeoPuntoOggettoIstanza) {
        this.idGeoPuntoOggettoIstanza = idGeoPuntoOggettoIstanza;
    }

    public Long getIdOggettoIstanza() {
        return idOggettoIstanza;
    }

    public void setIdOggettoIstanza(Long idOggettoIstanza) {
        this.idOggettoIstanza = idOggettoIstanza;
    }

    public Long getIdGeometrico() {
        return idGeometrico;
    }

    public void setIdGeometrico(Long idGeometrico) {
        this.idGeometrico = idGeometrico;
    }

    public Long getIdVirtuale() {
        return idVirtuale;
    }

    public void setIdVirtuale(Long idVirtuale) {
        this.idVirtuale = idVirtuale;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getJsonGeoFeature() {
        return jsonGeoFeature;
    }

    public void setJsonGeoFeature(String jsonGeoFeature) {
        this.jsonGeoFeature = jsonGeoFeature;
    }

    public String getDesGeeco() {
        return desGeeco;
    }

    public void setDesGeeco(String desGeeco) {
        this.desGeeco = desGeeco;
    }

    @Override
    public int hashCode() {
        return Objects.hash(desGeeco, geometry, idGeoPuntoOggettoIstanza, idGeometrico,
                idOggettoIstanza, idVirtuale, jsonGeoFeature);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GeoPuntoOggettoIstanzaDTO other = (GeoPuntoOggettoIstanzaDTO) obj;
        return Objects.equals(desGeeco, other.desGeeco) && Objects.equals(geometry, other.geometry)
                && Objects.equals(idGeoPuntoOggettoIstanza, other.idGeoPuntoOggettoIstanza)
                && Objects.equals(idGeometrico, other.idGeometrico)
                && Objects.equals(idOggettoIstanza, other.idOggettoIstanza)
                && Objects.equals(idVirtuale, other.idVirtuale) && Objects.equals(jsonGeoFeature, other.jsonGeoFeature);
    }

    @Override
    public String toString() {
        return "GeoPuntoOggettoIstanzaDTO {\n    idGeoPuntoOggettoIstanza: " + idGeoPuntoOggettoIstanza
                + "\n    idOggettoIstanza: " + idOggettoIstanza + "\n    idGeometrico: " + idGeometrico
                + "\n    idVirtuale: " + idVirtuale + "\n    geometry: " + geometry + "\n    jsonGeoFeature: "
                + jsonGeoFeature + "\n    desGeeco: " + desGeeco + "\n}";
    }



}