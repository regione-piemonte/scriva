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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Parcel
 */

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Parcel extends BaseCata {
    @JsonProperty("id_geo_particella")
    private Integer idGeoParticella = null;

    @JsonProperty("foglio")
    private String foglio = null;

    @JsonProperty("allegato")
    private String allegato = null;

    @JsonProperty("sviluppo")
    private String sviluppo = null;

    @JsonProperty("aggiornato_al")
    private String aggiornatoAl = null;

    @JsonProperty("particella")
    private String particella = null;

    public Parcel idGeoParticella(Integer idGeoParticella) {
        this.idGeoParticella = idGeoParticella;
        return this;
    }

    /**
     * Get idGeoParticella
     *
     * @return idGeoParticella
     **/
    public Integer getIdGeoParticella() {
        return idGeoParticella;
    }

    public void setIdGeoParticella(Integer idGeoParticella) {
        this.idGeoParticella = idGeoParticella;
    }

    public Parcel foglio(String foglio) {
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

    public Parcel allegato(String allegato) {
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

    public Parcel sviluppo(String sviluppo) {
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

    public Parcel aggiornatoAl(String aggiornatoAl) {
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

    public Parcel particella(String particella) {
        this.particella = particella;
        return this;
    }

    /**
     * Get particella
     *
     * @return particella
     **/
    public String getParticella() {
        return particella;
    }

    public void setParticella(String particella) {
        this.particella = particella;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parcel parcel = (Parcel) o;
        return Objects.equals(this.idGeoParticella, parcel.idGeoParticella) &&
                Objects.equals(this.foglio, parcel.foglio) &&
                Objects.equals(this.allegato, parcel.allegato) &&
                Objects.equals(this.sviluppo, parcel.sviluppo) &&
                Objects.equals(this.aggiornatoAl, parcel.aggiornatoAl) &&
                Objects.equals(this.particella, parcel.particella) &&
                super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGeoParticella, foglio, allegato, sviluppo, aggiornatoAl, particella, super.hashCode());
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Parcel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    idGeoParticella: ").append(toIndentedString(idGeoParticella)).append("\n");
        sb.append("    foglio: ").append(toIndentedString(foglio)).append("\n");
        sb.append("    allegato: ").append(toIndentedString(allegato)).append("\n");
        sb.append("    sviluppo: ").append(toIndentedString(sviluppo)).append("\n");
        sb.append("    aggiornatoAl: ").append(toIndentedString(aggiornatoAl)).append("\n");
        sb.append("    particella: ").append(toIndentedString(particella)).append("\n");
        sb.append("}");
        return sb.toString();
    }

}