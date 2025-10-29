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

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Geo punto oggetto istanza extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GeoPuntoOggettoIstanzaExtendedDTO extends GeoPuntoOggettoIstanzaDTO implements Serializable {

    @JsonProperty("oggetto_istanza")
    private OggettoIstanzaExtendedDTO oggettoIstanza;

    /**
     * Gets oggetto istanza.
     *
     * @return the oggetto istanza
     */
    public OggettoIstanzaExtendedDTO getOggettoIstanza() {
        return oggettoIstanza;
    }

    /**
     * Sets oggetto istanza.
     *
     * @param oggettoIstanza the oggetto istanza
     */
    public void setOggettoIstanza(OggettoIstanzaExtendedDTO oggettoIstanza) {
        this.oggettoIstanza = oggettoIstanza;
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
        GeoPuntoOggettoIstanzaExtendedDTO that = (GeoPuntoOggettoIstanzaExtendedDTO) o;
        return Objects.equals(oggettoIstanza, that.oggettoIstanza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oggettoIstanza);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "GeoPuntoOggettoIstanzaExtendedDTO {\n" +
                super.toString() +
                "         oggettoIstanza:" + oggettoIstanza +
                "}\n";
    }

    /**
     * Gets dto.
     *
     * @return GeoPuntoOggettoIstanzaDTO dto
     */
    @JsonIgnore
    public GeoPuntoOggettoIstanzaDTO getDTO() {
        GeoPuntoOggettoIstanzaDTO dto = new GeoPuntoOggettoIstanzaDTO();
        dto.setIdGeoPuntoOggettoIstanza(this.getIdGeoPuntoOggettoIstanza());
        if (null != this.getOggettoIstanza()) {
            dto.setIdOggettoIstanza(this.getOggettoIstanza().getIdOggettoIstanza());
        }
        dto.setIdGeometrico(this.getIdGeometrico());
        dto.setIdVirtuale(this.getIdVirtuale());
        dto.setGeometry(this.getGeometry());
        dto.setDesGeeco(this.getDesGeeco());
        dto.setJsonGeoFeature(this.getJsonGeoFeature());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        return dto;
    }

}