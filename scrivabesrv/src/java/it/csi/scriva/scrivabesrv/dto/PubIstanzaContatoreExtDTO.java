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
import java.util.List;
import java.util.TreeMap;


/**
 * The type Pub istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubIstanzaContatoreExtDTO implements Serializable {

    @JsonProperty("val")
    private List<PubIstanzaContatoreDTO> contatore;


	/**
	 * Instantiates a new Pub istanza contatore ext dto.
	 */
	public PubIstanzaContatoreExtDTO() {

    }


	/**
	 * Instantiates a new Pub istanza contatore ext dto.
	 *
	 * @param contatore the contatore
	 */
	public PubIstanzaContatoreExtDTO(List<PubIstanzaContatoreDTO> contatore) {
        super();
        this.contatore = contatore;
    }


	/**
	 * Gets contatore.
	 *
	 * @return the contatore
	 */
	public List<PubIstanzaContatoreDTO> getContatore() {
        return contatore;
    }


	/**
	 * Sets contatore.
	 *
	 * @param contatore the contatore
	 */
	public void setContatore(List<PubIstanzaContatoreDTO> contatore) {
        this.contatore = contatore;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contatore == null) ? 0 : contatore.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PubIstanzaContatoreExtDTO other = (PubIstanzaContatoreExtDTO) obj;
        if (contatore == null) {
            if (other.contatore != null)
                return false;
        } else if (!contatore.equals(other.contatore))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "PubIstanzaContatoreExtDTO [contatore=" + contatore + "]";
    }


	/**
	 * Gets map tree.
	 *
	 * @return the map tree
	 */
	public TreeMap<String, String> getMapTree() {
        TreeMap<String, String> map = new TreeMap<>();
        List<PubIstanzaContatoreDTO> contatore = this.contatore;
        int count = 0;
        for (PubIstanzaContatoreDTO pubIstanzaContatoreDTO : contatore) {
            count++;
            map.put(count + "_fase", pubIstanzaContatoreDTO.getFase());
            map.put(count + "_contatore", pubIstanzaContatoreDTO.getContatore().toString());
        }
        return map;
    }


}