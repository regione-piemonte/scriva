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
 * The type Oggetto app extended dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoAppExtendedDTO extends OggettoAppDTO implements Serializable {

    @JsonProperty("tipo_oggetto_app")
    private TipoOggettoAppDTO tipoOggettoAppDTO;

    @JsonProperty("tipo_evento")
    //@JsonIgnore
    private TipoEventoDTO tipoEvento;


    /**
     * Gets tipo oggetto app.
     *
     * @return tipoOggettoApp tipo oggetto app
     */
    @JsonProperty("tipo_oggetto_app")
    public TipoOggettoAppDTO getTipoOggettoApp() {
        return tipoOggettoAppDTO;
    }

    /**
     * Sets tipo oggetto app.
     *
     * @param tipoOggettoAppDTO tipoOggettoApp
     */
    public void setTipoOggettoApp(TipoOggettoAppDTO tipoOggettoAppDTO) {
        this.tipoOggettoAppDTO = tipoOggettoAppDTO;
    }

    /**
     * Gets tipo evento.
     *
     * @return the tipo evento
     */
    public TipoEventoDTO getTipoEvento() {
        return tipoEvento;
    }

    /**
     * Sets tipo evento.
     *
     * @param tipoEvento the tipo evento
     */
    public void setTipoEvento(TipoEventoDTO tipoEvento) {
        this.tipoEvento = tipoEvento;
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
        OggettoAppExtendedDTO that = (OggettoAppExtendedDTO) o;
        return Objects.equals(tipoOggettoAppDTO, that.tipoOggettoAppDTO) && Objects.equals(tipoEvento, that.tipoEvento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoOggettoAppDTO, tipoEvento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "OggettoAppExtendedDTO {\n" +
                super.toString() +
                "         tipoOggettoAppDTO:" + tipoOggettoAppDTO +
                ",\n         tipoEvento:" + tipoEvento +
                "}\n";
    }
}