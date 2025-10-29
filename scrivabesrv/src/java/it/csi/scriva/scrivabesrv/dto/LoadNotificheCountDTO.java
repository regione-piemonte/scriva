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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The type Search count istanze dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadNotificheCountDTO implements Serializable {

    @JsonProperty("tutte")
    private Integer tutte;

    @JsonProperty("lette")
    private Integer lette;

    @JsonProperty("non_lette")
    private Integer nonLette;

    @JsonProperty("cancellate")
    private Integer cancellate;

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
     * @param tutte the tutte to set
     */
    public void setTutte(Integer tutte) {
        this.tutte = tutte;
    }

    /**
     * Gets lette.
     *
     * @return the lette
     */
    public Integer getLette() {
        return lette;
    }

    /**
     * Sets lette.
     *
     * @param lette the lette to set
     */
    public void setLette(Integer lette) {
        this.lette = lette;
    }

    /**
     * Gets non lette.
     *
     * @return the nonLette
     */
    public Integer getNonLette() {
        return nonLette;
    }

    /**
     * Sets non lette.
     *
     * @param nonLette the nonLette to set
     */
    public void setNonLette(Integer nonLette) {
        this.nonLette = nonLette;
    }

    /**
     * Gets cancellate.
     *
     * @return the cancellate
     */
    public Integer getCancellate() {
        return cancellate;
    }

    /**
     * Sets cancellate.
     *
     * @param cancellate the cancellate to set
     */
    public void setCancellate(Integer cancellate) {
        this.cancellate = cancellate;
    }

	/**
	 * @param o Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LoadNotificheCountDTO that = (LoadNotificheCountDTO) o;
		return Objects.equals(tutte, that.tutte) && Objects.equals(lette, that.lette) && Objects.equals(nonLette, that.nonLette) && Objects.equals(cancellate, that.cancellate);
	}

	/**
	 * @return int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(tutte, lette, nonLette, cancellate);
	}

	/**
	 * @return string
	 */
	@Override
	public String toString() {
		return "LoadNotificheCountDTO {\n" +
				"         tutte:" + tutte +
				",\n         lette:" + lette +
				",\n         nonLette:" + nonLette +
				",\n         cancellate:" + cancellate +
				"}\n";
	}

    /**
     * Gets map.
     *
     * @return the map
     */
    @JsonIgnore
	public Map<String, Integer> getMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("tutte", this.tutte);
        map.put("lette", this.lette);
        map.put("non_lette", this.nonLette);
        map.put("cancellate", this.cancellate);
        return map;
    }

    /**
     * Gets json.
     *
     * @return the json
     */
    @JsonIgnore
    public String getJson() {
        String result;
        try {
            result = new ObjectMapper().writeValueAsString(this.getMap());
        } catch (JsonProcessingException e) {
            result = null;
        }
        return result;
    }
}