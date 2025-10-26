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

public class TipoAdempimentoProfiloDTO implements Serializable {

    @JsonProperty("id_tipo_adempi_profilo")
    private Long idTipoAdempimentoProfilo;

    @JsonProperty("id_profilo_app")
    private Long idProfiloApp;

    @JsonProperty("id_tipo_adempimento")
    private Long idTipoAdempimento;

    @JsonProperty("flg_sola_lettura")
    private Boolean flgSoloLettura;

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
     * @return idProfiloApp
     */
    public Long getIdProfiloApp() {
        return idProfiloApp;
    }

    /**
     * @param idProfiloApp idProfiloApp
     */
    public void setIdProfiloApp(Long idProfiloApp) {
        this.idProfiloApp = idProfiloApp;
    }

    /**
     * @return idTipoAdempimento
     */
    public Long getIdTipoAdempimento() {
        return idTipoAdempimento;
    }

    /**
     * @param idTipoAdempimento idTipoAdempimento
     */
    public void setIdTipoAdempimento(Long idTipoAdempimento) {
        this.idTipoAdempimento = idTipoAdempimento;
    }

    /**
     * @return flgSoloLettura
     */
    public Boolean getFlgSoloLettura() {
        return flgSoloLettura;
    }

    /**
     * @param flgSoloLettura flgSoloLettura
     */
    public void setFlgSoloLettura(Boolean flgSoloLettura) {
        this.flgSoloLettura = flgSoloLettura;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoAdempimentoProfiloDTO that = (TipoAdempimentoProfiloDTO) o;
        return Objects.equals(idTipoAdempimentoProfilo, that.idTipoAdempimentoProfilo) && Objects.equals(idProfiloApp, that.idProfiloApp) && Objects.equals(idTipoAdempimento, that.idTipoAdempimento) && Objects.equals(flgSoloLettura, that.flgSoloLettura);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipoAdempimentoProfilo, idProfiloApp, idTipoAdempimento, flgSoloLettura);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoAdempimentoProfiloDTO {");
        sb.append("         idTipoAdempimentoProfilo:").append(idTipoAdempimentoProfilo);
        sb.append(",         idProfiloApp:").append(idProfiloApp);
        sb.append(",         idTipoAdempimento:").append(idTipoAdempimento);
        sb.append(",         flgSoloLettura:").append(flgSoloLettura);
        sb.append("}");
        return sb.toString();
    }
}