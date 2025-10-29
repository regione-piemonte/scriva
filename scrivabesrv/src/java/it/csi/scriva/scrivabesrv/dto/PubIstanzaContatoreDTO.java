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


/**
 * The type Pub istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubIstanzaContatoreDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("fase")
    private String fase;

    @JsonProperty("contatore")
    private Long contatore;

    /**
     * Instantiates a new Pub istanza contatore dto.
     *
     * @param fase      the fase
     * @param contatore the contatore
     */
    public PubIstanzaContatoreDTO(String fase, Long contatore) {
        super();
        this.fase = fase;
        this.contatore = contatore;
    }

    /**
     * Instantiates a new Pub istanza contatore dto.
     */
    public PubIstanzaContatoreDTO() {

    }

    /**
     * Gets fase.
     *
     * @return the fase
     */
    public String getFase() {
        return fase;
    }

    /**
     * Sets fase.
     *
     * @param fase the fase
     */
    public void setFase(String fase) {
        this.fase = fase;
    }

    /**
     * Gets contatore.
     *
     * @return the contatore
     */
    public Long getContatore() {
        return contatore;
    }

    /**
     * Sets contatore.
     *
     * @param contatore the contatore
     */
    public void setContatore(Long contatore) {
        this.contatore = contatore;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PubIstanzaContatoreDTO other = (PubIstanzaContatoreDTO) obj;
        if (contatore == null) {
            if (other.contatore != null)
                return false;
        } else if (!contatore.equals(other.contatore))
            return false;
        if (fase == null) {
            if (other.fase != null)
                return false;
        } else if (!fase.equals(other.fase))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contatore == null) ? 0 : contatore.hashCode());
        result = prime * result + ((fase == null) ? 0 : fase.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "PubIstanzaContatoreDTO [fase=" + fase + ", contatore=" + contatore + "]";
    }


}