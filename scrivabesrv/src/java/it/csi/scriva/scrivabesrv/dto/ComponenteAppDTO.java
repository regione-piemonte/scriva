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
 * The type Componente app dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponenteAppDTO implements Serializable {

    @JsonProperty("id_componente_app")
    private Long idComponenteApp;

    @JsonProperty("cod_componente_app")
    private String codComponenteApp;

    @JsonProperty("des_componente_app")
    private String desComponenteApp;

    /**
     * Gets id componente app.
     *
     * @return idComponenteApp id componente app
     */
    public Long getIdComponenteApp() {
        return idComponenteApp;
    }

    /**
     * Sets id componente app.
     *
     * @param idComponenteApp idComponenteApp
     */
    public void setIdComponenteApp(Long idComponenteApp) {
        this.idComponenteApp = idComponenteApp;
    }

    /**
     * Gets cod componente app.
     *
     * @return codComponenteApp cod componente app
     */
    public String getCodComponenteApp() {
        return codComponenteApp;
    }

    /**
     * Sets cod componente app.
     *
     * @param codComponenteApp codComponenteApp
     */
    public void setCodComponenteApp(String codComponenteApp) {
        this.codComponenteApp = codComponenteApp;
    }

    /**
     * Gets des componente app.
     *
     * @return desComponenteApp des componente app
     */
    public String getDesComponenteApp() {
        return desComponenteApp;
    }

    /**
     * Sets des componente app.
     *
     * @param desComponenteApp desComponenteApp
     */
    public void setDesComponenteApp(String desComponenteApp) {
        this.desComponenteApp = desComponenteApp;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponenteAppDTO that = (ComponenteAppDTO) o;
        return Objects.equals(idComponenteApp, that.idComponenteApp) && Objects.equals(codComponenteApp, that.codComponenteApp) && Objects.equals(desComponenteApp, that.desComponenteApp);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idComponenteApp, codComponenteApp, desComponenteApp);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ComponenteApp {");
        sb.append("         idComponenteApp:").append(idComponenteApp);
        sb.append(",         codComponenteApp:'").append(codComponenteApp).append("'");
        sb.append(",         desComponenteApp:'").append(desComponenteApp).append("'");
        sb.append("}");
        return sb.toString();
    }
}