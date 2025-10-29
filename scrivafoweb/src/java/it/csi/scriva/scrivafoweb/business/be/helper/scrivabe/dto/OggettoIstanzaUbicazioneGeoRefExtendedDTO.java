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
 * The type Oggetto istanza ubicazione geo ref extended dto.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OggettoIstanzaUbicazioneGeoRefExtendedDTO that = (OggettoIstanzaUbicazioneGeoRefExtendedDTO) o;
        return Objects.equals(geoAreaOggettoIstanza, that.geoAreaOggettoIstanza) && Objects.equals(geoLineaOggettoIstanza, that.geoLineaOggettoIstanza) && Objects.equals(geoPuntoOggettoIstanza, that.geoPuntoOggettoIstanza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), geoAreaOggettoIstanza, geoLineaOggettoIstanza, geoPuntoOggettoIstanza);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoIstanzaUbicazioneGeoRefExtendedDTO {");
        sb.append("         geoAreaOggettoIstanza:").append(geoAreaOggettoIstanza);
        sb.append(",         geoLineaOggettoIstanza:").append(geoLineaOggettoIstanza);
        sb.append(",         geoPuntoOggettoIstanza:").append(geoPuntoOggettoIstanza);
        sb.append("}");
        return sb.toString();
    }
}