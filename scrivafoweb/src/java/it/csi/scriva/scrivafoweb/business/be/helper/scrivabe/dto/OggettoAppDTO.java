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

public class OggettoAppDTO implements Serializable {

    @JsonProperty("id_oggetto_app")
    private Long idOggettoApp;

    @JsonProperty("id_tipo_ogg_app")
    private Long idTipoOggettoApp;

    @JsonProperty("cod_oggetto_app")
    private String codOggettoApp;

    @JsonProperty("des_oggetto_app")
    private String desOggettoApp;

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
     * @return idTipoOggettoApp
     */
    public Long getIdTipoOggettoApp() {
        return idTipoOggettoApp;
    }

    /**
     * @param idTipoOggettoApp idTipoOggettoApp
     */
    public void setIdTipoOggettoApp(Long idTipoOggettoApp) {
        this.idTipoOggettoApp = idTipoOggettoApp;
    }

    /**
     * @return codOggettoApp
     */
    public String getCodOggettoApp() {
        return codOggettoApp;
    }

    /**
     * @param codOggettoApp codOggettoApp
     */
    public void setCodOggettoApp(String codOggettoApp) {
        this.codOggettoApp = codOggettoApp;
    }

    /**
     * @return desOggettoApp
     */
    public String getDesOggettoApp() {
        return desOggettoApp;
    }

    /**
     * @param desOggettoApp desOggettoApp
     */
    public void setDesOggettoApp(String desOggettoApp) {
        this.desOggettoApp = desOggettoApp;
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
        return Objects.equals(idOggettoApp, that.idOggettoApp) && Objects.equals(idTipoOggettoApp, that.idTipoOggettoApp) && Objects.equals(codOggettoApp, that.codOggettoApp) && Objects.equals(desOggettoApp, that.desOggettoApp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idOggettoApp, idTipoOggettoApp, codOggettoApp, desOggettoApp);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OggettoApp {");
        sb.append("         idOggettoApp:").append(idOggettoApp);
        sb.append(",         idTipoOggettoApp:").append(idTipoOggettoApp);
        sb.append(",         codOggettoApp:'").append(codOggettoApp).append("'");
        sb.append(",         desOggettoApp:'").append(desOggettoApp).append("'");
        sb.append("}");
        return sb.toString();
    }
}