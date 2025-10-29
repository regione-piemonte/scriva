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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * The type Oggetto istanza ubicazione geo ref extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OggettoIstanzaUbicazioneGeoRefExtendedDTO extends OggettoIstanzaUbicazioneExtendedDTO implements Serializable {

    @JsonProperty("geo_area_oggetto_istanza")
    private List<GeoAreaOggettoIstanzaDTO> geoAreaOggettoIstanza;

    @JsonProperty("geo_linea_oggetto_istanza")
    private List<GeoLineaOggettoIstanzaDTO> geoLineaOggettoIstanza;

    @JsonProperty("geo_punto_oggetto_istanza")
    private List<GeoPuntoOggettoIstanzaDTO> geoPuntoOggettoIstanza;

    /**
     * Gets geo area oggetto istanza.
     *
     * @return the geo area oggetto istanza
     */
    public List<GeoAreaOggettoIstanzaDTO> getGeoAreaOggettoIstanza() {
        return geoAreaOggettoIstanza;
    }

    /**
     * Sets geo area oggetto istanza.
     *
     * @param geoAreaOggettoIstanza the geo area oggetto istanza
     */
    public void setGeoAreaOggettoIstanza(List<GeoAreaOggettoIstanzaDTO> geoAreaOggettoIstanza) {
        this.geoAreaOggettoIstanza = geoAreaOggettoIstanza;
    }

    /**
     * Gets geo linea oggetto istanza.
     *
     * @return the geo linea oggetto istanza
     */
    public List<GeoLineaOggettoIstanzaDTO> getGeoLineaOggettoIstanza() {
        return geoLineaOggettoIstanza;
    }

    /**
     * Sets geo linea oggetto istanza.
     *
     * @param geoLineaOggettoIstanza the geo linea oggetto istanza
     */
    public void setGeoLineaOggettoIstanza(List<GeoLineaOggettoIstanzaDTO> geoLineaOggettoIstanza) {
        this.geoLineaOggettoIstanza = geoLineaOggettoIstanza;
    }

    /**
     * Gets geo punto oggetto istanza.
     *
     * @return the geo punto oggetto istanza
     */
    public List<GeoPuntoOggettoIstanzaDTO> getGeoPuntoOggettoIstanza() {
        return geoPuntoOggettoIstanza;
    }

    /**
     * Sets geo punto oggetto istanza.
     *
     * @param geoPuntoOggettoIstanza the geo punto oggetto istanza
     */
    public void setGeoPuntoOggettoIstanza(List<GeoPuntoOggettoIstanzaDTO> geoPuntoOggettoIstanza) {
        this.geoPuntoOggettoIstanza = geoPuntoOggettoIstanza;
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
        OggettoIstanzaUbicazioneGeoRefExtendedDTO that = (OggettoIstanzaUbicazioneGeoRefExtendedDTO) o;
        return Objects.equals(geoAreaOggettoIstanza, that.geoAreaOggettoIstanza) && Objects.equals(geoLineaOggettoIstanza, that.geoLineaOggettoIstanza) && Objects.equals(geoPuntoOggettoIstanza, that.geoPuntoOggettoIstanza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), geoAreaOggettoIstanza, geoLineaOggettoIstanza, geoPuntoOggettoIstanza);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoIstanzaUbicazioneGeoRefExtendedDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         geoAreaOggettoIstanza:").append(geoAreaOggettoIstanza);
        sb.append(",         geoLineaOggettoIstanza:").append(geoLineaOggettoIstanza);
        sb.append(",         geoPuntoOggettoIstanza:").append(geoPuntoOggettoIstanza);
        sb.append("}");
        return sb.toString();
    }

    /**
     * To json string string.
     *
     * @return string string
     */
    @JsonIgnore
    public String toJsonString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * To json obj json object.
     *
     * @return JSONObject json object
     */
    @JsonIgnore
    public JSONObject toJsonObj() {
        String sJson = this.toJsonString();
        JSONObject obj = new org.json.JSONObject(sJson);
        obj.remove("istanza");
        obj.put("id_istanza", this.getIdIstanza());
        return obj;
    }
}