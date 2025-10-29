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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * The type Catasto sezione dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(Include.NON_NULL)
public class CatastoSezioneDTO implements Serializable {

    @JsonProperty("comune")
    private CatastoComuneDTO comune = null;

    @JsonProperty("sezione")
    private String sezione = null;

    @JsonProperty("nome_sezione")
    private String nomeSezione = null;

    /**
     * Gets comune.
     *
     * @return the comune
     */
    public CatastoComuneDTO getComune() {
        return comune;
    }

    /**
     * Sets comune.
     *
     * @param comune the comune
     */
    public void setComune(CatastoComuneDTO comune) {
        this.comune = comune;
    }

    /**
     * Gets sezione.
     *
     * @return the sezione
     */
    public String getSezione() {
        return sezione;
    }

    /**
     * Sets sezione.
     *
     * @param sezione the sezione
     */
    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    /**
     * Gets nome sezione.
     *
     * @return the nome sezione
     */
    public String getNomeSezione() {
        return nomeSezione;
    }

    /**
     * Sets nome sezione.
     *
     * @param nomeSezione the nome sezione
     */
    public void setNomeSezione(String nomeSezione) {
        this.nomeSezione = nomeSezione;
    }
}