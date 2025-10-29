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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Geo oggetto istanza dto.
 *
 * @author CSI PIEMONTE
 */
public class GeoOggettoIstanzaDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_geometrico")
    private Long idGeometrico;

    @JsonProperty("json_geo_geometria")
    private String jsonGeoGeometria;

    @JsonProperty("des_geeco")
    private String desGeeco;

    @JsonProperty("json_geo_feature")
    private String jsonGeoFeature;

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
     * Gets json geo geometria.
     *
     * @return the json geo geometria
     */
    public String getJsonGeoGeometria() {
        return jsonGeoGeometria;
    }

    /**
     * Sets json geo geometria.
     *
     * @param jsonGeoGeometria the json geo geometria
     */
    public void setJsonGeoGeometria(String jsonGeoGeometria) {
        this.jsonGeoGeometria = jsonGeoGeometria;
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
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        GeoOggettoIstanzaDTO that = (GeoOggettoIstanzaDTO) o;
        return Objects.equals(idGeometrico, that.idGeometrico) && Objects.equals(jsonGeoGeometria, that.jsonGeoGeometria) && Objects.equals(desGeeco, that.desGeeco) && Objects.equals(jsonGeoFeature, that.jsonGeoFeature);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idGeometrico, jsonGeoGeometria, desGeeco, jsonGeoFeature);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GeoOggettoIstanzaDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idGeometrico:").append(idGeometrico);
        sb.append(",         jsonGeoGeometria:'").append(jsonGeoGeometria).append("'");
        sb.append(",         desGeeco:'").append(desGeeco).append("'");
        sb.append(",         jsonGeoFeature:'").append(jsonGeoFeature).append("'");
        sb.append("}");
        return sb.toString();
    }
}