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

public class TipoAdempimentoProfiloExtendedDTO extends TipoAdempimentoProfiloDTO implements Serializable {

    @JsonProperty("profilo_app")
    private ProfiloAppExtendedDTO profiloApp;

    @JsonProperty("tipo_adempimento")
    private TipoAdempimentoExtendedDTO tipoAdempimento;

    /**
     * @return ProfiloAppExtendedDTO
     */
    public ProfiloAppExtendedDTO getProfiloApp() {
        return profiloApp;
    }

    /**
     * @param profiloApp ProfiloAppExtendedDTO
     */
    public void setProfiloApp(ProfiloAppExtendedDTO profiloApp) {
        this.profiloApp = profiloApp;
    }

    /**
     * @return TipoAdempimentoExtendedDTO
     */
    public TipoAdempimentoExtendedDTO getTipoAdempimento() {
        return tipoAdempimento;
    }

    /**
     * @param tipoAdempimento TipoAdempimentoExtendedDTO
     */
    public void setTipoAdempimento(TipoAdempimentoExtendedDTO tipoAdempimento) {
        this.tipoAdempimento = tipoAdempimento;
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
        TipoAdempimentoProfiloExtendedDTO that = (TipoAdempimentoProfiloExtendedDTO) o;
        return Objects.equals(profiloApp, that.profiloApp) && Objects.equals(tipoAdempimento, that.tipoAdempimento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), profiloApp, tipoAdempimento);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoAdempimentoProfiloExtendedDTO {");
        sb.append(super.toString());
        sb.append("         profiloApp:").append(profiloApp);
        sb.append(",         tipoAdempimento:").append(tipoAdempimento);
        sb.append("}");
        return sb.toString();
    }
}