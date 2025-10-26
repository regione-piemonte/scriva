/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class TipoAdempimentoOggettoAppDTO implements Serializable {

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
     * @return idTipoAdempimentoOggettoApp
     */
    public Long getIdTipoAdempimentoOggettoApp() {
        return idTipoAdempimentoOggettoApp;
    }

    /**
     * @param idTipoAdempimentoOggettoApp idTipoAdempimentoOggettoApp
     */
    public void setIdTipoAdempimentoOggettoApp(Long idTipoAdempimentoOggettoApp) {
        this.idTipoAdempimentoOggettoApp = idTipoAdempimentoOggettoApp;
    }

    /**
     * @return idOggettoApp
     */
    public Long getIdOggettoApp() {
        return idOggettoApp;
    }

    /**
     * @param idOggettoApp idOggettoApp
     */
    public void setIdOggettoApp(Long idOggettoApp) {
        this.idOggettoApp = idOggettoApp;
    }

    /**
     * @return idStatoIstanza
     */
    public Long getIdStatoIstanza() {
        return idStatoIstanza;
    }

    /**
     * @param idStatoIstanza idStatoIstanza
     */
    public void setIdStatoIstanza(Long idStatoIstanza) {
        this.idStatoIstanza = idStatoIstanza;
    }

    /**
     * @return idTipoAdempimentoProfilo
     */
    public Long getIdTipoAdempimentoProfilo() {
        return idTipoAdempimentoProfilo;
    }

    /**
     * @param idTipoAdempimentoProfilo idTipoAdempimentoProfilo
     */
    public void setIdTipoAdempimentoProfilo(Long idTipoAdempimentoProfilo) {
        this.idTipoAdempimentoProfilo = idTipoAdempimentoProfilo;
    }

    /**
     * @return flgClonaIstanza
     */
    public Boolean getFlgClonaIstanza() {
        return flgClonaIstanza;
    }

    /**
     * @param flgClonaIstanza flgClonaIstanza
     */
    public void setFlgClonaIstanza(Boolean flgClonaIstanza) {
        this.flgClonaIstanza = flgClonaIstanza;
    }

    /**
     * @return idAdempimentoClonaIstanza
     */
    public Long getIdAdempimentoClonaIstanza() {
        return idAdempimentoClonaIstanza;
    }

    /**
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
        return Objects.equals(idTipoAdempimentoOggettoApp, that.idTipoAdempimentoOggettoApp) && Objects.equals(idOggettoApp, that.idOggettoApp) && Objects.equals(idStatoIstanza, that.idStatoIstanza) && Objects.equals(idTipoAdempimentoProfilo, that.idTipoAdempimentoProfilo) && Objects.equals(flgClonaIstanza, that.flgClonaIstanza) && Objects.equals(idAdempimentoClonaIstanza, that.idAdempimentoClonaIstanza);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipoAdempimentoOggettoApp, idOggettoApp, idStatoIstanza, idTipoAdempimentoProfilo, flgClonaIstanza, idAdempimentoClonaIstanza);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoAdempimentoOggettoAppDTO {");
        sb.append("         idTipoAdempimentoOggettoApp:").append(idTipoAdempimentoOggettoApp);
        sb.append(",         idOggettoApp:").append(idOggettoApp);
        sb.append(",         idStatoIstanza:").append(idStatoIstanza);
        sb.append(",         idTipoAdempimentoProfilo:").append(idTipoAdempimentoProfilo);
        sb.append(",         flgClonaIstanza:").append(flgClonaIstanza);
        sb.append(",         idAdempimentoClonaIstanza:").append(idAdempimentoClonaIstanza);
        sb.append("}");
        return sb.toString();
    }
}