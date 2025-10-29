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

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Oggetto app dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OggettoAppDTO implements Serializable {

    @JsonProperty("id_oggetto_app")
    private Long idOggettoApp;

    @JsonProperty("id_tipo_ogg_app")
    private Long idTipoOggettoApp;

    @JsonProperty("cod_oggetto_app")
    private String codOggettoApp;

    @JsonProperty("des_oggetto_app")
    private String desOggettoApp;

    @JsonProperty("id_tipo_evento")
    private Long idTipoEvento;

    //@JsonProperty("flg_previsto_da_gestore_processo")
    @JsonIgnore
    private Boolean flgPrevistoDaGestoreProcesso;

    /**
     * Gets id oggetto app.
     *
     * @return idOggettoApp id oggetto app
     */
    public Long getIdOggettoApp() {
        return idOggettoApp;
    }

    /**
     * Sets id oggetto app.
     *
     * @param idOggettoApp idOggettoApp
     */
    public void setIdOggettoApp(Long idOggettoApp) {
        this.idOggettoApp = idOggettoApp;
    }

    /**
     * Gets id tipo oggetto app.
     *
     * @return idTipoOggettoApp id tipo oggetto app
     */
    public Long getIdTipoOggettoApp() {
        return idTipoOggettoApp;
    }

    /**
     * Sets id tipo oggetto app.
     *
     * @param idTipoOggettoApp idTipoOggettoApp
     */
    public void setIdTipoOggettoApp(Long idTipoOggettoApp) {
        this.idTipoOggettoApp = idTipoOggettoApp;
    }

    /**
     * Gets cod oggetto app.
     *
     * @return codOggettoApp cod oggetto app
     */
    public String getCodOggettoApp() {
        return codOggettoApp;
    }

    /**
     * Sets cod oggetto app.
     *
     * @param codOggettoApp codOggettoApp
     */
    public void setCodOggettoApp(String codOggettoApp) {
        this.codOggettoApp = codOggettoApp;
    }

    /**
     * Gets des oggetto app.
     *
     * @return desOggettoApp des oggetto app
     */
    public String getDesOggettoApp() {
        return desOggettoApp;
    }

    /**
     * Sets des oggetto app.
     *
     * @param desOggettoApp desOggettoApp
     */
    public void setDesOggettoApp(String desOggettoApp) {
        this.desOggettoApp = desOggettoApp;
    }

    /**
     * Gets id tipo evento.
     *
     * @return the id tipo evento
     */
    public Long getIdTipoEvento() {
        return idTipoEvento;
    }

    /**
     * Sets id tipo evento.
     *
     * @param idTipoEvento the id tipo evento
     */
    public void setIdTipoEvento(Long idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    /**
     * Gets flg previsto da gestore processo.
     *
     * @return the flg previsto da gestore processo
     */
    public Boolean getFlgPrevistoDaGestoreProcesso() {
        return flgPrevistoDaGestoreProcesso;
    }

    /**
     * Sets flg previsto da gestore processo.
     *
     * @param flgPrevistoDaGestoreProcesso the flg previsto da gestore processo
     */
    public void setFlgPrevistoDaGestoreProcesso(Boolean flgPrevistoDaGestoreProcesso) {
        this.flgPrevistoDaGestoreProcesso = flgPrevistoDaGestoreProcesso;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OggettoAppDTO that = (OggettoAppDTO) o;
        return Objects.equals(idOggettoApp, that.idOggettoApp) && Objects.equals(idTipoOggettoApp, that.idTipoOggettoApp) && Objects.equals(codOggettoApp, that.codOggettoApp) && Objects.equals(desOggettoApp, that.desOggettoApp) && Objects.equals(idTipoEvento, that.idTipoEvento) && Objects.equals(flgPrevistoDaGestoreProcesso, that.flgPrevistoDaGestoreProcesso);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idOggettoApp, idTipoOggettoApp, codOggettoApp, desOggettoApp, idTipoEvento, flgPrevistoDaGestoreProcesso);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "OggettoAppDTO {\n" +
                "         idOggettoApp:" + idOggettoApp +
                ",\n         idTipoOggettoApp:" + idTipoOggettoApp +
                ",\n         codOggettoApp:'" + codOggettoApp + "'" +
                ",\n         desOggettoApp:'" + desOggettoApp + "'" +
                ",\n         idTipoEvento:" + idTipoEvento +
                ",\n         flgPrevistoDaGestoreProcesso:" + flgPrevistoDaGestoreProcesso +
                "}\n";
    }
}