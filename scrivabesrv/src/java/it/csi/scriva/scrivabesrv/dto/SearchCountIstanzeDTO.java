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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/**
 * The type Search count istanze dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchCountIstanzeDTO extends BaseDTO implements Serializable {

    @JsonProperty("tutte")
    private Integer tutte;

    @JsonProperty("bozza")
    private Integer bozza;

    @JsonProperty("da_firmare")
    private Integer daFirmare;

    @JsonProperty("firmate")
    private Integer firmate;

    @JsonProperty("presentate")
    private Integer presentate;

    @JsonProperty("da_integrare")
    private Integer daIntegrare;

    /**
     * Gets tutte.
     *
     * @return the tutte
     */
    public Integer getTutte() {
        return tutte;
    }

    /**
     * Sets tutte.
     *
     * @param tutte the tutte
     */
    public void setTutte(Integer tutte) {
        this.tutte = tutte;
    }

    /**
     * Gets bozza.
     *
     * @return the bozza
     */
    public Integer getBozza() {
        return bozza;
    }

    /**
     * Sets bozza.
     *
     * @param bozza the bozza
     */
    public void setBozza(Integer bozza) {
        this.bozza = bozza;
    }

    /**
     * Gets da firmare.
     *
     * @return the da firmare
     */
    public Integer getDaFirmare() {
        return daFirmare;
    }

    /**
     * Sets da firmare.
     *
     * @param daFirmare the da firmare
     */
    public void setDaFirmare(Integer daFirmare) {
        this.daFirmare = daFirmare;
    }

    /**
     * Gets firmate.
     *
     * @return the firmate
     */
    public Integer getFirmate() {
        return firmate;
    }

    /**
     * Sets firmate.
     *
     * @param firmate the firmate
     */
    public void setFirmate(Integer firmate) {
        this.firmate = firmate;
    }

    /**
     * Gets presentate.
     *
     * @return the presentate
     */
    public Integer getPresentate() {
        return presentate;
    }

    /**
     * Sets presentate.
     *
     * @param presentate the presentate
     */
    public void setPresentate(Integer presentate) {
        this.presentate = presentate;
    }

    /**
     * Gets da integrare.
     *
     * @return the da integrare
     */
    public Integer getDaIntegrare() {
        return daIntegrare;
    }

    /**
     * Sets da integrare.
     *
     * @param daIntegrare the da integrare
     */
    public void setDaIntegrare(Integer daIntegrare) {
        this.daIntegrare = daIntegrare;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(bozza, daFirmare, daIntegrare, firmate, presentate, tutte);
    }

    /**
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        SearchCountIstanzeDTO other = (SearchCountIstanzeDTO) obj;
        return Objects.equals(bozza, other.bozza) && Objects.equals(daFirmare, other.daFirmare) && Objects.equals(daIntegrare, other.daIntegrare) && Objects.equals(firmate, other.firmate) && Objects.equals(presentate, other.presentate) && Objects.equals(tutte, other.tutte);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SearchCountIstanzeDTO {\n    tutte: ").append(tutte).append("\n    bozza: ").append(bozza).append("\n    daFirmare: ").append(daFirmare).append("\n    firmate: ").append(firmate).append("\n    presentate: ").append(presentate).append("\n    daIntegrare: ").append(daIntegrare).append("\n}");
        return builder.toString();
    }

    /**
     * Gets map.
     *
     * @return HashMap<String, Integer> map
     */
    public HashMap<String, Integer> getMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("tutte", this.tutte);
        map.put("bozza", this.bozza);
        map.put("presentate", this.presentate);
        map.put("da_firmare", this.daFirmare);
        map.put("firmate", this.firmate);
        map.put("da_integrare", this.daIntegrare);
        return map;
    }

}