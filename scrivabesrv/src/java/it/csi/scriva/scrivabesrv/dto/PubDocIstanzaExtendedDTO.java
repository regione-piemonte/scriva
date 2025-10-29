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
 * The type Pub doc istanza extended dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubDocIstanzaExtendedDTO extends PubDocIstanzaDTO implements Serializable {

    @JsonProperty("tipologia_allegato")
    private TipologiaAllegatoDTO tipologiaAllegato;

    @JsonProperty("tipo_integra_allegato")
    private TipoIntegraAllegatoDTO tipoIntegraAllegato;

    @JsonProperty("classe_allegato")
    private ClasseAllegatoDTO classeAllegato;

    /**
     * Gets tipologia allegato.
     *
     * @return the tipologia allegato
     */
    public TipologiaAllegatoDTO getTipologiaAllegato() {
        return tipologiaAllegato;
    }

    /**
     * Sets tipologia allegato.
     *
     * @param tipologiaAllegato the tipologia allegato
     */
    public void setTipologiaAllegato(TipologiaAllegatoDTO tipologiaAllegato) {
        this.tipologiaAllegato = tipologiaAllegato;
    }

    /**
     * Gets tipo integra allegato.
     *
     * @return the tipo integra allegato
     */
    public TipoIntegraAllegatoDTO getTipoIntegraAllegato() {
        return tipoIntegraAllegato;
    }

    /**
     * Sets tipo integra allegato.
     *
     * @param tipoIntegraAllegato the tipo integra allegato
     */
    public void setTipoIntegraAllegato(TipoIntegraAllegatoDTO tipoIntegraAllegato) {
        this.tipoIntegraAllegato = tipoIntegraAllegato;
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
        if (!super.equals(o)) return false;
        PubDocIstanzaExtendedDTO that = (PubDocIstanzaExtendedDTO) o;
        return Objects.equals(tipologiaAllegato, that.tipologiaAllegato) && Objects.equals(tipoIntegraAllegato, that.tipoIntegraAllegato) && Objects.equals(classeAllegato, that.classeAllegato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipologiaAllegato, tipoIntegraAllegato, classeAllegato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "PubDocIstanzaExtendedDTO {\n" +
                "         tipologiaAllegato:" + tipologiaAllegato +
                ",\n         tipoIntegraAllegato:" + tipoIntegraAllegato +
                ",\n         classeAllegato:" + classeAllegato +
                super.toString() +
                "}\n";
    }
}