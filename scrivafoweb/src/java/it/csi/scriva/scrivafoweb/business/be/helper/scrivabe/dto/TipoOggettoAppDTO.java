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

public class TipoOggettoAppDTO implements Serializable {

    @JsonProperty("id_tipo_oggetto_app")
    private Long idTipoOggettoApp;

    @JsonProperty("cod_tipo_oggetto_app")
    private String codTipoOggettoApp;

    @JsonProperty("des_tipo_oggetto_app")
    private String desTipoOggettoApp;

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
     * @return codTipoOggettoApp
     */
    public String getCodTipoOggettoApp() {
        return codTipoOggettoApp;
    }

    /**
     * @param codTipoOggettoApp codTipoOggettoApp
     */
    public void setCodTipoOggettoApp(String codTipoOggettoApp) {
        this.codTipoOggettoApp = codTipoOggettoApp;
    }

    /**
     * @return desTipoOggettoApp
     */
    public String getDesTipoOggettoApp() {
        return desTipoOggettoApp;
    }

    /**
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