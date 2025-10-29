/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Oggetto istanza ubicazione extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OggettoIstanzaUbicazioneGeometrieExtendedDTO extends OggettoIstanzaUbicazioneExtendedDTO implements Serializable {



    @JsonProperty("geometrie_oggetto")
    private List<GeoOggettoIstanzaDTO> geometrieOggettoIstanzaList;

 


    /**
     * Retrieves the list of geometries associated with this object instance.
     *
     * @return a list of GeoOggettoIstanzaDTO objects representing the geometries
     */
    public List<GeoOggettoIstanzaDTO> getGeometrieOggettoIstanzaList() {
        return geometrieOggettoIstanzaList;
    }

    /**
     * Sets the list of GeoOggettoIstanzaDTO objects representing geometric data for the instance.
     *
     * @param geometrieOggettoIstanzaList the list of geometric data objects to associate with this instance
     */
    public void setGeometrieOggettoIstanzaList(List<GeoOggettoIstanzaDTO> geometrieOggettoIstanzaList) {
        this.geometrieOggettoIstanzaList = geometrieOggettoIstanzaList;
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
        OggettoIstanzaUbicazioneGeometrieExtendedDTO that = (OggettoIstanzaUbicazioneGeometrieExtendedDTO) o;
        return Objects.equals(getTipologiaOggetto(), that.getTipologiaOggetto()) &&
               Objects.equals(getUbicazioneOggettoIstanza(), that.getUbicazioneOggettoIstanza()) &&
               Objects.equals(getOggettoIstanzaNatura2000List(), that.getOggettoIstanzaNatura2000List()) &&
               Objects.equals(getOggettoIstanzaAreaProtettaList(), that.getOggettoIstanzaAreaProtettaList()) &&
               Objects.equals(getOggettoIstanzaVincoloAutorizzaList(), that.getOggettoIstanzaVincoloAutorizzaList()) &&
               Objects.equals(geometrieOggettoIstanzaList, that.geometrieOggettoIstanzaList) &&
               Objects.equals(getIdOggettoIstanzaPadre(), that.getIdOggettoIstanzaPadre());
    }
    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), 
                            getTipologiaOggetto(), 
                            getUbicazioneOggettoIstanza(), 
                            getOggettoIstanzaNatura2000List(), 
                            getOggettoIstanzaAreaProtettaList(), 
                            getOggettoIstanzaVincoloAutorizzaList(), 
                            geometrieOggettoIstanzaList, 
                            getIdOggettoIstanzaPadre());
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "OggettoIstanzaUbicazioneGeometrieExtendedDTO {\n" +
                super.toString() +
                "         tipologiaOggetto:" + getTipologiaOggetto() +
                ",\n         ubicazioneOggettoIstanza:" + getUbicazioneOggettoIstanza() +
                ",\n         oggettoIstanzaNatura2000List:" + getOggettoIstanzaNatura2000List() +
                ",\n         oggettoIstanzaAreaProtettaList:" + getOggettoIstanzaAreaProtettaList() +
                ",\n         oggettoIstanzaVincoloAutorizzaList:" + getOggettoIstanzaVincoloAutorizzaList() +
                ",\n         geometrieOggettoIstanzaList:"+ geometrieOggettoIstanzaList +
                ",\n         idOggettoIstanzaPadre:" + getIdOggettoIstanzaPadre() +
                "}\n";
    }

 
}