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
 * The type Tipo adempimento oggetto app extended dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoAdempimentoOggettoAppExtendedDTO extends TipoAdempimentoOggettoAppDTO implements Serializable {

    @JsonProperty("oggetto_app")
    private OggettoAppExtendedDTO oggettoApp;

    @JsonProperty("stato_istanza")
    private StatoIstanzaDTO statoIstanza;

    @JsonProperty("tipo_adempi_profilo")
    private TipoAdempimentoProfiloExtendedDTO tipoAdempimentoProfilo;

    @JsonIgnore
    private String statoAttore;

    @JsonIgnore
    private Integer ordinamentoProfiloApp;

    /**
     * Gets oggetto app.
     *
     * @return oggettoApp oggetto app
     */
    public OggettoAppExtendedDTO getOggettoApp() {
        return oggettoApp;
    }

    /**
     * Sets oggetto app.
     *
     * @param oggettoApp oggettoApp
     */
    public void setOggettoApp(OggettoAppExtendedDTO oggettoApp) {
        this.oggettoApp = oggettoApp;
    }

    /**
     * Gets stato istanza.
     *
     * @return statoIstanza stato istanza
     */
    public StatoIstanzaDTO getStatoIstanza() {
        return statoIstanza;
    }

    /**
     * Sets stato istanza.
     *
     * @param statoIstanza statoIstanza
     */
    public void setStatoIstanza(StatoIstanzaDTO statoIstanza) {
        this.statoIstanza = statoIstanza;
    }

    /**
     * Gets tipo adempimento profilo.
     *
     * @return tipoAdempimentoProfilo tipo adempimento profilo
     */
    public TipoAdempimentoProfiloExtendedDTO getTipoAdempimentoProfilo() {
        return tipoAdempimentoProfilo;
    }

    /**
     * Sets tipo adempimento profilo.
     *
     * @param tipoAdempimentoProfilo tipoAdempimentoProfilo
     */
    public void setTipoAdempimentoProfilo(TipoAdempimentoProfiloExtendedDTO tipoAdempimentoProfilo) {
        this.tipoAdempimentoProfilo = tipoAdempimentoProfilo;
    }

    /**
     * Gets stato attore.
     *
     * @return statoAttore stato attore
     */
    public String getStatoAttore() {
        return statoAttore;
    }

    /**
     * Sets stato attore.
     *
     * @param statoAttore statoAttore
     */
    public void setStatoAttore(String statoAttore) {
        this.statoAttore = statoAttore;
    }

    /**
     * Gets ordinamento profilo app.
     *
     * @return ordinamentoProfiloApp ordinamento profilo app
     */
    public Integer getOrdinamentoProfiloApp() {
        return ordinamentoProfiloApp;
    }

    /**
     * Sets ordinamento profilo app.
     *
     * @param ordinamentoProfiloApp ordinamentoProfiloApp
     */
    public void setOrdinamentoProfiloApp(Integer ordinamentoProfiloApp) {
        this.ordinamentoProfiloApp = ordinamentoProfiloApp;
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
        return Objects.equals(oggettoApp, that.oggettoApp) && Objects.equals(statoIstanza, that.statoIstanza) && Objects.equals(tipoAdempimentoProfilo, that.tipoAdempimentoProfilo) && Objects.equals(statoAttore, that.statoAttore) && Objects.equals(ordinamentoProfiloApp, that.ordinamentoProfiloApp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oggettoApp, statoIstanza, tipoAdempimentoProfilo, statoAttore, ordinamentoProfiloApp);
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
        sb.append(",         statoAttore:").append(statoAttore);
        sb.append(",         ordinamentoProfiloApp:").append(ordinamentoProfiloApp);
        sb.append("}");
        return sb.toString();
    }
}