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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class TipoAdempimentoOggettoAppExtendedDTO extends TipoAdempimentoOggettoAppDTO implements Serializable {

    @JsonProperty("oggetto_app")
    private OggettoAppExtendedDTO oggettoApp;

    @JsonProperty("stato_istanza")
    private StatoIstanzaDTO statoIstanza;

    @JsonProperty("tipo_adempi_profilo")
    private TipoAdempimentoProfiloExtendedDTO tipoAdempimentoProfilo;

    /**
     * @return oggettoApp
     */
    public OggettoAppExtendedDTO getOggettoApp() {
        return oggettoApp;
    }

    /**
     * @param oggettoApp oggettoApp
     */
    public void setOggettoApp(OggettoAppExtendedDTO oggettoApp) {
        this.oggettoApp = oggettoApp;
    }

    /**
     * @return statoIstanza
     */
    public StatoIstanzaDTO getStatoIstanza() {
        return statoIstanza;
    }

    /**
     * @param statoIstanza statoIstanza
     */
    public void setStatoIstanza(StatoIstanzaDTO statoIstanza) {
        this.statoIstanza = statoIstanza;
    }

    /**
     * @return tipoAdempimentoProfilo
     */
    public TipoAdempimentoProfiloExtendedDTO getTipoAdempimentoProfilo() {
        return tipoAdempimentoProfilo;
    }

    /**
     * @param tipoAdempimentoProfilo tipoAdempimentoProfilo
     */
    public void setTipoAdempimentoProfilo(TipoAdempimentoProfiloExtendedDTO tipoAdempimentoProfilo) {
        this.tipoAdempimentoProfilo = tipoAdempimentoProfilo;
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
        TipoAdempimentoOggettoAppExtendedDTO that = (TipoAdempimentoOggettoAppExtendedDTO) o;
        return Objects.equals(oggettoApp, that.oggettoApp) && Objects.equals(statoIstanza, that.statoIstanza) && Objects.equals(tipoAdempimentoProfilo, that.tipoAdempimentoProfilo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oggettoApp, statoIstanza, tipoAdempimentoProfilo);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoAdempimentoOggettoAppExtendedDTO {");
        sb.append(super.toString());
        sb.append("         oggettoApp:").append(oggettoApp);
        sb.append(",         statoIstanza:").append(statoIstanza);
        sb.append(",         tipoAdempimentoProfilo:").append(tipoAdempimentoProfilo);
        sb.append("}");
        return sb.toString();
    }
}