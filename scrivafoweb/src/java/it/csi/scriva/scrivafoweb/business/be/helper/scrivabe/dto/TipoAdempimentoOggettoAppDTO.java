/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipo adempimento oggetto app dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoAdempimentoOggettoAppDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_tipo_adempi_ogg_app")
    private Long idTipoAdempimentoOggettoApp;

    @JsonProperty("id_oggetto_app")
    private Long idOggettoApp;

    @JsonProperty("id_stato_istanza")
    private Long idStatoIstanza;

    @JsonProperty("id_tipo_adempi_profilo")
    private Long idTipoAdempimentoProfilo;

    @JsonProperty("flg_clona_istanza")
    private Boolean flgClonaIstanza;

    @JsonProperty("id_adempimento_clona_istanza")
    private Long idAdempimentoClonaIstanza;

    /**
     * Gets id istanza.
     *
     * @return the id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza the id istanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets id tipo adempimento oggetto app.
     *
     * @return idTipoAdempimentoOggettoApp id tipo adempimento oggetto app
     */
    public Long getIdTipoAdempimentoOggettoApp() {
        return idTipoAdempimentoOggettoApp;
    }

    /**
     * Sets id tipo adempimento oggetto app.
     *
     * @param idTipoAdempimentoOggettoApp idTipoAdempimentoOggettoApp
     */
    public void setIdTipoAdempimentoOggettoApp(Long idTipoAdempimentoOggettoApp) {
        this.idTipoAdempimentoOggettoApp = idTipoAdempimentoOggettoApp;
    }

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
     * Gets id stato istanza.
     *
     * @return idStatoIstanza id stato istanza
     */
    public Long getIdStatoIstanza() {
        return idStatoIstanza;
    }

    /**
     * Sets id stato istanza.
     *
     * @param idStatoIstanza idStatoIstanza
     */
    public void setIdStatoIstanza(Long idStatoIstanza) {
        this.idStatoIstanza = idStatoIstanza;
    }

    /**
     * Gets id tipo adempimento profilo.
     *
     * @return idTipoAdempimentoProfilo id tipo adempimento profilo
     */
    public Long getIdTipoAdempimentoProfilo() {
        return idTipoAdempimentoProfilo;
    }

    /**
     * Sets id tipo adempimento profilo.
     *
     * @param idTipoAdempimentoProfilo idTipoAdempimentoProfilo
     */
    public void setIdTipoAdempimentoProfilo(Long idTipoAdempimentoProfilo) {
        this.idTipoAdempimentoProfilo = idTipoAdempimentoProfilo;
    }

    /**
     * Gets flg clona istanza.
     *
     * @return flgClonaIstanza flg clona istanza
     */
    public Boolean getFlgClonaIstanza() {
        return flgClonaIstanza;
    }

    /**
     * Sets flg clona istanza.
     *
     * @param flgClonaIstanza flgClonaIstanza
     */
    public void setFlgClonaIstanza(Boolean flgClonaIstanza) {
        this.flgClonaIstanza = flgClonaIstanza;
    }

    /**
     * Gets id adempimento clona istanza.
     *
     * @return idAdempimentoClonaIstanza id adempimento clona istanza
     */
    public Long getIdAdempimentoClonaIstanza() {
        return idAdempimentoClonaIstanza;
    }

    /**
     * Sets id adempimento clona istanza.
     *
     * @param idAdempimentoClonaIstanza idAdempimentoClonaIstanza
     */
    public void setIdAdempimentoClonaIstanza(Long idAdempimentoClonaIstanza) {
        this.idAdempimentoClonaIstanza = idAdempimentoClonaIstanza;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoAdempimentoOggettoAppDTO that = (TipoAdempimentoOggettoAppDTO) o;
        return Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idTipoAdempimentoOggettoApp, that.idTipoAdempimentoOggettoApp) && Objects.equals(idOggettoApp, that.idOggettoApp) && Objects.equals(idStatoIstanza, that.idStatoIstanza) && Objects.equals(idTipoAdempimentoProfilo, that.idTipoAdempimentoProfilo) && Objects.equals(flgClonaIstanza, that.flgClonaIstanza) && Objects.equals(idAdempimentoClonaIstanza, that.idAdempimentoClonaIstanza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, idTipoAdempimentoOggettoApp, idOggettoApp, idStatoIstanza, idTipoAdempimentoProfilo, flgClonaIstanza, idAdempimentoClonaIstanza);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TipoAdempimentoOggettoAppDTO {\n" +
                "         idIstanza:" + idIstanza +
                ",\n         idTipoAdempimentoOggettoApp:" + idTipoAdempimentoOggettoApp +
                ",\n         idOggettoApp:" + idOggettoApp +
                ",\n         idStatoIstanza:" + idStatoIstanza +
                ",\n         idTipoAdempimentoProfilo:" + idTipoAdempimentoProfilo +
                ",\n         flgClonaIstanza:" + flgClonaIstanza +
                ",\n         idAdempimentoClonaIstanza:" + idAdempimentoClonaIstanza +
                "}\n";
    }
}