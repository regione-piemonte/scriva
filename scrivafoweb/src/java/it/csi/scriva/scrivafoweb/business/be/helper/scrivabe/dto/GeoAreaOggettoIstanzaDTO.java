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
public class GeoAreaOggettoIstanzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_geo_area_oggetto_istanza")
    private Long idGeoAreaOggettoIstanza;

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

    public Long getIdGeoAreaOggettoIstanza() {
        return idGeoAreaOggettoIstanza;
    }

    public void setIdGeoAreaOggettoIstanza(Long idGeoAreaOggettoIstanza) {
        this.idGeoAreaOggettoIstanza = idGeoAreaOggettoIstanza;
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
        return Objects.hash(desGeeco, geometry, idGeoAreaOggettoIstanza, idGeometrico,
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
        GeoAreaOggettoIstanzaDTO other = (GeoAreaOggettoIstanzaDTO) obj;
        return Objects.equals(desGeeco, other.desGeeco) && Objects.equals(geometry, other.geometry)
                && Objects.equals(idGeoAreaOggettoIstanza, other.idGeoAreaOggettoIstanza)
                && Objects.equals(idGeometrico, other.idGeometrico)
                && Objects.equals(idOggettoIstanza, other.idOggettoIstanza)
                && Objects.equals(idVirtuale, other.idVirtuale) && Objects.equals(jsonGeoFeature, other.jsonGeoFeature);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GeoAreaOggettoIstanzaDTO {");
        sb.append("         idGeoAreaOggettoIstanza:").append(idGeoAreaOggettoIstanza);
        sb.append(",         idOggettoIstanza:").append(idOggettoIstanza);
        sb.append(",         idGeometrico:").append(idGeometrico);
        sb.append(",         idVirtuale:").append(idVirtuale);
        sb.append(",         geometry:'").append(geometry).append("'");
        sb.append(",         jsonGeoFeature:'").append(jsonGeoFeature).append("'");
        sb.append(",         desGeeco:'").append(desGeeco).append("'");
        sb.append("}");
        return sb.toString();
    }
}