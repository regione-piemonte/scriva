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
import java.util.Objects;

/**
 * The type Adempimento stato istanza dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdempimentoStatoIstanzaDTO implements Serializable {

    @JsonProperty("stato_istanza")
    private StatoIstanzaDTO statoIstanza;

    @JsonProperty("classe_allegato")
    private ClasseAllegatoDTO classeAllegato;

    /**
     * Gets stato istanza.
     *
     * @return the stato istanza
     */
    public StatoIstanzaDTO getStatoIstanza() {
        return statoIstanza;
    }

    /**
     * Sets stato istanza.
     *
     * @param statoIstanza the stato istanza
     */
    public void setStatoIstanza(StatoIstanzaDTO statoIstanza) {
        this.statoIstanza = statoIstanza;
    }

    /**
     * Gets classe allegato.
     *
     * @return the classe allegato
     */
    public ClasseAllegatoDTO getClasseAllegato() {
        return classeAllegato;
    }

    /**
     * Sets classe allegato.
     *
     * @param classeAllegato the classe allegato
     */
    public void setClasseAllegato(ClasseAllegatoDTO classeAllegato) {
        this.classeAllegato = classeAllegato;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdempimentoStatoIstanzaDTO that = (AdempimentoStatoIstanzaDTO) o;
        return Objects.equals(statoIstanza, that.statoIstanza) && Objects.equals(classeAllegato, that.classeAllegato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(statoIstanza, classeAllegato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "AdempimentoStatoIstanzaDTO {\n" +
                "         statoIstanza:" + statoIstanza +
                ",\n         classeAllegato:" + classeAllegato +
                "}\n";
    }
}