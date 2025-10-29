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
 * The type Tipo adempimento profilo dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
     * Gets id profilo app.
     *
     * @return idProfiloApp id profilo app
     */
    public Long getIdProfiloApp() {
        return idProfiloApp;
    }

    /**
     * Sets id profilo app.
     *
     * @param idProfiloApp idProfiloApp
     */
    public void setIdProfiloApp(Long idProfiloApp) {
        this.idProfiloApp = idProfiloApp;
    }

    /**
     * Gets id tipo adempimento.
     *
     * @return idTipoAdempimento id tipo adempimento
     */
    public Long getIdTipoAdempimento() {
        return idTipoAdempimento;
    }

    /**
     * Sets id tipo adempimento.
     *
     * @param idTipoAdempimento idTipoAdempimento
     */
    public void setIdTipoAdempimento(Long idTipoAdempimento) {
        this.idTipoAdempimento = idTipoAdempimento;
    }

    /**
     * Gets flg solo lettura.
     *
     * @return flgSoloLettura flg solo lettura
     */
    public Boolean getFlgSoloLettura() {
        return flgSoloLettura;
    }

    /**
     * Sets flg solo lettura.
     *
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