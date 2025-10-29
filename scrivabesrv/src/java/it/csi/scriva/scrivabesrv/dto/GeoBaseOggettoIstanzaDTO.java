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
public class GeoBaseOggettoIstanzaDTO extends BaseDTO implements Serializable {

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

    /**
     * Gets id oggetto istanza.
     *
     * @return the id oggetto istanza
     */
    public Long getIdOggettoIstanza() {
        return idOggettoIstanza;
    }

    /**
     * Sets id oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     */
    public void setIdOggettoIstanza(Long idOggettoIstanza) {
        this.idOggettoIstanza = idOggettoIstanza;
    }

    /**
     * Gets id geometrico.
     *
     * @return the id geometrico
     */
    public Long getIdGeometrico() {
        return idGeometrico;
    }

    /**
     * Sets id geometrico.
     *
     * @param idGeometrico the id geometrico
     */
    public void setIdGeometrico(Long idGeometrico) {
        this.idGeometrico = idGeometrico;
    }

    /**
     * Gets id virtuale.
     *
     * @return the id virtuale
     */
    public Long getIdVirtuale() {
        return idVirtuale;
    }

    /**
     * Sets id virtuale.
     *
     * @param idVirtuale the id virtuale
     */
    public void setIdVirtuale(Long idVirtuale) {
        this.idVirtuale = idVirtuale;
    }

    /**
     * Gets geometry.
     *
     * @return the geometry
     */
    public String getGeometry() {
        return geometry;
    }

    /**
     * Sets geometry.
     *
     * @param geometry the geometry
     */
    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    /**
     * Gets json geo feature.
     *
     * @return the json geo feature
     */
    public String getJsonGeoFeature() {
        return jsonGeoFeature;
    }

    /**
     * Sets json geo feature.
     *
     * @param jsonGeoFeature the json geo feature
     */
    public void setJsonGeoFeature(String jsonGeoFeature) {
        this.jsonGeoFeature = jsonGeoFeature;
    }

    /**
     * Gets des geeco.
     *
     * @return the des geeco
     */
    public String getDesGeeco() {
        return desGeeco;
    }

    /**
     * Sets des geeco.
     *
     * @param desGeeco the des geeco
     */
    public void setDesGeeco(String desGeeco) {
        this.desGeeco = desGeeco;
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
        GeoBaseOggettoIstanzaDTO that = (GeoBaseOggettoIstanzaDTO) o;
        return Objects.equals(idOggettoIstanza, that.idOggettoIstanza) && Objects.equals(idGeometrico, that.idGeometrico) && Objects.equals(idVirtuale, that.idVirtuale) && Objects.equals(geometry, that.geometry) && Objects.equals(jsonGeoFeature, that.jsonGeoFeature) && Objects.equals(desGeeco, that.desGeeco);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idOggettoIstanza, idGeometrico, idVirtuale, geometry, jsonGeoFeature, desGeeco);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "GeoBaseOggettoIstanzaDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idOggettoIstanza:" + idOggettoIstanza +
                ",\n         idGeometrico:" + idGeometrico +
                ",\n         idVirtuale:" + idVirtuale +
                ",\n         geometry:'" + geometry + "'" +
                ",\n         jsonGeoFeature:'" + jsonGeoFeature + "'" +
                ",\n         desGeeco:'" + desGeeco + "'" +
                "}\n";
    }

}