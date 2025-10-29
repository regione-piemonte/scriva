/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Sheet
 */
@JsonInclude(Include.NON_NULL)
public class Sheet extends BaseCata {
    @JsonProperty("id_geo_foglio")
    private Integer idGeoFoglio = null;

    @JsonProperty("foglio")
    private String foglio = null;

    @JsonProperty("allegato")
    private String allegato = null;

    @JsonProperty("sviluppo")
    private String sviluppo = null;

    @JsonProperty("aggiornato_al")
    private String aggiornatoAl = null;

    @JsonProperty("areagis_ha")
    private BigDecimal areagisHa = null;

    public Sheet idGeoFoglio(Integer idGeoFoglio) {
        this.idGeoFoglio = idGeoFoglio;
        return this;
    }

    /**
     * Get idGeoFoglio
     *
     * @return idGeoFoglio
     **/
    public Integer getIdGeoFoglio() {
        return idGeoFoglio;
    }

    public void setIdGeoFoglio(Integer idGeoFoglio) {
        this.idGeoFoglio = idGeoFoglio;
    }

    public Sheet foglio(String foglio) {
        this.foglio = foglio;
        return this;
    }

    /**
     * Get foglio
     *
     * @return foglio
     **/
    public String getFoglio() {
        return foglio;
    }

    public void setFoglio(String foglio) {
        this.foglio = foglio;
    }

    public Sheet allegato(String allegato) {
        this.allegato = allegato;
        return this;
    }

    /**
     * Get allegato
     *
     * @return allegato
     **/
    public String getAllegato() {
        return allegato;
    }

    public void setAllegato(String allegato) {
        this.allegato = allegato;
    }

    public Sheet sviluppo(String sviluppo) {
        this.sviluppo = sviluppo;
        return this;
    }

    /**
     * Get sviluppo
     *
     * @return sviluppo
     **/
    public String getSviluppo() {
        return sviluppo;
    }

    public void setSviluppo(String sviluppo) {
        this.sviluppo = sviluppo;
    }

    public Sheet aggiornatoAl(String aggiornatoAl) {
        this.aggiornatoAl = aggiornatoAl;
        return this;
    }

    /**
     * The date is expressed accordingly the ISO 8601 standard using UTC time
     *
     * @return aggiornatoAl
     **/
    public String getAggiornatoAl() {
        return aggiornatoAl;
    }

    public void setAggiornatoAl(String aggiornatoAl) {
        this.aggiornatoAl = aggiornatoAl;
    }

    public Sheet areagisHa(BigDecimal areagisHa) {
        this.areagisHa = areagisHa;
        return this;
    }

    /**
     * Get areagisHa
     *
     * @return areagisHa
     **/
    public BigDecimal getAreagisHa() {
        return areagisHa;
    }

    public void setAreagisHa(BigDecimal areagisHa) {
        this.areagisHa = areagisHa;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sheet sheet = (Sheet) o;
        return Objects.equals(this.idGeoFoglio, sheet.idGeoFoglio) &&
                Objects.equals(this.foglio, sheet.foglio) &&
                Objects.equals(this.allegato, sheet.allegato) &&
                Objects.equals(this.sviluppo, sheet.sviluppo) &&
                Objects.equals(this.aggiornatoAl, sheet.aggiornatoAl) &&
                Objects.equals(this.areagisHa, sheet.areagisHa) &&
                super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGeoFoglio, foglio, allegato, sviluppo, aggiornatoAl, areagisHa, super.hashCode());
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Sheet {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    idGeoFoglio: ").append(toIndentedString(idGeoFoglio)).append("\n");
        sb.append("    foglio: ").append(toIndentedString(foglio)).append("\n");
        sb.append("    allegato: ").append(toIndentedString(allegato)).append("\n");
        sb.append("    sviluppo: ").append(toIndentedString(sviluppo)).append("\n");
        sb.append("    aggiornatoAl: ").append(toIndentedString(aggiornatoAl)).append("\n");
        sb.append("    areagisHa: ").append(toIndentedString(areagisHa)).append("\n");
        sb.append("}");
        return sb.toString();
    }

}