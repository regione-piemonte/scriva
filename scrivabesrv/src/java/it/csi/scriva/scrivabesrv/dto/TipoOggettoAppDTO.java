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
 * The type Tipo oggetto app dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoOggettoAppDTO implements Serializable {

    @JsonProperty("id_tipo_oggetto_app")
    private Long idTipoOggettoApp;

    @JsonProperty("cod_tipo_oggetto_app")
    private String codTipoOggettoApp;

    @JsonProperty("des_tipo_oggetto_app")
    private String desTipoOggettoApp;

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
     * Gets cod tipo oggetto app.
     *
     * @return codTipoOggettoApp cod tipo oggetto app
     */
    public String getCodTipoOggettoApp() {
        return codTipoOggettoApp;
    }

    /**
     * Sets cod tipo oggetto app.
     *
     * @param codTipoOggettoApp codTipoOggettoApp
     */
    public void setCodTipoOggettoApp(String codTipoOggettoApp) {
        this.codTipoOggettoApp = codTipoOggettoApp;
    }

    /**
     * Gets des tipo oggetto app.
     *
     * @return desTipoOggettoApp des tipo oggetto app
     */
    public String getDesTipoOggettoApp() {
        return desTipoOggettoApp;
    }

    /**
     * Sets des tipo oggetto app.
     *
     * @param desTipoOggettoApp desTipoOggettoApp
     */
    public void setDesTipoOggettoApp(String desTipoOggettoApp) {
        this.desTipoOggettoApp = desTipoOggettoApp;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoOggettoAppDTO that = (TipoOggettoAppDTO) o;
        return Objects.equals(idTipoOggettoApp, that.idTipoOggettoApp) && Objects.equals(codTipoOggettoApp, that.codTipoOggettoApp) && Objects.equals(desTipoOggettoApp, that.desTipoOggettoApp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipoOggettoApp, codTipoOggettoApp, desTipoOggettoApp);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoOggettoApp {");
        sb.append("         idTipoOggettoApp:").append(idTipoOggettoApp);
        sb.append(",         codTipoOggettoApp:'").append(codTipoOggettoApp).append("'");
        sb.append(",         desTipoOggettoApp:'").append(desTipoOggettoApp).append("'");
        sb.append("}");
        return sb.toString();
    }
}