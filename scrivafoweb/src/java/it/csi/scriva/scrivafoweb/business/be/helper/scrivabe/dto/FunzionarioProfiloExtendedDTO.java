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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Fuznionario profilo extended dto.
 *
 * @author CSI PIEMONTE
 */
public class FunzionarioProfiloExtendedDTO extends FunzionarioProfiloDTO implements Serializable {

    @JsonProperty("profilo_app")
    private ProfiloAppExtendedDTO profiloApp;

    @JsonProperty("tipo_adempi_profilo")
    private List<TipoAdempimentoProfiloDTO> tipoAdempimentoProfilo;

    @JsonProperty("oggetto_app")
    private List<OggettoAppExtendedDTO> oggettoApp;

    /**
     * Gets profilo app.
     *
     * @return the profilo app
     */
    public ProfiloAppExtendedDTO getProfiloApp() {
        return profiloApp;
    }

    /**
     * Sets profilo app.
     *
     * @param profiloApp the profilo app
     */
    public void setProfiloApp(ProfiloAppExtendedDTO profiloApp) {
        this.profiloApp = profiloApp;
    }

    /**
     * Gets tipo adempimento profilo.
     *
     * @return the tipo adempimento profilo
     */
    public List<TipoAdempimentoProfiloDTO> getTipoAdempimentoProfilo() {
        return tipoAdempimentoProfilo;
    }

    /**
     * Sets tipo adempimento profilo.
     *
     * @param tipoAdempimentoProfilo the tipo adempimento profilo
     */
    public void setTipoAdempimentoProfilo(List<TipoAdempimentoProfiloDTO> tipoAdempimentoProfilo) {
        this.tipoAdempimentoProfilo = tipoAdempimentoProfilo;
    }

    /**
     * Gets oggetto app.
     *
     * @return the oggetto app
     */
    public List<OggettoAppExtendedDTO> getOggettoApp() {
        return oggettoApp;
    }

    /**
     * Sets oggetto app.
     *
     * @param oggettoApp the oggetto app
     */
    public void setOggettoApp(List<OggettoAppExtendedDTO> oggettoApp) {
        this.oggettoApp = oggettoApp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FunzionarioProfiloExtendedDTO that = (FunzionarioProfiloExtendedDTO) o;
        return Objects.equals(profiloApp, that.profiloApp) && Objects.equals(tipoAdempimentoProfilo, that.tipoAdempimentoProfilo) && Objects.equals(oggettoApp, that.oggettoApp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), profiloApp, tipoAdempimentoProfilo, oggettoApp);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FunzionarioProfiloExtendedDTO {");
        sb.append("         profiloApp:").append(profiloApp);
        sb.append(",         tipoAdempimentoProfilo:").append(tipoAdempimentoProfilo);
        sb.append(",         oggettoApp:").append(oggettoApp);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public FunzionarioProfiloDTO getDTO() {
        FunzionarioProfiloDTO dto = new FunzionarioProfiloDTO();
        dto.setIdFunzionario(this.getIdFunzionario());
        if (this.profiloApp != null) {
            dto.setIdProfiloApp(this.profiloApp.getIdProfiloApp());
        }
        dto.setDataInizioValidita(this.getDataInizioValidita());
        dto.setDataFineValidita(this.getDataFineValidita());
        return dto;
    }
}